package com.infinium.server.entities.mobs.hostile.ghoulmobs;

import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.entities.InfiniumEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Random;

public class GhoulWitchEntity extends WitchEntity implements InfiniumEntity {
    public GhoulWitchEntity(EntityType<? extends WitchEntity> entityType, World world) {
        super(entityType, world);
        this.setCustomName(ChatFormatter.text("&cSCP-049"));

    }

    public void attack(LivingEntity target, float pullProgress) {
        if (!this.isDrinking()) {

            Vec3d vec3d = target.getVelocity();
            double distanceX = target.getX() + vec3d.getX() - this.getX();
            double height = target.getEyeY() - 1.1f - this.getY();
            double distanceZ = target.getZ() + vec3d.getY() - this.getZ();
            double totalDistance = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);

            StatusEffectInstance[] statusEffects;
            if (target instanceof RaiderEntity) {
                statusEffects = new StatusEffectInstance[] {
                        new StatusEffectInstance(StatusEffects.STRENGTH, 120, 3),
                        new StatusEffectInstance(StatusEffects.REGENERATION, 160, 0),
                        new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 160, 3),
                        new StatusEffectInstance(StatusEffects.INVISIBILITY, 200, 0),
                        new StatusEffectInstance(StatusEffects.SPEED, 80, 0),
                        new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 140, 3),
                };

                this.setTarget(null);

            } else {
                statusEffects = new StatusEffectInstance[] {
                        new StatusEffectInstance(StatusEffects.POISON, 120, 3),
                        new StatusEffectInstance(StatusEffects.BLINDNESS, 160, 0),
                        new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 160, 3),
                        new StatusEffectInstance(StatusEffects.WEAKNESS, 200, 4),
                        new StatusEffectInstance(StatusEffects.LEVITATION, 80, 0),
                        new StatusEffectInstance(StatusEffects.SLOWNESS, 140, 3),
                };
            }

            PotionEntity potionEntity = new PotionEntity(this.world, this);
            var potRandomEffect = new StatusEffectInstance[] {statusEffects[new Random().nextInt(statusEffects.length - 1)]};
            var potionTemp = PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.EMPTY);
            var potionStack = PotionUtil.setCustomPotionEffects(potionTemp, Arrays.stream(potRandomEffect).toList());
            potionEntity.setItem(potionStack);
            potionEntity.setPitch(potionEntity.getPitch() + 20.0F);
            potionEntity.setVelocity(distanceX, height + totalDistance * 0.2, distanceZ, 0.75F, 8.0F);

            if (!this.isSilent()) {
                this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
            }

            this.world.spawnEntity(potionEntity);
        }
    }

    public static DefaultAttributeContainer.Builder createGhoulWitchAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4);
    }

}
