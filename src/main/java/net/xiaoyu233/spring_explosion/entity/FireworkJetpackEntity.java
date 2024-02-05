package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.fireworks.FireworkJetpack;

public class FireworkJetpackEntity extends BaseFireworkEntity<FireworkJetpackEntity, FireworkJetpack>{
    public FireworkJetpackEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected FireworkJetpack getFirework() {
        return FireworkJetpack.INSTANCE;
    }
}
