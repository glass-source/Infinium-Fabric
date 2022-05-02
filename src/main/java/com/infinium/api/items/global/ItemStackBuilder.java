package com.infinium.api.items.global;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;


public class ItemStackBuilder {

    protected ItemStack stack;

    public ItemStackBuilder(Item item) {
        stack = new ItemStack(item);
    }

    public ItemStackBuilder(ItemStack stack) {
        this.stack = stack;
    }


    public ItemStackBuilder setDisplayName(String text) {
        stack.setCustomName(Text.of(text));
        return this;
    }

    public ItemStackBuilder setDamage(int damage) {
        stack.setDamage(damage);
        return this;
    }

    public ItemStackBuilder setCount(int count) {
        stack.setCount(count);
        return this;
    }



    public ItemStackBuilder addEnchantment(Enchantment enchant, int level) {
        stack.addEnchantment(enchant, level);
        return this;
    }

    public ItemStackBuilder hideFlag(ItemStack.TooltipSection flag) {
        stack.addHideFlag(flag);
        return this;
    }

    public ItemStackBuilder setHolder(Entity entity) {
        stack.setHolder(entity);
        return this; //la verdad no se porque hize esto pero ok
    }

    public ItemStackBuilder setNBTCompound(NbtCompound nbt) {
        stack.setNbt(nbt);
        return this;
    }

    public <T> ItemStackBuilder addNBT(String key, T value) {
        NbtCompound compound = stack.getOrCreateNbt();

        if(value instanceof String s) {
            compound.putString(key, s);
        }else if(value instanceof Integer i) {
            compound.putInt(key, i);

        }else if(value instanceof Double d) {

            compound.putDouble(key, d);

        }else if(value instanceof Long l) {

            compound.putLong(key, l);

        }else if(value instanceof Boolean b) {

            compound.putBoolean(key, b);

        }else if(value instanceof Byte by) {

            compound.putByte(key, by);

        }else {
            compound.putString(key, value.toString());
        }
        return this;
    }


    public ItemStackBuilder createNBT() {
        stack.getOrCreateNbt();
        return this;
    }


    public ItemStackBuilder addAttributeModifier(EntityAttribute attribute, EntityAttributeModifier modifier, EquipmentSlot slot)  {
        stack.addAttributeModifier(attribute, modifier, slot);
        return this;
    }

    public ItemStack build() {
        return stack;
    }

}
