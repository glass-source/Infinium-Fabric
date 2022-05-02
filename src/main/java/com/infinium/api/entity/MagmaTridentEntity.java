package com.infinium.api.entity;

import com.infinium.api.items.global.InfiniumItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MagmaTridentEntity extends TridentEntity {

    public final ItemStack tridentStack;
    public static final TrackedData<Boolean> MAGMA;

    static {
        MAGMA = DataTracker.registerData(MagmaTridentEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public MagmaTridentEntity(EntityType<? extends TridentEntity> entityType, World world) {
        super(entityType, world);
        tridentStack = new ItemStack(InfiniumItems.MAGMA_TRIDENT);
        this.dataTracker.set(MAGMA, false);
        this.setCustomName(Text.of("Magma Trident"));
    }

    public MagmaTridentEntity(World world, LivingEntity owner, ItemStack stack) {
        super(world, owner, stack);
        tridentStack = stack;
        this.setCustomName(Text.of("Magma Trident"));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(MAGMA, true);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (!world.isClient()) entity.setFireTicks(entity.getFireTicks() + 1000);
        float f = 8.0F;
        if (entity instanceof LivingEntity livingEntity) {
            f += EnchantmentHelper.getAttackDamage(tridentStack, livingEntity.getGroup());
        }

        Entity owner = this.getOwner();
        DamageSource damageSource = DamageSource.trident(this, owner == null ? this : owner);
        SoundEvent soundEvent = SoundEvents.ITEM_TRIDENT_HIT;
        if (entity.damage(damageSource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity livingEntity2) {
                if (owner instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity2, owner);
                    EnchantmentHelper.onTargetDamaged((LivingEntity) owner, livingEntity2);
                }

                onHit(livingEntity2);
            }
        }

        this.setVelocity(getVelocity().multiply(-0.01D, -0.1D, -0.01D));
        float g = 1.0F;
        if (world instanceof ServerWorld && EnchantmentHelper.hasChanneling(tridentStack)) {
            BlockPos blockPos = entity.getBlockPos();
            if (world.isSkyVisible(blockPos)) {
                LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
                assert lightningEntity != null;
                lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                lightningEntity.setChanneler(owner != null ? (ServerPlayerEntity) owner : null);
                world.spawnEntity(lightningEntity);
                soundEvent = SoundEvents.ITEM_TRIDENT_THUNDER;
                g = 5.0F;
            }
        }

        playSound(soundEvent, g, 1.0F);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return super.createSpawnPacket();
    }

    public static boolean isMagmaTrident(TridentEntity entity) {
        try {
            return entity.getDataTracker().get(MAGMA);
        } catch (NullPointerException ignored) {
            return false;
        }
    }

}
