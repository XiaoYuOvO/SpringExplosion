package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.sound.SESoundEvents;
import net.xiaoyu233.spring_explosion.fireworks.GlowingBomb;
import net.xiaoyu233.spring_explosion.util.ParticleUtil;

public class GlowingBombEntity extends BaseBombEntity<GlowingBombEntity, GlowingBomb>{
    public GlowingBombEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    protected void onHit() {
        if (!this.getWorld().isClient) {
            for (Entity entity : ((IExplosionEntityRecord) this.getWorld()
                    .createExplosion(this,
                            this.getWorld().getDamageSources().explosion(this, this.getOwner()),
                            new FireworkExplosionBehavior<>(this),
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            2f,
                            false,
                            World.ExplosionSourceType.MOB,
                            false)).springExplosion$getAffectedEntities()) {
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 1200, 1));
                }
            }
            this.playSound(SESoundEvents.GLOWING_BOMB_EXPLODE, 1.5f, (float) (1 + random.nextGaussian() * 0.1f));
        }else {
            ParticleUtil.explodeBall((ClientWorld) getWorld(),this.getPos(), this.random, ParticleTypes.END_ROD, 0.4, 5, true);
        }
    }

    @Override
    protected GlowingBomb getFirework() {
        return GlowingBomb.INSTANCE;
    }
}
