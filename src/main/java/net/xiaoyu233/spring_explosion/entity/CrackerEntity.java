package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.sound.SESoundEvents;
import net.xiaoyu233.spring_explosion.fireworks.Cracker;
import net.xiaoyu233.spring_explosion.util.ParticleUtil;

public class CrackerEntity extends BaseBombEntity<CrackerEntity, Cracker>{
    public CrackerEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected double getGravity() {
        return 0.05;
    }

    @Override
    protected float getTrailOffset() {
        return 0.1f;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        World world = this.getWorld();
        if (!world.isClient) {
            Vec3d pos = entityHitResult.getEntity().getPos();
            this.setPosition(pos.add(this.getPos().subtract(pos).multiply(0.1, 1, 0.1)));
            entityHitResult.getEntity().damage(world.getDamageSources()
                    .explosion(this, this.getOwner()), 2.0f);
        }
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected void onHit() {
        World world = this.getWorld();
        if (world.isClient){
            ParticleUtil.spawnConicalParticlesFromFacing(this.getWorld(), ParticleTypes.FLAME, this.getRotationClient().negate(), this.getPos(), Vec3d.ZERO,10,1.3f, 60);
        }else {
            this.playSound(SESoundEvents.FIRECRACKER_EXPLODE,0.7f, (float) (1.7f + random.nextGaussian() * 0.3f));
        }
    }

    @Override
    protected Cracker getFirework() {
        return Cracker.INSTANCE;
    }
}
