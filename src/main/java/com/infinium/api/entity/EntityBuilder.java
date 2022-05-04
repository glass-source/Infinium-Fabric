package com.infinium.api.entity;

import com.infinium.api.utils.ChatFormatter;
import com.infinium.api.world.Location;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public class EntityBuilder {

    public static EntityBuilder create(LivingEntity entity) {
        return new EntityBuilder(entity);
    }


    private final LivingEntity entity;
    private Location loc;


    protected EntityBuilder(LivingEntity entity) {
        this.entity = entity;
        loc = new Location(entity.getWorld(), entity.getX(), entity.getY(), entity.getZ());
    }


    public EntityBuilder setHealth(double value) {
        entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(value);
        entity.setHealth((float) value);

        return this;
    }


    public EntityBuilder addStatusEffect(StatusEffect effect, int amplifier, int duration) {
        entity.addStatusEffect(new StatusEffectInstance(effect, amplifier, duration));
        return this;
    }

    public EntityBuilder setItemInHand(Hand slot, ItemStack item) {
        entity.setStackInHand(slot, item);
        return this;
    }

    public EntityBuilder setItem(EquipmentSlot slot, ItemStack item) {
        entity.equipStack(slot, item);
        return this;
    }


    public EntityBuilder setCharged(boolean value) {
        if(value && entity.getType() == EntityType.CREEPER) {
            entity.onStruckByLightning(null, null);
            entity.setFireTicks(0);
            entity.setHealth(entity.getHealth() + 1);
        }
        return this;
    }

    public EntityBuilder setInvisible(boolean value) {
        entity.setInvisible(value);
        return this;
    }

    public EntityBuilder teleport(Location loc) {
        entity.teleport(loc.getX(), loc.getY(), loc.getZ());
        this.loc = loc;
        return this;
    }

    public EntityBuilder teleport(double x, double y, double z) {
        entity.teleport(x,y,z);
        this.loc = new Location(loc.getWorld(), x, y, z);
        return this;
    }

    public EntityBuilder setVelocity(Vec3d vec) {
        entity.setVelocity(vec);
        return this;
    }

    public EntityBuilder setName(String name) {
        entity.setCustomName(ChatFormatter.text(name));
        return this;
    }
}
