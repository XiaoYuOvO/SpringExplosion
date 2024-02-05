package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.fireworks.FireCircle;

public class FireCircleEntity extends BaseFireworkEntity<FireCircleEntity, FireCircle>{

    private int subCountRemain = FireCircle.SUB_COUNT;
    private int summonSubCooldown = FireCircle.SUMMON_INTERVAL;
    public FireCircleEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onEntityFiring() {
//        if (this.isOnGround()) {
            if (summonSubCooldown > 0) {
                summonSubCooldown--;
            } else if (subCountRemain > 0 && summonSubCooldown == 0) {
                Vec3d subThrowVec = this.getRotationVector(0, this.getYaw())
                        .rotateY((FireCircle.SUB_COUNT - subCountRemain) * (360f / FireCircle.SUB_COUNT) * MathHelper.RADIANS_PER_DEGREE)
                        .multiply(FireCircle.SUB_RADIUS);
                FireCircleSubEntity fireCircleSubEntity = SEEntityTypes.FIRE_CIRCLE_SUB.create(this.getWorld());
                fireCircleSubEntity.setOwner(this.getOwner());
                fireCircleSubEntity.setPosition(this.getX(), this.getY(), this.getZ());
                fireCircleSubEntity.setVelocity(subThrowVec);
                this.getWorld().spawnEntity(fireCircleSubEntity);
                this.subCountRemain--;
                this.summonSubCooldown = FireCircle.SUMMON_INTERVAL;
            }
//        }
    }

    @Override
    protected boolean shouldTickFiring() {
        return this.isOnGround();
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        subCountRemain = nbt.getInt("SubCount");
        summonSubCooldown = nbt.getInt("SummonSubCooldown");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("SubCount", subCountRemain);
        nbt.putInt("SummonSubCooldown", summonSubCooldown);
    }

    @Override
    protected FireCircle getFirework() {
        return FireCircle.INSTANCE;
    }
}
