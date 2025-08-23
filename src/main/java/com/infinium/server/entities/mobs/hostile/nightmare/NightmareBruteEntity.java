package com.infinium.server.entities.mobs.hostile.nightmare;

import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.entities.InfiniumEntity;
import com.infinium.server.items.InfiniumItems;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class NightmareBruteEntity extends PiglinBruteEntity implements InfiniumEntity {

    public NightmareBruteEntity(EntityType<? extends PiglinBruteEntity> entityType, World world) {
        super(entityType, world);
        this.setCustomName(ChatFormatter.text("&cNightmare Brute"));

    }

    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;

        } else if (source.isFire()) {
            return false;

        } else {
            return super.damage(source, amount);
        }
    }

    public static DefaultAttributeContainer.Builder createNightmareBruteAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 80.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.36)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 20.0);
    }

    protected void initEquipment(LocalDifficulty difficulty) {
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(InfiniumItems.VOID_AXE));
        this.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0);
    }

}
