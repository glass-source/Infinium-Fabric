package me.mrswz.entity;

import me.mrswz.items.ModItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MagmaTridentEntity extends TridentEntity {

    private final ItemStack tridentStack;

    public MagmaTridentEntity(EntityType<? extends TridentEntity> entityType, World world) {
        super(entityType, world);
         tridentStack = new ItemStack(ModItems.MAGMA_TRIDENT);
    }

    public MagmaTridentEntity(World world, LivingEntity owner, ItemStack stack) {
        super(world, owner, stack);
        tridentStack = stack;
    }



    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        float f = 8.0F;
        if (entity instanceof LivingEntity livingEntity) {
            f += EnchantmentHelper.getAttackDamage(tridentStack, livingEntity.getGroup());
        }

        Entity entity2 = getOwner();
        DamageSource damageSource = DamageSource.trident(this, entity2 == null ? this : entity2);
        SoundEvent soundEvent = SoundEvents.ITEM_TRIDENT_HIT;
        if (entity.damage(damageSource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity livingEntity2) {
                if (entity2 instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity2, entity2);
                    EnchantmentHelper.onTargetDamaged((LivingEntity) entity2, livingEntity2);
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
                lightningEntity.setChanneler(entity2 != null ? (ServerPlayerEntity) entity2 : null);
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
    
}
