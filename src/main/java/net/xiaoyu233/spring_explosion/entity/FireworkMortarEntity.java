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
import net.xiaoyu233.spring_explosion.fireworks.FireworkMortar;
import net.xiaoyu233.spring_explosion.util.FireworkUtil;

public class FireworkMortarEntity extends ProjectileFireworkEntity<FireworkMortarEntity, FireworkMortar> {
    private static final byte EXPLODE_STATUS = 64;
    public FireworkMortarEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void tickAcceleration() {
        if (isFiring()){
            this.setVelocity(this.getVelocity().add(this.getRotationVec(0).multiply(0.1)));
        }
    }

    @Override
    protected FireworkMortar getFirework() {
        return FireworkMortar.INSTANCE;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
//        super.onBlockHit(blockHitResult);
        serversideExplodeAndUpdateClient();
    }

    private void explodeAndDiscard() {
        this.getWorld().createExplosion(this, this.getWorld().getDamageSources().explosion(this, this.getOwner()), new EntityExplosionBehavior(this),this.getX(), this.getY(),this.getZ(), 2, false, World.ExplosionSourceType.MOB, false);
        this.getWorld().addFireworkParticle(this.getX(), this.getY() + 0.5, this.getZ(), 0,0,0, FireworkUtil.randomFirework(this.getWorld().random));
        this.discard();
    }


    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == EXPLODE_STATUS) {
            explodeAndDiscard();
        }
    }

    @Override
    public boolean canExplosionDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float explosionPower) {
        return false;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
//        super.onEntityHit(entityHitResult);
        serversideExplodeAndUpdateClient();
    }

    @Override
    protected void onBlockCollision(BlockState state) {
        if  (this.isFusing() || state.isAir() || this.getWorld().isClient) return;
        VoxelShape collisionShape = state.getCollisionShape(this.getWorld(), this.getBlockPos());
        if (collisionShape.isEmpty() || !collisionShape.getBoundingBox().offset(this.getPos()).intersects(this.getBoundingBox().expand(0.01))) return;
        serversideExplodeAndUpdateClient();
    }

    private void serversideExplodeAndUpdateClient() {
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, EXPLODE_STATUS);
            explodeAndDiscard();
        }
    }

    @Override
    protected double getGravity() {
        return this.getFuseRemain() == 0 ? 0.0 : super.getGravity();
    }
}
