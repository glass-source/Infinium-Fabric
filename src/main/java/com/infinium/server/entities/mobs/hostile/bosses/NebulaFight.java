package com.infinium.server.entities.mobs.hostile.bosses;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class NebulaFight {

    private boolean isRunning = false;
    private int attackCooldown = 200;
    private final EnderDragonEntity mob;
    private NebulaAttacks lastAttack;
    private int lastAttackIndex = 0;
    private boolean canAttack = true;
    private ScheduledFuture<?> fightTask;
    private ScheduledFuture<?> attackTask;
    private BlockPos beaconBlockPos;
    public NebulaFight(EnderDragonEntity mob) {
        this.mob = mob;
    }
    public void stop() {
        isRunning = false;
        attackTask.cancel(true);
        attackTask = null;
        fightTask.cancel(true);
        fightTask = null;
        attackCooldown = 0;
        lastAttackIndex = 0;
        canAttack = false;
        beaconBlockPos = null;
        lastAttack = null;
        mob.kill();
    }
    public void start() {
        isRunning = true;
        ChatFormatter.broadcastMessage("started");
        var instance = Infinium.getInstance();
        instance.getExecutor().scheduleAtFixedRate(() -> {
            if (canAttack) attackCooldown--;
            if (attackCooldown <= 0) {
                attack();
            }

            if (mob.isDead() || !mob.isAlive() || mob.getHealth() <= 0 || mob.deathTime >= 20) {
                stop();
            }

        }, 0, 50, TimeUnit.MILLISECONDS);
    }
    private void attack() {
        var instance = Infinium.getInstance();
        NebulaAttacks currentAttack;
        switch (lastAttackIndex)  {
            case 0 -> currentAttack = NebulaAttacks.NEGATIVE_EFFECTS;
            case 1 -> currentAttack = NebulaAttacks.RANDOM_TELEPORT;
            case 2 -> currentAttack = NebulaAttacks.EFFECT_CLOUD;
            case 3 -> currentAttack = NebulaAttacks.BEACON;
            default -> currentAttack = NebulaAttacks.RANDOM_ATTACK;
        }

        if (currentAttack == NebulaAttacks.RANDOM_ATTACK) {
            NebulaAttacks[] attacks = NebulaAttacks.values();
            currentAttack = attacks[new Random().nextInt(attacks.length)];
        }

        switch (currentAttack) {
            case NEGATIVE_EFFECTS -> {
                var core = instance.getCore();
                var manager = core.getSanityManager();
                core.getTotalPlayers().forEach(serverPlayerEntity -> {
                    manager.decrease(serverPlayerEntity, 10, manager.SANITY);
                    serverPlayerEntity.clearStatusEffects();
                    serverPlayerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 2), mob);
                    serverPlayerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 200, 0), mob);
                });
            }

            case EFFECT_CLOUD -> instance.getCore().getTotalPlayers().forEach(player -> {
                AreaEffectCloudEntity entity = EntityType.AREA_EFFECT_CLOUD.create(player.getWorld());
                assert entity != null;
                entity.refreshPositionAndAngles(player.getBlockPos(), 0.0f, 0.0f);
                player.getWorld().spawnNewEntityAndPassengers(entity);
                entity.setOwner(mob);
                entity.setRadius(2f);
                entity.setDuration(220);
                entity.setParticleType(ParticleTypes.SMOKE);
                entity.addEffect(new StatusEffectInstance(StatusEffects.WITHER, 60, 2));
                entity.addEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 2));
                entity.addEffect(new StatusEffectInstance(StatusEffects.HUNGER, 60, 2));
            });

            case RANDOM_TELEPORT -> {
                var playerList = instance.getCore().getTotalPlayers();
                int[] counter = {0};
                playerList.forEach(player -> {
                    int j = mob.getRandom().nextInt(counter[0] + 1);
                    var randomPlayer = playerList.get(j);
                    var x = randomPlayer.getX();
                    var y = randomPlayer.getY();
                    var z = randomPlayer.getZ();
                    player.teleport(x, y, z);
                    counter[0]++;
                });
            }

            case BEACON -> {
                canAttack = false;
                var world = mob.getWorld();
                var random = mob.getRandom();
                var x = random.nextInt(50) * (random.nextBoolean() ? -1 : 1);
                var z = random.nextInt(50) * (random.nextBoolean() ? -1 : 1);
                var y = instance.getCore().getHighestY(world, x, z);
                beaconBlockPos = new BlockPos(x, y, z);
                world.setBlockState(beaconBlockPos, Blocks.BEACON.getDefaultState());
                ChatFormatter.broadcastMessageWithPrefix("Beacon in: " + x + " " + y + " " + z);

                attackTask = instance.getExecutor().schedule(() -> {

                    if (world.getBlockState(beaconBlockPos) == Blocks.BEACON.getDefaultState()) {
                        var playerList = instance.getCore().getTotalPlayers();
                        playerList.forEach(player -> player.damage(DamageSource.GENERIC, 9999));
                        ChatFormatter.broadcastMessageWithPrefix("El Beacon ha explotado!");
                    }
                    canAttack = true;
                    world.setBlockState(beaconBlockPos, Blocks.AIR.getDefaultState());
                    beaconBlockPos = null;
                }, 25, TimeUnit.SECONDS);
            }
        }

        this.lastAttack = currentAttack;
        attackCooldown = 160 + mob.getRandom().nextInt(40);
        this.lastAttackIndex++;
        if (lastAttackIndex > NebulaAttacks.values().length - 1) this.lastAttackIndex = 0;
        playSound();
    }
    private void playSound() {
        SoundEvent event;
        switch (this.lastAttack) {
            case NEGATIVE_EFFECTS -> event = SoundEvents.ENTITY_WITHER_HURT;
            case EFFECT_CLOUD -> event = SoundEvents.ENTITY_SPLASH_POTION_BREAK;
            case RANDOM_TELEPORT -> event = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
            case BEACON -> event = SoundEvents.BLOCK_WOOD_BREAK;
            default -> event = SoundEvents.BLOCK_AMETHYST_BLOCK_FALL;
        }

        Infinium.getInstance().getCore().getTotalPlayers().forEach(p -> p.playSound(event, SoundCategory.AMBIENT, 5, 0.3f));
    }
    public MobEntity getMob() {
        return mob;
    }
    public NebulaFight getNebulaFight(EnderDragonEntity mob) {
        if (this.mob == mob) return this;
        return null;
    }

    public boolean isRunning() {
        return this.isRunning;
    }
    private enum NebulaAttacks {
        NEGATIVE_EFFECTS(),
        EFFECT_CLOUD(),
        RANDOM_TELEPORT(),
        BEACON(),
        RANDOM_ATTACK()
        ;

        NebulaAttacks() {

        }
    }

}
