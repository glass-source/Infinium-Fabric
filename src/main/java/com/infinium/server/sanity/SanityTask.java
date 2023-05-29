package com.infinium.server.sanity;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.effects.InfiniumEffects;
import com.infinium.server.sounds.InfiniumSounds;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

public class SanityTask {
    private final EntityAttributeModifier SANITY_HEALTH_BUFF = new EntityAttributeModifier(UUID.randomUUID(), "Sanity Health Buff", 4, EntityAttributeModifier.Operation.ADDITION);
    private final EntityAttributeModifier SANITY_HEALTH_DEBUFF = new EntityAttributeModifier(UUID.randomUUID(), "Sanity Health Debuff", -4, EntityAttributeModifier.Operation.ADDITION);
    private final SanityManager manager;
    public SanityTask(SanityManager manager) {
        this.manager = manager;
    }
    public void run() {
        Infinium.getInstance().getCore().getServer().getPlayerManager().getPlayerList().forEach(this::sanityTick);
    }
    private void sanityTick(PlayerEntity p) {
        if (shouldPreventEffects(p)) return;
        try {
            healthEffects(p);
            kairosEffects(p);
            soundEffects(p);
            biomeEffects(p);
            entityEffects(p);
            lightEffects(p);
            potionEffects(p);
            manager.syncSanity((ServerPlayerEntity) p, manager.get(p, manager.SANITY));
        } catch (Exception ex) {
            Infinium.getInstance().LOGGER.error("Hubo un error con la cordura! que raro...");
            ex.printStackTrace();
        }
    }
    private void potionEffects(PlayerEntity p) {
        int sanity = manager.get(p, manager.SANITY);
        var entityAttributeInstance = p.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (entityAttributeInstance == null) return;

        if (sanity <= 50) if (p.getRandom().nextInt(50) == 1) p.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 120, 2));
        if (sanity <= 40) if (p.getRandom().nextInt(50) == 1) p.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 120, 2));

        if (sanity <= 30) {
            if (p.getRandom().nextInt(30) == 1) {
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 120, 2));
            }

        }

        if (sanity <= 20) {
            if (p.getRandom().nextInt(20) == 1) {
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 120, 0));
                p.addStatusEffect(new StatusEffectInstance(InfiniumEffects.MADNESS, 120, 0));
                p.playSound(SoundEvents.ENTITY_GHAST_HURT, SoundCategory.AMBIENT, 1, 0.05f);
            }
        }

        if (sanity <= 10) {
            p.addStatusEffect(new StatusEffectInstance(InfiniumEffects.MADNESS, 120, 1));
            if (p.getRandom().nextInt(10) == 1) p.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 120, 2));
            if (p.getRandom().nextInt(10) == 1) p.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 120, 4));

        }

        if (sanity <= 5) {
            p.addStatusEffect(new StatusEffectInstance(InfiniumEffects.MADNESS, 120, 2));
        }

        if (sanity == 0) {
            if (p.getRandom().nextInt(50) == 1) p.damage(DamageSource.MAGIC, 2.0f);
        }
    }
    private void lightEffects(PlayerEntity p) {
        var world = p.getWorld();
        var blockPos = p.getBlockPos();
        var lightLevel = world.getLightLevel(blockPos);
        var lowCooldown = manager.get(p, manager.LOW_LIGHT_COOLDOWN);
        var highCooldown = manager.get(p, manager.HIGH_LIGHT_COOLDOWN);

        if (lightLevel < 8) manager.decrease(p, 1, manager.LOW_LIGHT_COOLDOWN);
        else manager.decrease(p, 1, manager.HIGH_LIGHT_COOLDOWN);

        if (lowCooldown <= 0) {
            manager.set(p, 35 - lightLevel, manager.LOW_LIGHT_COOLDOWN);
            manager.decrease(p, 1, manager.SANITY);
        }

        if (highCooldown <= 0) {
            manager.set(p, 35 + lightLevel, manager.HIGH_LIGHT_COOLDOWN);
            manager.add(p, 1, manager.SANITY);
        }

    }
    private void biomeEffects(PlayerEntity p) {
        var world = p.getWorld();
        var blockPos = p.getBlockPos();
        var biome = world.getBiome(blockPos);
        var biomeKey = biome.getKey().isPresent() ? biome.getKey().get().getValue().toString() : "";
        var cooldown = manager.get(p, manager.BIOME_COOLDOWN);

        manager.decrease(p, 1, manager.BIOME_COOLDOWN);

        if (cooldown <= 0) {
            manager.set(p, 25, manager.BIOME_COOLDOWN);
            manager.add(p, getBiomeLevel(biomeKey), manager.SANITY);
        }
    }
    private void entityEffects(PlayerEntity p) {
        var list = getNearbyEntities(p);
        var cooldown = manager.get(p, manager.ENTITY_COOLDOWN);
        var maxSize = Math.min(list.size(), 5);
        manager.decrease(p, maxSize, manager.ENTITY_COOLDOWN);

        if (cooldown <= 0) {
            manager.set(p, 40, manager.ENTITY_COOLDOWN);
            manager.decrease(p, 2, manager.SANITY);
        }
    }
    private void healthEffects(PlayerEntity p) {
        var entityAttributeInstance = p.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (entityAttributeInstance == null) return;
        double health = p.getHealth();
        double hunger = p.getHungerManager().getFoodLevel();
        int sanity = manager.get(p, manager.SANITY);

        if (sanity >= 85) {
            if (!entityAttributeInstance.hasModifier(SANITY_HEALTH_BUFF)) entityAttributeInstance.addTemporaryModifier(SANITY_HEALTH_BUFF);

        } else {
            if (entityAttributeInstance.hasModifier(SANITY_HEALTH_BUFF)) {
                entityAttributeInstance.removeModifier(SANITY_HEALTH_BUFF);
                p.setHealth(p.getHealth());
            }
        }

        if (sanity > 10) {
            if (entityAttributeInstance.hasModifier(SANITY_HEALTH_DEBUFF)) {
                entityAttributeInstance.removeModifier(SANITY_HEALTH_DEBUFF);
                p.setHealth(p.getHealth());
            }
        } else {
            if (!entityAttributeInstance.hasModifier(SANITY_HEALTH_DEBUFF)) entityAttributeInstance.addTemporaryModifier(SANITY_HEALTH_DEBUFF);
        }

        if (hunger >= 16.0) {
            manager.decrease(p, 1, manager.FULL_HUNGER_COOLDOWN);

        } else {
            manager.decrease(p, 1, manager.EMPTY_HUNGER_COOLDOWN);
        }

        if (health >= 18.0) {
            manager.decrease(p, 1, manager.POSITIVE_HEALTH_COOLDOWN);

        } else  {
            manager.decrease(p, 1, manager.NEGATIVE_HEALTH_COOLDOWN);
        }

        if (manager.get(p, manager.POSITIVE_HEALTH_COOLDOWN) <= 0) {
            manager.add(p, 1, manager.SANITY);
            manager.set(p, 40, manager.POSITIVE_HEALTH_COOLDOWN);
        }

        if (manager.get(p, manager.NEGATIVE_HEALTH_COOLDOWN) <= 0) {
            manager.decrease(p, 2, manager.SANITY);
            manager.set(p, 20, manager.NEGATIVE_HEALTH_COOLDOWN);
        }

        if (manager.get(p, manager.FULL_HUNGER_COOLDOWN) <= 0) {
            manager.decrease(p, 1, manager.SANITY);
            manager.set(p, 40, manager.FULL_HUNGER_COOLDOWN);
        }

        if (manager.get(p, manager.EMPTY_HUNGER_COOLDOWN) <= 0) {
            manager.decrease(p, 2, manager.SANITY);
            manager.set(p, 20, manager.EMPTY_HUNGER_COOLDOWN);
        }
    }
    private void kairosEffects(PlayerEntity p) {
        int timeCooldownSeconds = manager.get(p, manager.TIME_COOLDOWN);
        var timeCooldownMinutes = timeCooldownSeconds % 3600 / 60;
        var formattedSeconds = timeCooldownSeconds % 60;
        var color = "&6";

        if (timeCooldownSeconds < 150) {
            int value = manager.get(p, "color");
            manager.set(p, value == 0 ? 1 : 0, "color");
            color = value == 0 ? "&4" : "&r";
        }

        var msg = ChatFormatter.text("&7[" + color + String.format("%02d:%02d", timeCooldownMinutes, formattedSeconds) + "&7]");

        if (timeCooldownSeconds > 0) manager.decrease (p, 1, manager.TIME_COOLDOWN);
        if (timeCooldownSeconds < 150) {
            manager.decrease(p, 2, manager.SANITY);
        }
        p.sendMessage(msg, true);
    }
    private void soundEffects(PlayerEntity p) {
        int cooldown = manager.get(p, manager.SOUND_COOLDOWN);
        int sanity = manager.get(p, manager.SANITY);
        if (sanity >= 80) return;

        manager.decrease(p, 1, manager.SOUND_COOLDOWN);

        if (cooldown < 0) {
            var sound = getMoodSoundEvent(p);
            float pitch = 1;
            if (sound.equals(InfiniumSounds.PLAYER_DEATH)) pitch = 0.7f;
            if (sound.equals(InfiniumSounds.ECLIPSE_START)) pitch = 0.5f;
            p.playSound(getMoodSoundEvent(p), SoundCategory.AMBIENT, 1, pitch);
            manager.set(p, 300 + p.getRandom().nextInt(40), manager.SOUND_COOLDOWN);
        }
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
                    -> {return -1;}

            case
            "minecraft:soul_sand_valley",
                    "minecraft:the_end",
                    "minecraft:small_end_islands",
                    "minecraft:end_midlands",
                    "minecraft:end_highlands",
                    "minecraft:end_barrens",
                    "infinium:the_void",
                    "infinium:the_nightmare"
                    -> {return -2;}
        }
    }
    private SoundEvent getMoodSoundEvent(PlayerEntity p) {
        if (new Random().nextInt(20) == 1) return InfiniumSounds.SANITY_VOICE_INVITE;
        int sanity = manager.get(p, manager.SANITY);

        SoundEvent sound = SoundEvents.AMBIENT_NETHER_WASTES_MOOD;
        if (sanity <= 80) sound = InfiniumSounds.LOW_SANITY_1;
        if (sanity <= 70) sound = InfiniumSounds.LOW_SANITY_2;
        if (sanity <= 60) sound = InfiniumSounds.LOW_SANITY_3;
        if (sanity <= 50) sound = InfiniumSounds.LOW_SANITY_4;
        if (sanity <= 30) sound = p.getRandom().nextBoolean() ? InfiniumSounds.ECLIPSE_START : InfiniumSounds.LOW_SANITY_5;
        if (sanity <= 20) sound = p.getRandom().nextBoolean() ? InfiniumSounds.ECLIPSE_START : InfiniumSounds.LOW_SANITY_6;
        if (sanity <= 10) sound = p.getRandom().nextBoolean() ? InfiniumSounds.PLAYER_DEATH : InfiniumSounds.LOW_SANITY_7;
        return sound;
    }
    private List<HostileEntity> getNearbyEntities(PlayerEntity p) {
        var world = p.getWorld();
        var x = p.getX();
        var y = p.getY();
        var z = p.getZ();
        var box = new Box(x - 7, y - 7, z - 7, x + 7, y + 7, z + 7);
        Predicate<HostileEntity> shouldAdd = HostileEntity::isAlive;
        return world.getEntitiesByClass(HostileEntity.class, box, shouldAdd);
    }
    private boolean shouldPreventEffects(PlayerEntity p) {
        return p.isCreative() || p.isSpectator() || p.isDead();
    }


}
