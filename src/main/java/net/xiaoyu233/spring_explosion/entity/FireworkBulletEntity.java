package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.EntityExplosionBehavior;
import net.minecraft.world.explosion.Explosion;
import net.xiaoyu233.spring_explosion.fireworks.FireworkBullet;
import net.xiaoyu233.spring_explosion.util.FireworkUtil;
import net.xiaoyu233.spring_explosion.util.ParticleUtil;

public class FireworkBulletEntity extends ProjectileFireworkEntity<FireworkBulletEntity, FireworkBullet> implements IFireworkEntity {
    public FireworkBulletEntity(EntityType<? extends OwnedGeoEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected FireworkBullet getFirework() {
        return FireworkBullet.INSTANCE;
    }

    @Override
    public boolean isImmuneToExplosion() {
        return true;
    }


    @Override
    protected void onBlockCollision(BlockState state) {
        if  (this.isFusing() || state.isAir() || this.getWorld().isClient) return;
        VoxelShape collisionShape = state.getCollisionShape(this.getWorld(), this.getBlockPos());
        if (collisionShape.isEmpty() || !collisionShape.getBoundingBox().offset(this.getPos()).intersects(this.getBoundingBox().expand(0.01))) return;
        serversideExplodeAndUpdateClient();
    }

    /**
     * Modify the speed for next tick after move() being called to prevent zero velocity causing raycast failing
     */
    protected void tickAcceleration() {
        this.addVelocity(ParticleUtil.getRotationVector(this.getPitch(), this.getYaw()).multiply(0.2));
    }

    private void explodeAndDiscard() {
        World world = this.getWorld();
        if (world.isClient){
            world.addFireworkParticle(this.getX(), this.getY() + 0.5, this.getZ(), 0, 0, 0, FireworkUtil.randomFirework(world.random, 1));
        }else {
            world.createExplosion(this, world.getDamageSources().explosion(this, this.getOwner()), new EntityExplosionBehavior(this), this.getX(), this.getY(), this.getZ(), 1f, false, World.ExplosionSourceType.MOB, false);
        }
        this.discard();
    }

    @Override
    public boolean canExplosionDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float explosionPower) {
        return false;
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == EXPLODE_STATUS) {
            explodeAndDiscard();
        }
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        serversideExplodeAndUpdateClient();
    }

    private void serversideExplodeAndUpdateClient() {
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, EXPLODE_STATUS);
            explodeAndDiscard();
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        serversideExplodeAndUpdateClient();
    }
}
