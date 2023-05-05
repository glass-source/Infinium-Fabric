package com.infinium.server.entities.goals.voidmobs.voidenderman;

import com.infinium.server.entities.mobs.hostile.voidmobs.voidenderman.VoidEndermanEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class VoidEndermanChasePlayerGoal extends Goal {
    private final VoidEndermanEntity voidEnderman;
    @Nullable private LivingEntity target;

    public VoidEndermanChasePlayerGoal(VoidEndermanEntity enderman) {
        this.voidEnderman = enderman;
        this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
    }

    public boolean canStart() {
        this.target = this.voidEnderman.getTarget();
        if (!(this.target instanceof PlayerEntity)) {
            return false;
        } else {
            double d = this.target.squaredDistanceTo(this.voidEnderman);
            return !(d > 256.0) && this.voidEnderman.isPlayerStaring((PlayerEntity) this.target);
        }
    }

    public void start() {
        this.voidEnderman.getNavigation().stop();
    }

    public void tick() {
        if (this.target == null) return;
        this.voidEnderman.getLookControl().lookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
    }
}