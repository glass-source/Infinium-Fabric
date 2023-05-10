package com.infinium.server.listeners.entity;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.eclipse.SolarEclipseManager;
import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.events.entity.EntitySpawnEvent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.Random;


public class EntitySpawnListeners {

    private final InfiniumServerManager core;
    private final SolarEclipseManager solarEclipseManager;

    public EntitySpawnListeners(Infinium instance){
        this.core = instance.getCore();
        this.solarEclipseManager = this.core.getEclipseManager();
    }

    public void registerListeners() {
        entitySpawnCallback();
        solarEclipseEntitySpawnCallback();

    }
    private void entitySpawnCallback(){
        EntitySpawnEvent.EVENT.register((entity) -> {
            var livingEntity = entity instanceof LivingEntity ? (LivingEntity) entity : entity;
            if (livingEntity.getWorld().isClient) return ActionResult.FAIL;
            if (Infinium.getInstance().getDateUtils() == null) return ActionResult.FAIL;

            var day = Infinium.getInstance().getDateUtils().getCurrentDay();
            var world = (ServerWorld) entity.getWorld();
            var blockPos = entity.getBlockPos();
            var entityTypeString = entity.getType().toString();

            switch (world.getRegistryKey().getValue().toString()) {

                case "minecraft:overworld" -> {

                    if (day >= 7 && day < 14) {

                        if (new Random().nextInt(10) <= 8) {
                            assert livingEntity instanceof LivingEntity;
                            switch (entityTypeString) {

                                case "entity.minecraft.creeper" -> {
                                    assert livingEntity instanceof CreeperEntity;
                                    var creeper = (CreeperEntity) livingEntity;
                                    creeper.setCustomName(ChatFormatter.text("&6Charged Creeper"));
                                    creeper.onStruckByLightning(world, null);
                                    creeper.setFireTicks(0);
                                }

                                case "entity.minecraft.skeleton" -> {
                                    assert livingEntity instanceof SkeletonEntity;
                                    var skelly = (SkeletonEntity) livingEntity;
                                    var bow = Items.BOW.getDefaultStack();
                                    bow.addEnchantment(Enchantments.POWER, 25);
                                    skelly.setStackInHand(Hand.MAIN_HAND, bow);
                                    skelly.setEquipmentDropChance(EquipmentSlot.MAINHAND,0);
                                    skelly.setCustomName(ChatFormatter.text("&6Power XXV Skeleton"));
                                }

                                case "entity.minecraft.evoker" -> spawnMobFromEntity((LivingEntity) livingEntity, InfiniumEntityType.EXPLOSIVE_SORCERER, blockPos);
                                case "entity.minecraft.vindicator" -> spawnMobFromEntity((LivingEntity) livingEntity, InfiniumEntityType.BERSERKER, blockPos);
                                case "entity.minecraft.pillager" -> spawnMobFromEntity((LivingEntity) livingEntity, InfiniumEntityType.RAIDER, blockPos);
                            }
                        } else {
                            assert livingEntity instanceof LivingEntity;
                            switch (entityTypeString) {
                                case "entity.minecraft.evoker" -> spawnMobFromEntity((LivingEntity) livingEntity, InfiniumEntityType.EXPLOSIVE_SORCERER, blockPos);
                                case "entity.minecraft.vindicator" -> spawnMobFromEntity((LivingEntity) livingEntity, InfiniumEntityType.BERSERKER, blockPos);
                                case "entity.minecraft.pillager" -> spawnMobFromEntity((LivingEntity) livingEntity, InfiniumEntityType.RAIDER, blockPos);
                                case "entity.minecraft.zombie" -> spawnMobFromEntity((LivingEntity) livingEntity, InfiniumEntityType.GHOUL_ZOMBIE, blockPos);
                                case "entity.minecraft.spider" -> spawnMobFromEntity((LivingEntity) livingEntity, InfiniumEntityType.GHOUL_SPIDER, blockPos);

                            }
                        }


                    }

                    if (day >= 14) {
                        assert livingEntity instanceof LivingEntity;
                        switch (entityTypeString) {

                            case "entity.minecraft.evoker" -> spawnMobFromEntity((LivingEntity) livingEntity, InfiniumEntityType.EXPLOSIVE_SORCERER, blockPos);
                            case "entity.minecraft.vindicator" -> spawnMobFromEntity((LivingEntity) livingEntity, InfiniumEntityType.BERSERKER, blockPos);
                            case "entity.minecraft.pillager" -> spawnMobFromEntity((LivingEntity) livingEntity, InfiniumEntityType.RAIDER, blockPos);
                            case "entity.minecraft.zombie" -> spawnMobFromEntity((LivingEntity) livingEntity, InfiniumEntityType.GHOUL_ZOMBIE, blockPos);
                            case "entity.minecraft.spider" -> spawnMobFromEntity((LivingEntity) livingEntity, InfiniumEntityType.GHOUL_SPIDER, blockPos);

                            case "entity.minecraft.glow_squid"
                               , "entity.minecraft.squid"-> {
                                assert livingEntity instanceof SquidEntity;
                                var nuclearBomb = (SquidEntity) livingEntity;
                                nuclearBomb.setCustomName(ChatFormatter.text("&6BOMBA NUCLEAR"));
                                nuclearBomb.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 3));
                                nuclearBomb.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, Integer.MAX_VALUE, 9));
                            }
                        }
                    }

                }

