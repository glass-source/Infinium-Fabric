package com.infinium.server.entities.mobs.voidmobs.voidspider;

import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class VoidSpiderNavigation extends MobNavigation {
    @Nullable private BlockPos targetPos;

    public VoidSpiderNavigation(VoidSpiderEntity mobEntity, World world) {
        super(mobEntity, world);
    }

    public Path findPathTo(BlockPos target, int distance) {
        this.targetPos = target;
        return super.findPathTo(target, distance);
    }

    public Path findPathTo(VoidSpiderEntity entity, int distance) {
        this.targetPos = entity.getBlockPos();
        return super.findPathTo(entity, distance);
    }

    public boolean startMovingTo(VoidSpiderEntity entity, double speed) {
        Path path = this.findPathTo(entity, 0);
        if (path != null) {
            entity.setStartedWalking(true);
            return this.startMovingAlong(path, speed);
        } else {
            this.targetPos = entity.getBlockPos();
            this.speed = speed;
            return true;
        }
    }

    public void tick() {
        if (!this.isIdle()) {
            super.tick();
        } else {
            if (this.targetPos != null) {
                var targetPos = this.targetPos;
                var entity = this.entity;
                if (!targetPos.isWithinDistance(entity.getPos(), entity.getWidth()) && (!(entity.getY() > (double)targetPos.getY()) || !(new BlockPos(targetPos.getX(), entity.getY(), targetPos.getZ())).isWithinDistance(entity.getPos(), entity.getWidth()))) {
                    entity.getMoveControl().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), this.speed);
                } else {
                    this.targetPos = null;
                }
            }
        }
    }
}
