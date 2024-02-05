package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.fireworks.FireworkMachineGun;

public class FireworkMachineGunEntity extends BaseFireworkEntity<FireworkMachineGunEntity, FireworkMachineGun>{
    public FireworkMachineGunEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected FireworkMachineGun getFirework() {
        return FireworkMachineGun.INSTANCE;
    }
}
