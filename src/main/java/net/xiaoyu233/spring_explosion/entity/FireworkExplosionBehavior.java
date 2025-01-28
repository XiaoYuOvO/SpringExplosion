package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.world.explosion.EntityExplosionBehavior;
import net.minecraft.world.explosion.Explosion;

public class FireworkExplosionBehavior<E extends Entity & IFireworkEntity> extends EntityExplosionBehavior {
    private final E firework;
    public FireworkExplosionBehavior(E entity) {
        super(entity);
        this.firework = entity;
    }

    public float modifyEntityExplosionDamage(Explosion explosion, Entity target, float originalDamage, double exposure){
        if (target instanceof ItemEntity) return 0f;
        return firework.modifyExplosionDamage(explosion, target, originalDamage, exposure);
    }
}
