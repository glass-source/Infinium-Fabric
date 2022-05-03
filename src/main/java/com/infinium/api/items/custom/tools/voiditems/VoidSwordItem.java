package com.infinium.api.items.custom.tools.voiditems;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class VoidSwordItem extends SwordItem {

    public VoidSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.getWorld().isClient()) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 160, 7));
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 160, 7));
        }

        return super.postHit(stack, target, attacker);
    }
}
