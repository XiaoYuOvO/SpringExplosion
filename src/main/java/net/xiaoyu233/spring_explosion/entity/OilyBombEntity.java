package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.explosion.EntityExplosionBehavior;
import net.xiaoyu233.spring_explosion.client.sound.SESoundEvents;
import net.xiaoyu233.spring_explosion.effect.SEEffects;
import net.xiaoyu233.spring_explosion.fireworks.OilyBomb;
import net.xiaoyu233.spring_explosion.util.ParticleUtil;

public class OilyBombEntity extends BaseBombEntity<OilyBombEntity, OilyBomb>{
    public OilyBombEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    protected void onHit() {
        if (!this.getWorld().isClient) {
            for (Entity entity : ((IExplosionEntityRecord) this.getWorld().createExplosion(this, this.getWorld().getDamageSources().explosion(this, this.getOwner()), new EntityExplosionBehavior(this), this.getX(), this.getY(), this.getZ(), 1.2f, false, World.ExplosionSourceType.MOB, false)).springExplosion$getAffectedEntities()) {
                if (entity instanceof LivingEntity livingEntity) {
                    StatusEffectInstance statusEffect = livingEntity.getStatusEffect(SEEffects.OILY);
                    if (statusEffect != null) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(SEEffects.OILY,
                                400,
                                statusEffect.getAmplifier() + 1));
                    } else {
                        livingEntity.addStatusEffect(new StatusEffectInstance(SEEffects.OILY, 400, 0));
                    }
                }
            }
            this.playSound(SESoundEvents.OILY_BOMB_EXPLODE, 1,1);
        }else {
            ParticleUtil.explodeBall((ClientWorld) getWorld(),this.getPos(), this.random, ParticleTypes.SQUID_INK, 0.3, 3, true);
        }
    }


    @Override
    protected OilyBomb getFirework() {
        return OilyBomb.INSTANCE;
    }
}
