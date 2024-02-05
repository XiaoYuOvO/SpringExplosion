package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.xiaoyu233.spring_explosion.fireworks.BaseFirework;
import net.xiaoyu233.spring_explosion.util.PredicateUtil;

public abstract class ProjectileFireworkEntity<E extends BaseFireworkEntity<E,?>, F extends BaseFirework<E,?,?>> extends BaseFireworkEntity<E,F>{
    public ProjectileFireworkEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    public void tick() {
        super.tick();
        //Must be here!
        this.tickAcceleration();
        if (this.isFiring()) {
            HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
            boolean bl = false;
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
                BlockState blockState = this.getWorld().getBlockState(blockPos);
                if (blockState.isOf(Blocks.NETHER_PORTAL)) {
                    this.setInNetherPortal(blockPos);
                    bl = true;
                } else if (blockState.isOf(Blocks.END_GATEWAY)) {
                    BlockEntity blockEntity = this.getWorld().getBlockEntity(blockPos);
                    if (blockEntity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.canTeleport(this)) {
                        EndGatewayBlockEntity.tryTeleportingEntity(this.getWorld(), blockPos, blockState, this, (EndGatewayBlockEntity) blockEntity);
                    }

                    bl = true;
                }
            }

            if (hitResult.getType() != HitResult.Type.MISS && !bl) {
                this.onCollision(hitResult);
            }


            this.checkBlockCollision();
        }

        Vec3d vec3d = this.getVelocity();
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
//        this.updateRotation();
        float h;
        if (this.isTouchingWater()) {
            for(int i = 0; i < 4; ++i) {
                this.getWorld().addParticle(ParticleTypes.BUBBLE, d - vec3d.x * 0.25, e - vec3d.y * 0.25, f - vec3d.z * 0.25, vec3d.x, vec3d.y, vec3d.z);
            }

            h = 0.8F;
        } else {
            h = 0.99F;
        }

        this.setVelocity(vec3d.multiply(h));
        if (!shouldSelfMove()){
            this.setPosition(d, e, f);
        }
    }

    protected void checkBlockCollision() {
        Box box = this.getBoundingBox().expand(0.05,0,0.05);
        BlockPos blockPos = BlockPos.ofFloored(box.minX + 1.0E-7, box.minY + 1.0E-7, box.minZ + 1.0E-7);
        BlockPos blockPos2 = BlockPos.ofFloored(box.maxX - 1.0E-7, box.maxY - 1.0E-7, box.maxZ - 1.0E-7);
        if (this.getWorld().isRegionLoaded(blockPos, blockPos2)) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for(int i = blockPos.getX(); i <= blockPos2.getX(); ++i) {
                for(int j = blockPos.getY(); j <= blockPos2.getY(); ++j) {
                    for(int k = blockPos.getZ(); k <= blockPos2.getZ(); ++k) {
                        mutable.set(i, j, k);
                        BlockState blockState = this.getWorld().getBlockState(mutable);

                        try {
                            blockState.onEntityCollision(this.getWorld(), mutable, this);
                            this.onBlockCollision(blockState);
                        } catch (Throwable var12) {
                            CrashReport crashReport = CrashReport.create(var12, "Colliding entity with block");
                            CrashReportSection crashReportSection = crashReport.addElement("Block being collided with");
                            CrashReportSection.addBlockInfo(crashReportSection, this.getWorld(), mutable, blockState);
                            throw new CrashException(crashReport);
                        }
                    }
                }
            }
        }

    }

    /**
     * Modify the speed for next tick after move() being called to prevent zero velocity causing raycast failing
     * */
    protected void tickAcceleration(){}

    private boolean canHit(Entity entity) {
        if (!entity.canBeHitByProjectile()) {
            return false;
        } else {
            Entity owner = this.getOwner();
            return PredicateUtil.canHitEntityWithOwner(entity, owner) && (owner == null || !owner.isConnectedThroughVehicle(entity));
        }
    }

    protected void onCollision(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            this.onEntityHit((EntityHitResult)hitResult);
            this.getWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, hitResult.getPos(), GameEvent.Emitter.of(this, (BlockState)null));
        } else if (type == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult)hitResult;
            this.onBlockHit(blockHitResult);
            BlockPos blockPos = blockHitResult.getBlockPos();
            this.getWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, blockPos, GameEvent.Emitter.of(this, this.getWorld().getBlockState(blockPos)));
        }

    }

    public void setVelocityClient(double x, double y, double z) {
        this.setVelocity(x, y, z);
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            double d = Math.sqrt(x * x + z * z);
            this.setPitch((float)(MathHelper.atan2(y, d) * 57.2957763671875));
            this.setYaw((float)(MathHelper.atan2(x, z) * 57.2957763671875));
            this.prevPitch = this.getPitch();
            this.prevYaw = this.getYaw();
            this.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
//        BlockState blockState = this.getWorld().getBlockState(blockHitResult.getBlockPos());
//        blockState.onProjectileHit(this.getWorld(), blockState, blockHitResult, this);
    }

    protected double getGravity() {
        return 0.03F;
    }
}
