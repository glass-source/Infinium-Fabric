package com.infinium.server.items.custom.misc;

import com.infinium.server.items.InfiniumItems;
import com.infinium.server.items.custom.InfiniumItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.UUID;

public class InfiniumTotemItem extends Item implements InfiniumItem {

    private static final EntityAttributeModifier EXTRA_HEALTH_BOOST = new EntityAttributeModifier(UUID.randomUUID(), "Magma Totem Healthboost", 8, EntityAttributeModifier.Operation.ADDITION);;

    public InfiniumTotemItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!(entity instanceof ServerPlayerEntity p)) return;
        var entityAttributeInstance = p.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

        if (hasMagmaTotem(p)) {
            if (!entityAttributeInstance.hasModifier(EXTRA_HEALTH_BOOST)) {
                entityAttributeInstance.addTemporaryModifier(EXTRA_HEALTH_BOOST);
                p.setHealth(p.getHealth());

            }
        } else {
            if (entityAttributeInstance.hasModifier(EXTRA_HEALTH_BOOST)) {
                entityAttributeInstance.removeModifier(EXTRA_HEALTH_BOOST);
                p.damage(DamageSource.OUT_OF_WORLD, 0.0001f);
            }
        }
    }

    public boolean hasMagmaTotem(ServerPlayerEntity user) {
        for(ItemStack stack : user.getItemsHand()) {
            if (stack.isOf(InfiniumItems.MAGMA_TOTEM)) return true;
        }
        return false;

    }

}
