package com.infinium.server.listeners.entity;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.global.utils.DateUtils;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.eclipse.SolarEclipseManager;
import com.infinium.server.entities.InfiniumEntity;
import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.events.entity.EntitySpawnEvent;
import com.infinium.server.items.InfiniumItems;
import net.minecraft.block.AirBlock;
import net.minecraft.block.FluidBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.core.jmx.Server;

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

            if (!(entity instanceof LivingEntity livingEntity)) return ActionResult.FAIL;
            if (livingEntity.getWorld().isClient) return ActionResult.FAIL;

            var day = DateUtils.getDay();
            var world = (ServerWorld) livingEntity.getWorld();
            var blockPos = livingEntity.getBlockPos();
            var block = world.getBlockState(blockPos.down()).getBlock();
            var entityTypeString =  livingEntity.getType().toString();

            switch (world.getRegistryKey().getValue().toString()) {

                case "minecraft:overworld" -> {

                    if (day >= 7 && day < 14) {

                        if (new Random().nextInt(10) <= 8) {
                            switch (entityTypeString) {

                                case "entity.minecraft.creeper" -> {
                                    var creeper = (CreeperEntity) livingEntity;
                                    creeper.setCustomName(ChatFormatter.text("&6Charged Creeper"));
                                    creeper.onStruckByLightning(world, null);
                                }

                                case "entity.minecraft.skeleton" -> {
                                    var skelly = (SkeletonEntity) livingEntity;
                                    var bow = Items.BOW.getDefaultStack();
                                    bow.addEnchantment(Enchantments.POWER, 25);
                                    skelly.setStackInHand(Hand.MAIN_HAND, bow);
                                    skelly.setEquipmentDropChance(EquipmentSlot.MAINHAND,0);
                                    skelly.setCustomName(ChatFormatter.text("&6Power XXV Skeleton"));
                                }

                            }
                        } else {

                            switch (entityTypeString) {

                                case "entity.minecraft.zombie" -> spawnEntity(livingEntity, InfiniumEntityType.GHOUL_ZOMBIE, world, blockPos);

                                case "entity.minecraft.spider" -> spawnEntity(livingEntity, InfiniumEntityType.GHOUL_SPIDER, world, blockPos);

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
            if (livingEntity.getWorld().isClient || !solarEclipseManager.isActive()) return ActionResult.FAIL;

            var day = DateUtils.getDay();
            var world = (ServerWorld) livingEntity.getWorld();
            var blockPos = livingEntity.getBlockPos();
            var block = world.getBlockState(blockPos.down()).getBlock();
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
                                   , "entity.minecraft.cave_spider"-> {
                                    spawnEntity(livingEntity, InfiniumEntityType.GHOUL_SPIDER, world, blockPos);
                                }

                                case "entity.minecraft.zombie" -> {

                                    spawnEntity(livingEntity, InfiniumEntityType.GHOUL_ZOMBIE, world, blockPos);

                                }

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

    private void spawnEntity(LivingEntity entityToRemove, EntityType<?> type, ServerWorld world, BlockPos pos) {
        entityToRemove.remove(Entity.RemovalReason.DISCARDED);
        type.spawn(world, null, null, null, pos, SpawnReason.NATURAL, true, false);
    }

}
