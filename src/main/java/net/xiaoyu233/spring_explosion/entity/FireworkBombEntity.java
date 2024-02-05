package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraft.world.explosion.EntityExplosionBehavior;
import net.xiaoyu233.spring_explosion.fireworks.FireworkBomb;
import net.xiaoyu233.spring_explosion.util.FireworkUtil;

public class FireworkBombEntity extends BaseBombEntity<FireworkBombEntity, FireworkBomb>{
    public FireworkBombEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onHit() {
        if (this.getWorld().isClient) {
            this.getWorld().addFireworkParticle(this.getX(), this.getY(), this.getZ(), 0, 0, 0, FireworkUtil.randomFirework(this.getWorld().random,1));
        }else {
            this.getWorld().createExplosion(this, this.getWorld().getDamageSources().explosion(this, this.getOwner()), new EntityExplosionBehavior(this), this.getX(), this.getY(), this.getZ(), 1.2f, false, World.ExplosionSourceType.MOB, false);
        }
    }

    @Override
    protected FireworkBomb getFirework() {
        return FireworkBomb.INSTANCE;
    }
}
