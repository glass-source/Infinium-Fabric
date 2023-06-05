package com.infinium.server.entities.mobs.hostile.bosses;

import com.google.common.collect.ImmutableList;
import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.effects.InfiniumEffects;
import com.infinium.server.entities.InfiniumEntity;
import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.items.InfiniumItems;
import com.infinium.server.sounds.InfiniumSounds;
import draylar.identity.api.PlayerIdentity;
import draylar.identity.api.variant.IdentityType;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class SuperNovaEntity extends HostileEntity implements SkinOverlayOwner, RangedAttackMob, InfiniumEntity {
    private static final TrackedData<Integer> TRACKED_ENTITY_ID_1;
    private static final TrackedData<Integer> TRACKED_ENTITY_ID_2;
    private static final TrackedData<Integer> TRACKED_ENTITY_ID_3;
    private static final List<TrackedData<Integer>> TRACKED_ENTITY_IDS;
    private static final TrackedData<Integer> INVUL_TIMER;
    private final float[] sideHeadPitches = new float[2];
    private final float[] sideHeadYaws = new float[2];
    private final int[] skullCooldowns = new int[2];
    private final int[] chargedSkullCooldowns = new int[2];
    private int blockBreakingCooldown;
    private final ServerBossBar bossBar;
    private static final Predicate<LivingEntity> CAN_ATTACK_PREDICATE;
    private static final TargetPredicate HEAD_TARGET_PREDICATE;

    static {
        TRACKED_ENTITY_ID_1 = DataTracker.registerData(SuperNovaEntity.class, TrackedDataHandlerRegistry.INTEGER);
        TRACKED_ENTITY_ID_2 = DataTracker.registerData(SuperNovaEntity.class, TrackedDataHandlerRegistry.INTEGER);
        TRACKED_ENTITY_ID_3 = DataTracker.registerData(SuperNovaEntity.class, TrackedDataHandlerRegistry.INTEGER);
        TRACKED_ENTITY_IDS = ImmutableList.of(TRACKED_ENTITY_ID_1, TRACKED_ENTITY_ID_2, TRACKED_ENTITY_ID_3);
        INVUL_TIMER = DataTracker.registerData(SuperNovaEntity.class, TrackedDataHandlerRegistry.INTEGER);
        CAN_ATTACK_PREDICATE = (livingEntity -> livingEntity.isAlive() && livingEntity.isPlayer());
        HEAD_TARGET_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(60.0).setPredicate(CAN_ATTACK_PREDICATE);
    }
    public SuperNovaEntity(EntityType<? extends SuperNovaEntity> entityType, World world) {
        super(entityType, world);
        this.setCustomName(ChatFormatter.text("&8Super Nova"));
        this.setHealth(this.getMaxHealth());
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 3));
        this.bossBar = (ServerBossBar)(new ServerBossBar(this.getDisplayName(), BossBar.Color.BLUE, BossBar.Style.NOTCHED_20)).setDarkenSky(true).setThickenFog(true);
        this.moveControl = new FlightMoveControl(this, 20, false);
        this.experiencePoints = 6000;
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }
    protected void initGoals() {
        this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.0, 40, 20.0F));
        this.goalSelector.add(5, new FlyGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(0, new SuperNovaTargetGoal(this, false));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TRACKED_ENTITY_ID_1, 0);
        this.dataTracker.startTracking(TRACKED_ENTITY_ID_2, 0);
        this.dataTracker.startTracking(TRACKED_ENTITY_ID_3, 0);
        this.dataTracker.startTracking(INVUL_TIMER, 0);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Invul", this.getInvulnerableTimer());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setInvulTimer(nbt.getInt("Invul"));
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }

    }
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        if (this.bossBar != null) this.bossBar.setName(this.getDisplayName());
    }
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WITHER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WITHER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return InfiniumSounds.LOW_SANITY_8;
    }

    public void tickMovement() {
        Vec3d vec3d = this.getVelocity().multiply(1.0, 0.6, 1.0);
        if (!this.world.isClient && this.getTrackedEntityId(0) > 0) {
            Entity entity = this.world.getEntityById(this.getTrackedEntityId(0));
            if (entity != null) {
                double d = vec3d.y;
                if (this.getY() < entity.getY() || !this.shouldRenderOverlay() && this.getY() < entity.getY() + 5.0) {
                    d = Math.max(0.0, d);
                    d += 0.3 - d * 0.6000000238418579;
                }

                vec3d = new Vec3d(vec3d.x, d, vec3d.z);
                Vec3d vec3d2 = new Vec3d(entity.getX() - this.getX(), 0.0, entity.getZ() - this.getZ());
                if (vec3d2.horizontalLengthSquared() > 9.0) {
                    Vec3d vec3d3 = vec3d2.normalize();
                    vec3d = vec3d.add(vec3d3.x * 0.3 - vec3d.x * 0.6, 0.0, vec3d3.z * 0.3 - vec3d.z * 0.6);
                }
            }
        }

        this.setVelocity(vec3d);
        if (vec3d.horizontalLengthSquared() > 0.05) {
            this.setYaw((float) MathHelper.atan2(vec3d.z, vec3d.x) * 57.295776F - 90.0F);
        }

        try {
            super.tickMovement();
        } catch (NullPointerException ex) {
            Infinium.getInstance().LOGGER.error("Hubo un problema con {}, avisale a @Asunder que lo arregle!!", this);
            ex.printStackTrace();
        }

        int i;

        int j;
        for(i = 0; i < 2; ++i) {
            j = this.getTrackedEntityId(i + 1);
            Entity entity2 = null;
            if (j > 0) {
                entity2 = this.world.getEntityById(j);
            }

            if (entity2 != null) {
                double e = this.getHeadX(i + 1);
                double f = this.getHeadY(i + 1);
                double g = this.getHeadZ(i + 1);
                double h = entity2.getX() - e;
                double k = entity2.getEyeY() - f;
                double l = entity2.getZ() - g;
                double m = Math.sqrt(h * h + l * l);
                float n = (float)(MathHelper.atan2(l, h) * 57.2957763671875) - 90.0F;
                float o = (float)(-(MathHelper.atan2(k, m) * 57.2957763671875));
                this.sideHeadPitches[i] = this.getNextAngle(this.sideHeadPitches[i], o, 40.0F);
                this.sideHeadYaws[i] = this.getNextAngle(this.sideHeadYaws[i], n, 10.0F);
            } else {
                this.sideHeadYaws[i] = this.getNextAngle(this.sideHeadYaws[i], this.bodyYaw, 10.0F);
            }
        }

        boolean bl = this.shouldRenderOverlay();

        for(j = 0; j < 3; ++j) {
            double p = this.getHeadX(j);
            double q = this.getHeadY(j);
            double r = this.getHeadZ(j);
            this.world.addParticle(ParticleTypes.SMOKE, p + this.random.nextGaussian() * 0.4, q + this.random.nextGaussian() * 0.4, r + this.random.nextGaussian() * 0.4, 0.0, 0.0, 0.0);
            if (bl && this.world.random.nextInt(4) == 0) {
                this.world.addParticle(ParticleTypes.ENTITY_EFFECT, p + this.random.nextGaussian() * 0.4, q + this.random.nextGaussian() * 0.4, r + this.random.nextGaussian() * 0.35, 0.8, 0.8, 0.5);
            }
        }

        if (this.getInvulnerableTimer() > 0) {
            for(j = 0; j < 3; ++j) {
                this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + this.random.nextGaussian(), this.getY() + (double)(this.random.nextFloat() * 3.3F), this.getZ() + this.random.nextGaussian(), 0.7, 0.7, 0.9);
            }
        }

    }

    protected void mobTick() {
        int i;
        if (this.getInvulnerableTimer() > 0) {
            i = this.getInvulnerableTimer() - 1;
            this.bossBar.setPercent(1.0F - (float)i / 220.0F);
            if (i <= 0) {
                Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
                this.world.createExplosion(this, this.getX(), this.getEyeY(), this.getZ(), 7.0F, false, destructionType);
                if (!this.isSilent()) {
                    this.world.syncGlobalEvent(1023, this.getBlockPos(), 0);
                }
            }

            this.setInvulTimer(i);
            if (this.age % 10 == 0) {
                this.heal(100.0F);
            }

        } else {
            super.mobTick();

            int j;
            for(i = 1; i < 3; ++i) {
                if (this.age >= this.skullCooldowns[i - 1]) {
                    this.skullCooldowns[i - 1] = this.age + 10 + this.random.nextInt(10);
                    if (this.world.getDifficulty() == Difficulty.NORMAL || this.world.getDifficulty() == Difficulty.HARD) {
                        int[] var10000 = this.chargedSkullCooldowns;
                        int var10001 = i - 1;
                        int var10003 = var10000[i - 1];
                        var10000[var10001] = var10000[i - 1] + 1;
                        if (var10003 > 15) {
                            double d = MathHelper.nextDouble(this.random, this.getX() - 10.0, this.getX() + 10.0);
                            double e = MathHelper.nextDouble(this.random, this.getY() - 5.0, this.getY() + 5.0);
                            double h = MathHelper.nextDouble(this.random, this.getZ() - 10.0, this.getZ() + 10.0);
                            this.shootSkullAt(i + 1, d, e, h, true);
                            this.chargedSkullCooldowns[i - 1] = 0;
                        }
                    }

                    j = this.getTrackedEntityId(i);
                    if (j > 0) {
                        LivingEntity livingEntity = (LivingEntity)this.world.getEntityById(j);
                        if (livingEntity != null && this.canTarget(livingEntity) && !(this.squaredDistanceTo(livingEntity) > 900.0) && this.canSee(livingEntity)) {
                            this.shootSkullAt(i + 1, livingEntity);
                            this.skullCooldowns[i - 1] = this.age + 40 + this.random.nextInt(20);
                            this.chargedSkullCooldowns[i - 1] = 0;
                        } else {
                            this.setTrackedEntityId(i, 0);
                        }
                    } else {
                        List<LivingEntity> list = this.world.getTargets(LivingEntity.class, HEAD_TARGET_PREDICATE, this, this.getBoundingBox().expand(20.0, 8.0, 20.0));
                        if (!list.isEmpty()) {
                            LivingEntity livingEntity2 = list.get(this.random.nextInt(list.size()));
                            this.setTrackedEntityId(i, livingEntity2.getId());
                        }
                    }
                }
            }

            if (this.getTarget() != null) {
                this.setTrackedEntityId(0, this.getTarget().getId());
            } else {
                this.setTrackedEntityId(0, 0);
            }

            if (this.blockBreakingCooldown > 0) {
                --this.blockBreakingCooldown;
                if (this.blockBreakingCooldown == 0 && this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                    i = MathHelper.floor(this.getY());
                    j = MathHelper.floor(this.getX());
                    int k = MathHelper.floor(this.getZ());
                    boolean bl = false;

                    for(int l = -1; l <= 1; ++l) {
                        for(int m = -1; m <= 1; ++m) {
                            for(int n = 0; n <= 3; ++n) {
                                int o = j + l;
                                int p = i + n;
                                int q = k + m;
                                BlockPos blockPos = new BlockPos(o, p, q);
                                BlockState blockState = this.world.getBlockState(blockPos);
                                if (canDestroy(blockState)) {
                                    bl = this.world.breakBlock(blockPos, true, this) || bl;
                                }
                            }
                        }
                    }

                    if (bl) {
                        this.world.syncWorldEvent(null, 1022, this.getBlockPos(), 0);
                    }
                }
            }

            if (this.age % 20 == 0) {
                this.heal(1.0F);
            }

            this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
        }
    }

    public static boolean canDestroy(BlockState block) {
        return !block.isAir() && !block.isIn(BlockTags.WITHER_IMMUNE);
    }

    public boolean isOnFire() {
        return false;
    }

    public void onSummoned() {
        Infinium.getInstance().getCore().getTotalPlayers().forEach(player -> Criteria.SUMMONED_ENTITY.trigger(player, this));
        this.setInvulTimer(220);
        this.bossBar.setPercent(0.0F);
        this.setHealth(this.getMaxHealth());
        Infinium.getInstance().LOGGER.info("Max health: {}", this.getMaxHealth());
        Infinium.getInstance().LOGGER.info("Health: {}", this.getHealth());
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        Infinium.getInstance().getCore().getTotalPlayers().forEach(player -> Criteria.PLAYER_KILLED_ENTITY.trigger(player, this, source));
    }

    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    private double getHeadX(int headIndex) {
        if (headIndex <= 0) {
            return this.getX();
        } else {
            float f = (this.bodyYaw + (float)(180 * (headIndex - 1))) * 0.017453292F;
            float g = MathHelper.cos(f);
            return this.getX() + (double)g * 1.3;
        }
    }

    private double getHeadY(int headIndex) {
        return headIndex <= 0 ? this.getY() + 3.0 : this.getY() + 2.2;
    }

    private double getHeadZ(int headIndex) {
        if (headIndex <= 0) {
            return this.getZ();
        } else {
            float f = (this.bodyYaw + (float)(180 * (headIndex - 1))) * 0.017453292F;
            float g = MathHelper.sin(f);
            return this.getZ() + (double)g * 1.3;
        }
    }

    private float getNextAngle(float prevAngle, float desiredAngle, float maxDifference) {
        float f = MathHelper.wrapDegrees(desiredAngle - prevAngle);
        if (f > maxDifference) {
            f = maxDifference;
        }

        if (f < -maxDifference) {
            f = -maxDifference;
        }

        return prevAngle + f;
    }

    private void shootSkullAt(int headIndex, LivingEntity target) {
        this.shootSkullAt(headIndex, target.getX(), target.getY() + (double)target.getStandingEyeHeight() * 0.5, target.getZ(), headIndex == 0 && this.random.nextFloat() < 0.001F);
    }

    private void shootSkullAt(int headIndex, double targetX, double targetY, double targetZ, boolean charged) {
        if (!this.isSilent()) {
            this.world.syncWorldEvent(null, 1024, this.getBlockPos(), 0);
        }

        double d = this.getHeadX(headIndex);
        double e = this.getHeadY(headIndex);
        double f = this.getHeadZ(headIndex);
        double g = targetX - d;
        double h = targetY - e;
        double i = targetZ - f;
        SuperNovaSkullEntity skullEntity = new SuperNovaSkullEntity(this.world, this, g, h, i);
        skullEntity.setOwner(this);
        skullEntity.setCharged(charged);
        skullEntity.setPos(d, e, f);
        this.world.spawnEntity(skullEntity);
    }
    public void attack(LivingEntity target, float pullProgress) {
        this.shootSkullAt(0, target);
    }
    public boolean damage(DamageSource source, float amount) {

        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (source != DamageSource.DROWN && !(source.getAttacker() instanceof WitherEntity)) {
            if (this.getInvulnerableTimer() > 0 && source != DamageSource.OUT_OF_WORLD) {
                return false;
            } else {
                Entity entity;
                if (this.shouldRenderOverlay()) {
                    entity = source.getSource();
                    if (entity instanceof PersistentProjectileEntity) {
                        return false;
                    }
                }

                entity = source.getAttacker();
                if (!(entity instanceof PlayerEntity) && entity instanceof LivingEntity && ((LivingEntity) entity).getGroup() == this.getGroup()) {
                    return false;
                } else {
                    if (this.blockBreakingCooldown <= 0) {
                        this.blockBreakingCooldown = 80;
                    }

                    for(int i = 0; i < this.chargedSkullCooldowns.length; ++i) {
                        this.chargedSkullCooldowns[i] += 8;
                    }

                    return super.damage(source, amount);
                }
            }
        } else {
            return false;
        }
    }
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        ItemEntity itemEntity = this.dropItem(InfiniumItems.MYSTERIOUS_KEY);
        if (itemEntity != null) {
            itemEntity.setCovetedItem();
        }

    }
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.isDisallowedInPeaceful()) {
            this.discard();
        } else {
            this.despawnCounter = 0;
        }
    }
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }
    public boolean addStatusEffect(StatusEffectInstance effect, @Nullable Entity source) {
        return false;
    }

    public float getHeadYaw(int headIndex) {
        return this.sideHeadYaws[headIndex];
    }

    public float getHeadPitch(int headIndex) {
        return this.sideHeadPitches[headIndex];
    }

    public int getInvulnerableTimer() {
        return this.dataTracker.get(INVUL_TIMER);
    }

    public void setInvulTimer(int ticks) {
        this.dataTracker.set(INVUL_TIMER, ticks);
    }

    public int getTrackedEntityId(int headIndex) {
        return this.dataTracker.get(TRACKED_ENTITY_IDS.get(headIndex));
    }

    public void setTrackedEntityId(int headIndex, int id) {
        this.dataTracker.set(TRACKED_ENTITY_IDS.get(headIndex), id);
    }

    public boolean shouldRenderOverlay() {
        return false;
    }

    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    protected boolean canStartRiding(Entity entity) {
        return false;
    }

    public boolean canUsePortals() {
        return false;
    }

    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return effect.getEffectType() != StatusEffects.WITHER && super.canHaveStatusEffect(effect);
    }
    public static DefaultAttributeContainer.Builder createNovaAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15500.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.75)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 1.35)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 100)
                .add(EntityAttributes.GENERIC_ARMOR, 12.0);
    }
    private class SuperNovaTargetGoal extends ActiveTargetGoal<PlayerEntity> {
        private int attackCooldown = (20 * 10);
        private SupernovaAttacks lastAttack;
        private int lastAttackIndex = 1;
        private boolean canAttack = true;
        private LivingEntity lastTarget;
        private ScheduledFuture<?> task;
        public SuperNovaTargetGoal(SuperNovaEntity mob, boolean checkVisibility) {
            super(mob, PlayerEntity.class, checkVisibility);
        }
        public void tick() {
            super.tick();
            if (canAttack) attackCooldown--;
            if (attackCooldown <= 0) {
                attack();
            }
        }
        private void attack() {
            var superNova = SuperNovaEntity.this;
            var instance = Infinium.getInstance();
            SupernovaAttacks currentAttack;
            this.lastTarget = superNova.getTarget();
            switch (lastAttackIndex)  {
                case 0 -> currentAttack = SupernovaAttacks.EXPLOSION;
                case 1 -> currentAttack = SupernovaAttacks.NEGATIVE_EFFECTS;
                case 2 -> currentAttack = SupernovaAttacks.TNT_CIRCLE;
                case 3 -> currentAttack = SupernovaAttacks.SUMMON_MOBS;
                case 4 -> currentAttack = SupernovaAttacks.DISGUISE_PLAYERS;
                case 5 -> currentAttack = SupernovaAttacks.BEDROCK_BARRIER;
                default -> currentAttack = SupernovaAttacks.RANDOM_ATTACK;
            }

            if (currentAttack == SupernovaAttacks.RANDOM_ATTACK) {
                SupernovaAttacks[] attacks = SupernovaAttacks.values();
                currentAttack = attacks[new Random().nextInt(attacks.length)];
            }

            switch (currentAttack) {
                case EXPLOSION -> {
                    canAttack = false;
                    this.setControls(EnumSet.of(Control.MOVE, Control.JUMP, Control.LOOK));

                    List<Float> healthList = new ArrayList<>();
                    healthList.add(superNova.getHealth());
                    superNova.setInvulTimer(260);
                    superNova.bossBar.setPercent(0.0F);
                    superNova.setGlowing(true);
                    task = instance.getExecutor().schedule(() -> {
                        canAttack = true;
                        superNova.setHealth(healthList.get(0));
                        superNova.setGlowing(false);
                        createExplosionFromEntity(superNova, superNova.getWorld(), superNova.getBlockPos());
                        task.cancel(true);
                        task = null;
                    }, 12000, TimeUnit.MILLISECONDS);
                }

                case NEGATIVE_EFFECTS -> {
                    var core = instance.getCore();
                    var manager = core.getSanityManager();
                    core.getTotalPlayers().forEach(serverPlayerEntity -> {
                        manager.decrease(serverPlayerEntity, 15, manager.SANITY);
                        serverPlayerEntity.clearStatusEffects();
                        serverPlayerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 200, 2), superNova);
                        serverPlayerEntity.addStatusEffect(new StatusEffectInstance(InfiniumEffects.MADNESS, 200, 0), superNova);
                    });
                }

                case SUMMON_MOBS -> {
                    if (superNova.world instanceof ServerWorld serverWorld) {
                        int radius = 6;
                        int segments = 6;
                        Point2D.Double[] entityLocations = getCircle(radius, segments);

                        var list = InfiniumEntityType.getEntityTypes();

                        for (int i = 0; i < segments; i++) {
                            var entityType = list.get(i);
                            var entity = entityType.spawn(serverWorld, null, null, null, SuperNovaEntity.this.getBlockPos().add(entityLocations[i].x, 6, entityLocations[i].y), SpawnReason.NATURAL, true, false);
                            if(entity != null) {
                                entity.setGlowing(true);
                                entity.setSilent(false);
                            }
                        }
                    }
                }

                case DISGUISE_PLAYERS -> {
                    this.canAttack = false;
                    var totalPlayers = instance.getCore().getTotalPlayers();
                    totalPlayers.forEach(this::setIdentity);
                    this.task = instance.getExecutor().schedule(() -> {
                        this.canAttack = true;
                        totalPlayers.forEach(this::removeIdentity);
                    }, 15, TimeUnit.SECONDS);
                }

                case TNT_CIRCLE -> {
                    canAttack = false;
                    if (superNova.world instanceof ServerWorld serverWorld) {
                        double radius = 4.5d;
                        int segments = 12;

                        Point2D.Double[] tntLocations = getCircle(radius, segments);
                        List<TntEntity> tntList = new ArrayList<>();

                        for (int i = 0; i < segments; i++) {
                            var pos = superNova.getBlockPos().add(tntLocations[i].x, 2, tntLocations[i].y);
                            var tnt = EntityType.TNT.spawn(serverWorld, null, null, null, pos, SpawnReason.MOB_SUMMONED, true, false);

                            if (tnt != null) {
                                tnt.setFuse(200);
                                tnt.setGlowing(true);
                                tnt.setSilent(false);
                                tnt.setNoGravity(true);
                                tntList.add(tnt);
                            }
                        }

                        int[] totalTicks = {0};

                        task = instance.getExecutor().scheduleWithFixedDelay(() -> {
                            totalTicks[0]++;

                            for (int i = 0; i < tntList.size(); i++) {
                                var tnt = tntList.get(i);
                                if (tnt == null) continue;
                                var pos = SuperNovaEntity.this.getBlockPos().add(tntLocations[i].x, 2, tntLocations[i].y);
                                tnt.setPosition(pos.getX(), pos.getY(), pos.getZ());
                                if (totalTicks[0] >= 140) tnt.setNoGravity(false);
                            }

                            if (totalTicks[0] >= 140) {
                                this.canAttack = true;
                                this.task.cancel(true);
                                this.task = null;
                            }

                        }, 0, 50, TimeUnit.MILLISECONDS);
                    }
                }

                case BEDROCK_BARRIER -> {
                    LivingEntity target1 = superNova.getTarget();
                    if (target1 != null) {
                        var world = superNova.getWorld();
                        double radius = 4.5;
                        int segments = 20;
                        double maxHeight = Math.min(target1.getY(), superNova.getY());
                        double minHeight = Math.max(target1.getY(), superNova.getY()) + 1.0;
                        Point2D.Double[] entityLocations = getCircle(radius, segments);
                        List<BlockPos> blockPosList = new ArrayList<>();

                        for (int i = 0; i < segments; i++) {
                           for (int j = 0; j < 6; j++) {
                               var pos = target1.getBlockPos().add(entityLocations[i].x, j, entityLocations[i].y);
                               var pos2 = getHighestBlockpos(superNova, pos.getX(), pos.getZ(), maxHeight + j, minHeight + j);
                               world.setBlockState(pos2, Blocks.BEDROCK.getDefaultState());
                               blockPosList.add(pos2);
                               world.playSound(null, pos2, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1, 0.55f);
                           }
                        }

                        instance.getExecutor().schedule(() -> blockPosList.forEach(blockPos -> {
                            world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                            world.playSound(null, blockPos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1, 0.55f);
                        }), 8, TimeUnit.SECONDS);
                    }
                }



            }

            this.setTargetEntity(this.getClosestPlayer(superNova.getX(), superNova.getY(), superNova.getZ()));
            this.lastAttack = currentAttack;
            this.attackCooldown = (20 * 8);
            this.lastAttackIndex++;
            if (lastAttackIndex > SupernovaAttacks.values().length - 1) this.lastAttackIndex = 0;
            this.sendMessage();
        }
        private void sendMessage() {
            var message = "&8&lSuper Nova:&6 ";

            switch (this.lastAttack) {
                case EXPLOSION -> message += "Die.";
                case NEGATIVE_EFFECTS -> message += "Judgment.";
                case TNT_CIRCLE -> message += "Crush.";
                case SUMMON_MOBS -> message += "Thy end is now.";
                case BEDROCK_BARRIER -> message += "You can't escape.";
                case DISGUISE_PLAYERS -> message += "Have fun.";
                default -> message += "Be gone.";
            }

            ChatFormatter.broadcastMessage(message);
        }
        private void createExplosionFromEntity(@Nullable LivingEntity entity, World world, BlockPos position) {
            world.createExplosion(entity, position.getX(), position.getY(), position.getZ(), (float) 8, Explosion.DestructionType.DESTROY);
        }
        private Point2D.Double[] getCircle(double radius, int segments) {
            Point2D.Double[] circlePoints = new Point2D.Double[segments];

            for (int i = 0; i < segments; i++) {
                double angle = 2 * Math.PI * i / segments;
                double x = radius * Math.cos(angle);
                double y = radius * Math.sin(angle);
                circlePoints[i] = new Point2D.Double(x, y);

            }
            return circlePoints;
        }
        private PlayerEntity getClosestPlayer(double x, double y, double z) {
            Predicate<Entity> predicate = (entity -> !(entity instanceof PlayerEntity) || !entity.isSpectator() && !((PlayerEntity)entity).isCreative() && entity != this.lastTarget);
            return SuperNovaEntity.this.world.getClosestPlayer(x, y, z, 30, predicate);
        }
        private void setIdentity(ServerPlayerEntity player) {
            if (player.isCreative() || player.isSpectator()) return;
            Entity createdEntity = ((EntityType<?>) InfiniumEntityType.DUCK).create(player.world);
            if (createdEntity instanceof LivingEntity living) {
                IdentityType<?> defaultType = IdentityType.from(living);
                if (defaultType != null) {
                    PlayerIdentity.updateIdentity(player, defaultType, (LivingEntity)createdEntity);
                }
            }
        }
        private void removeIdentity(ServerPlayerEntity player) {
            PlayerIdentity.updateIdentity(player, null, null);
        }
        private BlockPos getHighestBlockpos(Entity entity, double x, double z, double maxY, double y) {
            var world = entity.world;
            BlockPos blockPos = new BlockPos(x, y, z);
            double d = 0.0;

            do {
                BlockPos blockPos2 = blockPos.down();
                BlockState blockState = world.getBlockState(blockPos2);
                if (blockState.isSideSolidFullSquare(world, blockPos2, Direction.UP)) {
                    if (!world.isAir(blockPos)) {
                        BlockState blockState2 = world.getBlockState(blockPos);

                        VoxelShape voxelShape = blockState2.getCollisionShape(world, blockPos);
                        if (!voxelShape.isEmpty()) {
                            d = voxelShape.getMax(Direction.Axis.Y);
                        }
                    }
                    break;
                }

                blockPos = blockPos.down();
            } while(blockPos.getY() >= MathHelper.floor(maxY) - 1);

            return new BlockPos(x,blockPos.getY() + d, z);
        }
        private enum SupernovaAttacks {
            EXPLOSION("Stays still for 8 seconds and generates an explosion"),
            NEGATIVE_EFFECTS("Lowers sanity of all players and gives them random negative effects"),
            TNT_CIRCLE("Summons a circle of primed tnts"),
            SUMMON_MOBS("Summons a circle of mobs"),
            BEDROCK_BARRIER("Generates an obsidian barrier around it's current target"),
            DISGUISE_PLAYERS("Sets the identity of online players to a duck."),
            RANDOM_ATTACK("Random Attack");

            SupernovaAttacks(String s) {

            }
        }
    }

}
