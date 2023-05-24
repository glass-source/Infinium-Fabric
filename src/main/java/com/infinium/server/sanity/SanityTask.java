package com.infinium.server.sanity;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.sounds.InfiniumSounds;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Box;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

public class SanityTask {
    private final EntityAttributeModifier EXTRA_HEALTH_BOOST = new EntityAttributeModifier(UUID.randomUUID(), "Sanity Health boost", 4, EntityAttributeModifier.Operation.ADDITION);
    private final SanityManager manager;
    public SanityTask(SanityManager manager) {
        this.manager = manager;
    }
    
    public void run() {
        Infinium.getInstance().getCore().getServer().getPlayerManager().getPlayerList().forEach(this::sanityEffects);
    }
    private void sanityEffects(PlayerEntity p) {
        if (shouldPreventEffects(p)) return;
        try {

            healthEffects(p);
            kairosEffects(p);
            soundEffects(p);
            biomeEffects(p);
            entityEffects(p);
            lightEffects(p);
            manager.syncSanity((ServerPlayerEntity) p, manager.get(p, manager.SANITY));
        } catch (Exception ex) {
            Infinium.getInstance().LOGGER.error("Hubo un error con la cordura! que raro...");
            ex.printStackTrace();
        }
    }
    private void lightEffects(PlayerEntity p) {
        var world = p.getWorld();
        var blockPos = p.getBlockPos();
        var lightLevel = Math.max(1, world.getLightLevel(blockPos));
        if (lightLevel > 7) manager.add(p, 1, manager.SOUND_POINTS);
        else manager.decrease(p, 1, manager.SOUND_POINTS);
    }
    private void biomeEffects(PlayerEntity p) {
        var world = p.getWorld();
        var blockPos = p.getBlockPos();
        var biome = world.getBiome(blockPos);
        var biomeString = biome.getKey().isPresent() ? biome.getKey().get().getValue().toString() : "";
        int biomeLevel = getBiomeLevel(biomeString);

        switch (biomeLevel) {
            case 1 -> manager.add(p, 1, manager.SOUND_POINTS);
            case 4, 6 -> manager.decrease(p, 1 , manager.SOUND_POINTS);
        }
    }

    private void entityEffects(PlayerEntity p) {
        var list = getNearbyEntities(p);
        manager.decrease(p, list.size(), manager.SOUND_POINTS);
    }
    private void healthEffects(PlayerEntity p) {
        var entityAttributeInstance = p.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (entityAttributeInstance == null) return;
        double health = p.getHealth();
        double hunger = p.getHungerManager().getFoodLevel();
        int sanity = manager.get(p, manager.SANITY);

        if (sanity >= 85) {
            if (!entityAttributeInstance.hasModifier(EXTRA_HEALTH_BOOST)) entityAttributeInstance.addTemporaryModifier(EXTRA_HEALTH_BOOST);

        } else {

            if (entityAttributeInstance.hasModifier(EXTRA_HEALTH_BOOST)) {
                entityAttributeInstance.removeModifier(EXTRA_HEALTH_BOOST);
                p.setHealth(p.getHealth());
            }

        }

        if (hunger >= 16.0) {
            manager.decrease(p, 1, manager.FULL_HUNGER_COOLDOWN);
            manager.add(p, 1, manager.SOUND_POINTS);

        } else {
            manager.decrease(p, 1, manager.EMPTY_HUNGER_COOLDOWN);
            manager.decrease(p, 1, manager.SOUND_POINTS);
        }

        if (health >= 16.0) {
            manager.decrease(p, 1, manager.POSITIVE_HEALTH_COOLDOWN);
            manager.add(p, 1, manager.SOUND_POINTS);

        } else  {
            manager.decrease(p, 1, manager.NEGATIVE_HEALTH_COOLDOWN);
            manager.decrease(p, 1, manager.SOUND_POINTS);
        }

        if (manager.get(p, manager.POSITIVE_HEALTH_COOLDOWN) <= 0) {
            increaseRandomSanity(p, 1, 1);
            manager.add(p, 1, manager.SOUND_POINTS);
            manager.set(p, 20, manager.POSITIVE_HEALTH_COOLDOWN);
        }

        if (manager.get(p, manager.NEGATIVE_HEALTH_COOLDOWN) <= 0) {
            decreaseRandomSanity(p, 1, 1);
            manager.decrease(p, 1 , manager.SOUND_POINTS);
            manager.set(p, 20, manager.NEGATIVE_HEALTH_COOLDOWN);
        }

        if (manager.get(p, manager.FULL_HUNGER_COOLDOWN) <= 0) {
            decreaseRandomSanity(p, 1, 1);
            manager.decrease(p, 1 , manager.SOUND_POINTS);
            manager.set(p, 20, manager.FULL_HUNGER_COOLDOWN);
        }

        if (manager.get(p, manager.EMPTY_HUNGER_COOLDOWN) <= 0) {
            decreaseRandomSanity(p, 1, 1);
            manager.decrease(p, 1 , manager.SOUND_POINTS);
            manager.set(p, 20, manager.EMPTY_HUNGER_COOLDOWN);
        }
    }
    private void kairosEffects(PlayerEntity p) {
        int timeCooldownSeconds = manager.get(p, manager.TIME_COOLDOWN);
        var timeCooldownMinutes = timeCooldownSeconds % 3600 / 60;
        var formattedSeconds = timeCooldownSeconds % 60;
        var color = timeCooldownSeconds <= 150 ? "&4" : "&6";
        var msg = ChatFormatter.text("&7[" + color + String.format("%02d:%02d", timeCooldownMinutes, formattedSeconds) + "&7]");

        if (timeCooldownSeconds > 0) manager.decrease (p, 1, manager.TIME_COOLDOWN);
        if (timeCooldownSeconds <= 150) {
            decreaseRandomSanity(p, 5,3);
            manager.decrease(p, 5, manager.SOUND_POINTS);
        }
        p.sendMessage(msg, true);
    }

