package com.infinium.server.listeners.entity;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.eclipse.SolarEclipseManager;
import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.entities.mobs.hostile.bosses.SuperNovaEntity;
import com.infinium.server.entities.mobs.hostile.raidmobs.RaiderEntity;
import com.infinium.server.events.entity.EntitySpawnEvent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
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

import java.util.Random;


public class EntitySpawnListeners {

    private final SolarEclipseManager solarEclipseManager;
    public EntitySpawnListeners(Infinium instance){
        this.solarEclipseManager = instance.getCore().getEclipseManager();
    }
    public void registerListeners() {
        entitySpawnCallback();
        solarEclipseEntitySpawnCallback();
    }
    private void entitySpawnCallback(){
        EntitySpawnEvent.EVENT.register((entity) -> {
            if (!(entity instanceof LivingEntity livingEntity)) return ActionResult.PASS;
            if (livingEntity.getWorld().isClient) return ActionResult.PASS;
            if (Infinium.getInstance().getDateUtils() == null) return ActionResult.PASS;
            var day = Infinium.getInstance().getDateUtils().getCurrentDay();
            var world = (ServerWorld) entity.getWorld();
            var entityTypeString = entity.getType().toString();

            if (entity instanceof SuperNovaEntity superNovaEntity) superNovaEntity.onSummoned();

            else switch (world.getRegistryKey().getValue().toString()) {

                case "minecraft:overworld" -> {

                    if (day >= 7 && day < 14) {

                        if (new Random().nextInt(10) <= 7) {
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

                                case "entity.minecraft.evoker" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.EXPLOSIVE_SORCERER);
                                case "entity.minecraft.vindicator" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.BERSERKER);
                                case "entity.minecraft.pillager" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.RAIDER);
                                case "entity.minecraft.witch" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.GHOUL_WITCH);
                                case "entity.minecraft.hoglin" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.NIGHTMARE_HOGLIN);
                                case "entity.minecraft.piglin_brute"
                                        ,"entity.minecraft.piglin"
                                        ,"entity.minecraft.zombified_piglin"   -> spawnMobFromEntity(livingEntity, InfiniumEntityType.NIGHTMARE_BRUTE);
                            }
                        } else {
                            switch (entityTypeString) {
                                case "entity.minecraft.evoker" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.EXPLOSIVE_SORCERER);
                                case "entity.minecraft.vindicator" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.BERSERKER);
                                case "entity.minecraft.pillager" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.RAIDER);
                                case "entity.minecraft.witch" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.GHOUL_WITCH);
                                case "entity.minecraft.zombie" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.GHOUL_ZOMBIE);
                                case "entity.minecraft.spider" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.GHOUL_SPIDER);
                                case "entity.minecraft.creeper" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.GHOUL_CREEPER);
                                case "entity.minecraft.skeleton" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.NIGHTMARE_SKELETON);
                                case "entity.minecraft.hoglin" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.NIGHTMARE_HOGLIN);
                                case "entity.minecraft.piglin_brute"
                                    ,"entity.minecraft.piglin"
                                    ,"entity.minecraft.zombified_piglin"   -> spawnMobFromEntity(livingEntity, InfiniumEntityType.NIGHTMARE_BRUTE);
                            }
                        }


                    }

                    if (day >= 14) {
                        switch (entityTypeString) {

                            case "entity.minecraft.evoker" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.EXPLOSIVE_SORCERER);
                            case "entity.minecraft.vindicator" -> spawnMobFromEntity( livingEntity, InfiniumEntityType.BERSERKER);
                            case "entity.minecraft.pillager" -> spawnMobFromEntity( livingEntity, InfiniumEntityType.RAIDER);
                            case "entity.minecraft.zombie" -> spawnMobFromEntity( livingEntity, InfiniumEntityType.GHOUL_ZOMBIE);
                            case "entity.minecraft.spider" -> spawnMobFromEntity( livingEntity, InfiniumEntityType.GHOUL_SPIDER);
                            case "entity.minecraft.creeper" -> spawnMobFromEntity( livingEntity, InfiniumEntityType.GHOUL_CREEPER);
                            case "entity.minecraft.skeleton" -> spawnMobFromEntity( livingEntity, InfiniumEntityType.NIGHTMARE_SKELETON);
                            case "entity.minecraft.witch" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.GHOUL_WITCH);
                            case "entity.minecraft.hoglin" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.NIGHTMARE_HOGLIN);
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
                    if (day >= 7) {
                        if (new Random().nextInt(10) >= 7) spawnNightmareMobs(livingEntity, entityTypeString);
                    }

                    if (day >= 14) {
                        spawnNightmareMobs(livingEntity, entityTypeString);
                    }
                }

                case "minecraft:the_end" -> {

                    if (entityTypeString.equals("entity.minecraft.enderman")) {
                        spawnRandomVoidMob(livingEntity);
                    }
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
            var entityTypeString =  livingEntity.getType().toString();

            switch (world.getRegistryKey().getValue().toString()) {

                case "minecraft:overworld", "minecraft:the_end", "infinium:nightmare", "infinium:the_void" -> {

                    if (day >= 7 && day < 14) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 0));
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 0));
                    }

                    if (day >= 14) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 1));
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 1));
                    }
                }
                case "minecraft:the_nether" -> {
                    if (day >= 7 && day < 14) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 0));
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 0));

                        if (entityTypeString.equals("entity.minecraft.wither_skeleton")) {
                            var wither_skeleton = (WitherSkeletonEntity) entity;
                            var bow = Items.BOW.getDefaultStack();
                            bow.addEnchantment(Enchantments.POWER, 40);
                            wither_skeleton.setStackInHand(Hand.MAIN_HAND, bow);
                            wither_skeleton.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0);
                        }
                    }

                    if (day >= 14) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 1));
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 1));
                    }

                }

            }

            return ActionResult.PASS;
        });
    }

    private void spawnMobFromEntity(LivingEntity entityToRemove, EntityType<?> typeToReplace) {
        if (entityToRemove.getWorld() instanceof ServerWorld world) {

            var spawned = typeToReplace.spawn(world, null, null, null, entityToRemove.getBlockPos(), SpawnReason.NATURAL, true, false);

            if (entityToRemove instanceof RaiderEntity raiderEntity && spawned instanceof RaiderEntity) {
                var raid = ((RaiderEntity) entityToRemove).getRaid();
                if (raid != null) raid.addRaider(raiderEntity.getWave(), ((RaiderEntity) spawned), raiderEntity.getBlockPos(), false);
            }
            entityToRemove.discard();
        }
    }

    private void spawnNightmareMobs(LivingEntity livingEntity, String entityTypeString) {
        switch (entityTypeString) {
            case "entity.minecraft.piglin_brute"
            ,"entity.minecraft.piglin"
            ,"entity.minecraft.zombified_piglin"   -> spawnMobFromEntity(livingEntity, InfiniumEntityType.NIGHTMARE_BRUTE);
            case "entity.minecraft.skeleton" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.NIGHTMARE_SKELETON);
            case "entity.minecraft.ghast" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.NIGHTMARE_GHAST);
            case "entity.minecraft.blaze" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.NIGHTMARE_BLAZE);
            case "entity.minecraft.hoglin" -> spawnMobFromEntity(livingEntity, InfiniumEntityType.NIGHTMARE_HOGLIN);
        }
    }
    private void spawnRandomVoidMob(LivingEntity entityToRemove) {
        EntityType<?>[] voidList = new EntityType<?>[] {
                InfiniumEntityType.VOID_ENDERMAN,
                InfiniumEntityType.VOID_GHAST,
                InfiniumEntityType.VOID_CREEPER,
                InfiniumEntityType.VOID_SKELETON,
                InfiniumEntityType.VOID_ZOMBIE,
                InfiniumEntityType.VOID_SPIDER
        };
        int random = new Random().nextInt(voidList.length);
        spawnMobFromEntity(entityToRemove, voidList[random]);
    }


}
