package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.fireworks.FireCircleSub;
import net.xiaoyu233.spring_explosion.util.PredicateUtil;

public class FireCircleSubEntity extends BaseFireworkEntity<FireCircleSubEntity, FireCircleSub>{
    public FireCircleSubEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void onEntityFiring() {
        if (this.getWorld().isClient){
            this.getWorld().addImportantParticle(ParticleTypes.FLAME, true, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() / 8, 0.3, this.random.nextGaussian() / 8);
        }else {
            this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(1.5), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.and(
                    PredicateUtil.getNotSameTeamAsOwnerPredicate(this.getOwner()))).forEach((entity) -> {
                        if (entity instanceof LivingEntity livingEntity) {
                            entity.damage(this.getWorld().getDamageSources().explosion(this, this.getOwner()), 1.5f);
                            entity.setOnFireFor(4);
                            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 1));
                        }
            });
        }
    }

    @Override
    protected FireCircleSub getFirework() {
        return FireCircleSub.INSTANCE;
    }
}
