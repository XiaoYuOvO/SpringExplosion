package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.xiaoyu233.spring_explosion.fireworks.BaseFirework;

public abstract class BaseBombEntity<E extends BaseBombEntity<E,?>, F extends BaseFirework<E,?,?>> extends ProjectileFireworkEntity<E,F>{
    public BaseBombEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }


    @Override
    //Side: Both
    protected void onEntityStopFiring() {
        this.onHit();
        this.discard();
    }

    @Override
    protected boolean shouldSelfMove() {
        return false;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        updateClientStatus();
        this.onHit();
        this.discard();
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == EXPLODE_STATUS) {
            this.onHit();
            this.discard();
        }
    }

    @Override
    public boolean canExplosionDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float explosionPower) {
        return false;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        updateClientStatus();
        this.onHit();
        this.discard();
    }

    private void updateClientStatus(){
        if (!this.getWorld().isClient){
            this.getWorld().sendEntityStatus(this, EXPLODE_STATUS);
        }
    }

    //Side: Both
    protected abstract void onHit();

}
