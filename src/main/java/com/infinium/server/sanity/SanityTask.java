package com.infinium.server.sanity;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.sounds.InfiniumSounds;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Box;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class SanityTask {
    private static final EntityAttributeModifier EXTRA_HEALTH_BOOST = new EntityAttributeModifier(UUID.randomUUID(), "Sanity Healthboost", 4, EntityAttributeModifier.Operation.ADDITION);
    private final SanityManager manager;
    public SanityTask(SanityManager manager){
        this.manager = manager;
    }
    
    public void run(){
        manager.totalPlayers.forEach(this::sanityEffects);
    }

    private void sanityEffects(PlayerEntity p) {
        if (shouldPreventEffects(p)) return;
        healthEffects(p);
        kairosEffects(p);
        soundEffects(p);
    }

    private void healthEffects(PlayerEntity p) {
        var entityAttributeInstance = p.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (entityAttributeInstance == null) return;
        int sanity = manager.get(p, manager.SANITY);

        if (sanity >= 85) {
            if (!entityAttributeInstance.hasModifier(EXTRA_HEALTH_BOOST)) entityAttributeInstance.addTemporaryModifier(EXTRA_HEALTH_BOOST);
            if (p.getHealth() >= 16.0) manager.decrease(p, 1, manager.POSITIVE_HEALTH_COOLDOWN);

        } else {

            if (entityAttributeInstance.hasModifier(EXTRA_HEALTH_BOOST)) {
                entityAttributeInstance.removeModifier(EXTRA_HEALTH_BOOST);
                p.setHealth(p.getHealth());
            }

            if (p.getHealth() <= 12.0) manager.decrease(p, 1, manager.NEGATIVE_HEALTH_COOLDOWN);
        }

        if (manager.get(p, manager.POSITIVE_HEALTH_COOLDOWN) <= 0) {
            increaseRandomSanity(p, 0);
            manager.set(p, 20, manager.POSITIVE_HEALTH_COOLDOWN);
        }

        if (manager.get(p, manager.NEGATIVE_HEALTH_COOLDOWN) <= 0) {
            decreaseRandomSanity(p, 0);
            manager.set(p, 15, manager.POSITIVE_HEALTH_COOLDOWN);
        }

    }
    private void kairosEffects(PlayerEntity p) {
        int timeCooldownSeconds = manager.get(p, manager.TIME_COOLDOWN);

        if (timeCooldownSeconds > 0) manager.decrease (p, 1, manager.TIME_COOLDOWN);
        if (timeCooldownSeconds <= 0) decreaseRandomSanity(p, 0);
        else if (timeCooldownSeconds <= 150) decreaseRandomSanity(p, 5);

        var timeCooldownMinutes = timeCooldownSeconds % 3600 / 60;
        var formattedSeconds = timeCooldownSeconds % 60;
        var color = timeCooldownSeconds <= 150 ? "&4" : "&6";
        var msg = ChatFormatter.text("&7[" + color + String.format("%02d:%02d", timeCooldownMinutes, formattedSeconds) + "&7]");
        p.sendMessage(msg, true);
    }

    private void soundEffects(PlayerEntity p) {
        int cooldown = manager.get(p, manager.SOUND_COOLDOWN);
        int sanity = manager.get(p, manager.SANITY);

        if (sanity <= 80) manager.decrease(p, 1, manager.SOUND_COOLDOWN);

        if (cooldown < 0) {
            p.playSound(getMoodSoundEvent(p), SoundCategory.AMBIENT, 1, 1);
            manager.decrease(p, 1, manager.SANITY);
            manager.set(p, 5, manager.SOUND_COOLDOWN);
        }
    }

    private SoundEvent getMoodSoundEvent(PlayerEntity p) {
        var sanity = manager.get(p, manager.SANITY);
        var world = p.getWorld();
        var blockPos = p.getBlockPos();
        var lightLevel = world.getLightLevel(blockPos);
        var worldKey = world.getRegistryKey().getValue().toString();
        var biome = world.getBiome(blockPos);
        var biomeKey = "";
        if (biome.getKey().isPresent()) biomeKey = biome.getKey().get().getValue().toString();

        int soundPoints;
        int sanityFactor = (int) (sanity * 0.1) + (int) (-lightLevel * 0.5);
        var random = world.getRandom();

        switch (worldKey) {

            default -> {
                soundPoints = 100;

                if (isPositiveBiome(biomeKey)) soundPoints += random.nextInt(10) + 5;
                else if (isNegativeBiome(biomeKey)) soundPoints -= random.nextInt(15) + 5;
                if (getNearbyEntities(p, 10).size() >= 5) soundPoints -= random.nextInt(15) + 10;
                else soundPoints += random.nextInt(15) + 5;

            }

            case "infinium:the_nightmare", "minecraft:the_nether" -> {
                soundPoints = 80;

                if (isPositiveBiome(biomeKey)) soundPoints += random.nextInt(10) + 5;
                else if (isNegativeBiome(biomeKey)) soundPoints -= random.nextInt(15) + 5;
                if (getNearbyEntities(p, 20).size() >= 3) soundPoints -= random.nextInt(15) + 10;
                else soundPoints += random.nextInt(15) + 5;

            }

            case "infinium:the_void", "minecraft:the_end" -> {
                soundPoints = 60;

                if (isPositiveBiome(biomeKey)) soundPoints += random.nextInt(10) + 5;
                else if (isNegativeBiome(biomeKey)) soundPoints -= random.nextInt(15) + 5;
                if (getNearbyEntities(p, 20).size() >= 1) soundPoints -= random.nextInt(15) + 10;
                else soundPoints += random.nextInt(15) + 5;

            }
        }

        soundPoints -= sanityFactor;
        Infinium.getInstance().LOGGER.info(String.valueOf(soundPoints));
        if (soundPoints <= 60) return InfiniumSounds.LOW_SANITY_2;

        return null;
    }

    private boolean isPositiveBiome(String biomeStringKey) {
        //String needs to be a biome registry key
        String s = biomeStringKey.toLowerCase();
        return s.equals("minecraft:plains") ||
               s.equals("minecraft:river")  ||
               s.equals("minecraft:swamp")  ||
               s.equals("minecraft:beach")  ||
               s.equals("minecraft:birch_forest") ||
               s.equals("minecraft:birch_forest_hills") ||
               s.equals("minecraft:ocean") ||
               s.equals("minecraft:meadow") ||
               s.equals("forest") ||
               s.equals("flower_forest") ||
               s.equals("lukewarm_ocean") ||
               s.equals("grove") ||
               s.equals("snowy_plains") ||
               s.equals("warm_ocean") ;

    }
    
    private boolean isNegativeBiome(String biomeStringKey) {
        String s = biomeStringKey.toLowerCase();
        return s.equals("");
    }
    
    private List<HostileEntity> getNearbyEntities(PlayerEntity p, int radius) {
        var world = p.getWorld();
        var x = p.getX();
        var y = p.getY();
        var z = p.getZ();
        var box = new Box(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
        Predicate<HostileEntity> shouldAdd = HostileEntity::isAlive;
        return world.getEntitiesByClass(HostileEntity.class, box, shouldAdd);
    }
    private void decreaseRandomSanity(PlayerEntity p, int randomChance) {
        if (p.getWorld().getRandom().nextInt(randomChance) == 0) manager.decrease(p, 1, manager.SANITY);
    }

    private void increaseRandomSanity(PlayerEntity p, int randomChance) {
        if (p.getWorld().getRandom().nextInt(randomChance) == 0) manager.add(p, 1, manager.SANITY);
    }

    private boolean shouldPreventEffects(PlayerEntity p) {
        return p.isCreative() || p.isSpectator() || p.isDead() || p.isCreativeLevelTwoOp() || p.getAbilities().creativeMode;
    }



}