                case "minecraft:the_nether" -> {

                }

                case "minecraft:the_end" -> {


                }

                case "infinium:the_void" -> {

                }
                case "infinium:nightmare" -> {

                }

            }

            return ActionResult.PASS;
        });
    }

    private void solarEclipseEntitySpawnCallback(){
        EntitySpawnEvent.EVENT.register((entity) -> {

            if (!(entity instanceof LivingEntity livingEntity)) return ActionResult.FAIL;
            if (livingEntity.getWorld().isClient || !solarEclipseManager.isActive()) return ActionResult.PASS;
            if (Infinium.getInstance().getDateUtils() == null) return ActionResult.FAIL;
            var day = Infinium.getInstance().getDateUtils().getCurrentDay();
            var world = (ServerWorld) livingEntity.getWorld();
            var blockPos = livingEntity.getBlockPos();
            var entityTypeString =  livingEntity.getType().toString();

            switch (world.getRegistryKey().getValue().toString()) {

                case "minecraft:overworld" -> {

                    if (day >= 7 && day < 14) {

                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 0));
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 0));

                        if (new Random().nextInt(10) >= 7) {
                            //TODO custom mobcap (idk if it's actually necessary)

                            switch (entityTypeString) {

                                case "entity.minecraft.spider"
                                   , "entity.minecraft.cave_spider"-> spawnMobFromEntity(livingEntity, InfiniumEntityType.GHOUL_SPIDER, blockPos);

                                case "entity.minecraft.zombie" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.GHOUL_ZOMBIE, blockPos);

                                case "entity.minecraft.skeleton" -> {

                                }


                            }

                        }


                    }
                }

                case "minecraft:the_nether" -> {
                    if (day >= 7 && day < 14) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 0));
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 0));

                        switch (entityTypeString) {

                            case "entity.minecraft.wither_skeleton" -> {
                                var wither_skeleton = (WitherSkeletonEntity) entity;
                                var bow = Items.BOW.getDefaultStack();
                                bow.addEnchantment(Enchantments.POWER, 40);
                                wither_skeleton.setStackInHand(Hand.MAIN_HAND, bow);
                                wither_skeleton.setEquipmentDropChance(EquipmentSlot.MAINHAND,0);
                            }
                        }

                    }
                }

                case "minecraft:the_end" -> {
                    if (day >= 7 && day < 14) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 0));
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 0));



                    }

                }

                case "infinium:the_void" -> {
                    if (day >= 7 && day < 14) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 0));
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 0));



                    }
                }

                case "infinium:nightmare" -> {
                    if (day >= 7 && day < 14) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 0));
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 0));



                    }
                }

            }

            return ActionResult.PASS;
        });
    }

    private void spawnMobFromEntity(LivingEntity entityToRemove, EntityType<?> typeToReplace, BlockPos pos) {
        entityToRemove.remove(Entity.RemovalReason.DISCARDED);
        typeToReplace.spawn(entityToRemove.getCommandSource().getWorld(), null, null, null, pos, SpawnReason.NATURAL, true, false);
    }

    private void createExplosionFromEntity(@Nullable Entity entity, World world, BlockPos position, float explosionPower, boolean breakBlocks) {
        world.createExplosion(entity, position.getX(), position.getY(), position.getZ(), explosionPower,  breakBlocks ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE);
    }

}
