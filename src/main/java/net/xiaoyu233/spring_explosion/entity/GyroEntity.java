package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.fireworks.Gyro;

public class GyroEntity extends BaseFireworkEntity<GyroEntity, Gyro> {

    public GyroEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected Gyro getFirework() {
        return Gyro.INSTANCE;
    }

}
