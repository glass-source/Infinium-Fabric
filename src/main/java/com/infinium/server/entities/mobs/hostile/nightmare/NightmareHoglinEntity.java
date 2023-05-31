package com.infinium.server.entities.mobs.hostile.nightmare;

import com.infinium.server.entities.InfiniumEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class NightmareHoglinEntity extends HoglinEntity implements InfiniumEntity{
    public NightmareHoglinEntity(EntityType<? extends HoglinEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;

        } else if (source.isFire() ) {
            return false;

        } else {
            return super.damage(source, amount);
        }
    }
    public static DefaultAttributeContainer.Builder createNightmareHoglinAttributes() {
        return HostileEntity.createHostileAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 80.0)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.32)
        .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.8)
        .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 3.5)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 25.0);
    }

}