    private void soundEffects(PlayerEntity p) {
        int cooldown = manager.get(p, manager.SOUND_COOLDOWN);
        int sanity = Math.max(1, manager.get(p, manager.SANITY));

        if (sanity > 60) manager.add(p, 1, manager.SOUND_POINTS);
        else manager.decrease(p, 1, manager.SOUND_POINTS);

        manager.decrease(p, 1, manager.SOUND_COOLDOWN);

        if (cooldown < 0) {
            p.playSound(getMoodSoundEvent(p), SoundCategory.AMBIENT, 1, 1);
            manager.set(p, 240, manager.SOUND_COOLDOWN);
            manager.set(p, 0, manager.SOUND_POINTS);
        }
    }

    private SoundEvent getMoodSoundEvent(PlayerEntity p) {
        if (new Random().nextInt(15) == 1) return InfiniumSounds.SANITY_VOICE_INVITE;

        int soundPoints = manager.get(p, manager.SOUND_POINTS);
        SoundEvent sound;

        if (soundPoints >= 40) {

            manager.add(p, 3, manager.SANITY);
            sound = InfiniumSounds.LOW_SANITY_1;

        } else if (soundPoints >= 25) {

            manager.add(p, 2, manager.SANITY);
            sound = InfiniumSounds.LOW_SANITY_2;

        } else if (soundPoints >= 15) {

            manager.add(p, 1, manager.SANITY);
            sound = InfiniumSounds.LOW_SANITY_3;

        } else if (soundPoints >= 0) {

            manager.add(p, 1, manager.SANITY);
            sound = InfiniumSounds.LOW_SANITY_4;

        } else if (soundPoints >= -25) {

            manager.decrease(p, 1, manager.SANITY);
            sound = InfiniumSounds.LOW_SANITY_5;

        } else if (soundPoints >= -50) {

            manager.decrease(p, 2, manager.SANITY);
            sound = InfiniumSounds.LOW_SANITY_6;

        } else if (soundPoints >= -70){
            manager.decrease(p, 3, manager.SANITY);
            sound = InfiniumSounds.LOW_SANITY_7;

        } else {
            manager.decrease(p, 6, manager.SANITY);
            sound = InfiniumSounds.LOW_SANITY_8;
        }

        return sound;
    }

    private int getBiomeLevel(String biomeKey) {
        String s = biomeKey.toLowerCase();

        switch (s) {

            default -> {return 1;}

            case
            "minecraft:dripstone_caves",
            "minecraft:warped_forest",
            "minecraft:jungle",
            "minecraft:crimson_forest",
            "minecraft:basalt_deltas",
            "minecraft:lush_caves"
            -> {return 4;}

            case
            "minecraft:soul_sand_valley",
            "minecraft:the_end",
            "minecraft:small_end_islands",
            "minecraft:end_midlands",
            "minecraft:end_highlands",
            "minecraft:end_barrens",
            "infinium:the_void",
            "infinium:the_nightmare"
            -> {return 6;}
        }
    }

    private List<HostileEntity> getNearbyEntities(PlayerEntity p) {
        var world = p.getWorld();
        var x = p.getX();
        var y = p.getY();
        var z = p.getZ();
        var box = new Box(x - 14, y - 14, z - 14, x + 14, y + 14, z + 14);
        Predicate<HostileEntity> shouldAdd = HostileEntity::isAlive;
        return world.getEntitiesByClass(HostileEntity.class, box, shouldAdd);
    }
    private void decreaseRandomSanity(PlayerEntity p, int randomChance, int amount) {
        if (p.getWorld().getRandom().nextInt(randomChance) + 1 == 1) manager.decrease(p, amount, manager.SANITY);
    }

    private void increaseRandomSanity(PlayerEntity p, int randomChance, int amount) {
        if (p.getWorld().getRandom().nextInt(randomChance) + 1 == 1) manager.add(p, amount, manager.SANITY);
    }

    private boolean shouldPreventEffects(PlayerEntity p) {
        return p.isCreative() || p.isSpectator() || p.isDead();
    }



}
