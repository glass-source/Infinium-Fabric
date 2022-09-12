package com.infinium.server.items.custom.armor;

import com.infinium.server.items.custom.InfiniumItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.UUID;

public class VoidArmorItem extends ArmorItem implements ItemConvertible, InfiniumItem {

    private static final EntityAttributeModifier EXTRA_HEALTH_BOOST = new EntityAttributeModifier(UUID.randomUUID(), "Void Armor Healthboost", 16, EntityAttributeModifier.Operation.ADDITION);;
    public VoidArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.getWorld().isClient()) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 160, 3));
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 160, 0));
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        return super.postMine(stack, world, state, pos, miner);
    }

    @Override
    public boolean isDamageable() {
        return super.isDamageable();
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return super.canRepair(stack, ingredient);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient()) return;
        if (!(entity instanceof PlayerEntity p)) return;
        var entityAttributeInstance = p.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

        if (hasVoidArmor(p)) {
            if (!entityAttributeInstance.hasModifier(EXTRA_HEALTH_BOOST)) {
                entityAttributeInstance.addTemporaryModifier(EXTRA_HEALTH_BOOST);
                p.setHealth(p.getHealth());
            }

            StatusEffect[] effects = {
            StatusEffects.RESISTANCE,
            StatusEffects.SPEED};

            Arrays.stream(effects).toList().forEach(status -> {
                if (!p.hasStatusEffect(status)) p.addStatusEffect(new StatusEffectInstance(status, 280, 0));
                if (p.getStatusEffect(status).getDuration() < 120)
                    p.addStatusEffect(new StatusEffectInstance(status, 280, 0));
            });
        } else {
            if (entityAttributeInstance.hasModifier(EXTRA_HEALTH_BOOST)) {
                entityAttributeInstance.removeModifier(EXTRA_HEALTH_BOOST);
                p.damage(DamageSource.OUT_OF_WORLD, 0.0001f);
            }
        }
    }

    private static boolean hasFullArmor(PlayerEntity user) {
        var inventory = user.getInventory();
        var boots = inventory.getArmorStack(0);
        var leggings = inventory.getArmorStack(1);
        var chestplate = inventory.getArmorStack(2);
        var helmet = inventory.getArmorStack(3);
        return !boots.isEmpty() && !leggings.isEmpty() && !chestplate.isEmpty() && !helmet.isEmpty();
    }

    public static boolean hasVoidArmor(PlayerEntity user) {
        var inventory = user.getInventory();
        var boots = inventory.getArmorStack(0).getItem();
        var leggings = inventory.getArmorStack(1).getItem();
        var chestplate = inventory.getArmorStack(2).getItem();
        var helmet = inventory.getArmorStack(3).getItem();

        return hasFullArmor(user) && helmet instanceof VoidArmorItem && chestplate instanceof VoidArmorItem
        && leggings instanceof VoidArmorItem && boots instanceof VoidArmorItem;
    }

}