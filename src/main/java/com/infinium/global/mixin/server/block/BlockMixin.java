package com.infinium.global.mixin.server.block;

import com.infinium.global.utils.DateUtils;
import com.infinium.server.items.custom.InfiniumItem;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock implements ItemConvertible {

    public BlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "afterBreak", at = @At("HEAD"))
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack, CallbackInfo ci){
        if (world.isClient) return;
        var item = player.getMainHandStack().getItem();
        var day = DateUtils.getDay();
        if (item instanceof InfiniumItem) return;

        if (day >= 7) {
            if (state.getBlock().equals(Blocks.ANCIENT_DEBRIS)) {
                player.setFireTicks((20 * 60) * 10);
            }
        }
    }

    @Inject(method = "onSteppedOn", at = @At("HEAD"))
    public void onStepped(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci){
        var block = state.getBlock();
        var day = DateUtils.getDay();
        if (!isBlock(block) || day < 7) return;
        if (!(entity instanceof ServerPlayerEntity p)) return;
        addBedrockEffects(p);
    }

    private void addBedrockEffects(ServerPlayerEntity p) {
        var day = DateUtils.getDay();

        if (day >= 7) {
            if (!p.hasStatusEffect(StatusEffects.SLOWNESS)) p.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 280, 1));
            if (p.getStatusEffect(StatusEffects.SLOWNESS).getDuration() < 120) p.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 280, 1));
        }

        if (day >= 14) {
            StatusEffect[] effects = {
            StatusEffects.STRENGTH,
            StatusEffects.RESISTANCE,
            StatusEffects.WATER_BREATHING,
            StatusEffects.JUMP_BOOST,
            StatusEffects.INVISIBILITY,
            StatusEffects.SPEED,
            StatusEffects.REGENERATION,
            StatusEffects.ABSORPTION,
            StatusEffects.FIRE_RESISTANCE,
            StatusEffects.NIGHT_VISION,
            StatusEffects.HEALTH_BOOST,
            StatusEffects.HERO_OF_THE_VILLAGE,
            StatusEffects.SATURATION,
            StatusEffects.SLOW_FALLING};

            Arrays.stream(effects).toList().forEach(p::removeStatusEffect);
        }

        if (day >= 21) {
            StatusEffect[] effects = {
            StatusEffects.SLOWNESS,
            StatusEffects.BLINDNESS,
            StatusEffects.POISON};

            Arrays.stream(effects).toList().forEach(status -> {
                if (!p.hasStatusEffect(status)) p.addStatusEffect(new StatusEffectInstance(status, 280, 3));
                if (p.getStatusEffect(status).getDuration() < 120) p.addStatusEffect(new StatusEffectInstance(status, 280, 3));
            });
        }

        if (day >= 35) {
            StatusEffect[] effects = {
                    StatusEffects.SLOWNESS,
                    StatusEffects.BLINDNESS,
                    StatusEffects.POISON,
                    StatusEffects.WITHER};

            Arrays.stream(effects).toList().forEach(status -> {
                if (!p.hasStatusEffect(status)) p.addStatusEffect(new StatusEffectInstance(status, 280, 4));
                if (p.getStatusEffect(status).getDuration() < 120) p.addStatusEffect(new StatusEffectInstance(status, 280, 4));
            });
        }
    }
    private boolean isBlock(Block block) {
        var item = block.asItem();
        var day = DateUtils.getDay();

        if (day >= 7 && day < 21) {
            return item.equals(Items.BEDROCK);

        } else if (day >= 21 && day < 35) {
            return item.equals(Items.BEDROCK)
                || item.equals(Items.MAGMA_BLOCK);
        } else {
            return block instanceof LeavesBlock
            || block instanceof FluidBlock
            || item.equals(Items.BEDROCK)
            || item.equals(Items.IRON_BLOCK)
            || item.equals(Items.MAGMA_BLOCK);
        }
    }


}
