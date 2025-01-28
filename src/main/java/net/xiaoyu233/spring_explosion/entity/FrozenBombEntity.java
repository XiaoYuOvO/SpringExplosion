package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.xiaoyu233.spring_explosion.components.items.FireworkItemBaseComponent;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.fireworks.FrozenBomb;
import net.xiaoyu233.spring_explosion.item.IFireworkItem;
import net.xiaoyu233.spring_explosion.util.ParticleUtil;
import net.xiaoyu233.spring_explosion.util.PredicateUtil;

public class FrozenBombEntity extends BaseBombEntity<FrozenBombEntity, FrozenBomb>{
    public FrozenBombEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onHit() {
        if (!this.getWorld().isClient) {
            for (Entity entity : ((IExplosionEntityRecord) this.getWorld().createExplosion(this, this.getWorld().getDamageSources().explosion(this, this.getOwner()), new FireworkExplosionBehavior<>(this), this.getX(), this.getY(), this.getZ(), 1.2f, false, World.ExplosionSourceType.MOB, false)).springExplosion$getAffectedEntities()) {
                if (entity instanceof LivingEntity livingEntity) {
                    StatusEffectInstance statusEffect = livingEntity.getStatusEffect(StatusEffects.SLOWNESS);
                    livingEntity.setFrozenTicks(60);
                    if (statusEffect != null) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 300, statusEffect.getAmplifier() + 1));
                    } else {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 0));
                    }
                }
            }

            for (Entity otherEntity : this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(4.0, 2.0, 4.0), PredicateUtil.getOwnerNotSameTeamPredicate(this.getOwner()))) {
                if (otherEntity instanceof BaseFireworkEntity<?,?> firework){
                    firework.disposeOnWater();
                }else if (otherEntity instanceof LivingEntity livingEntity) {
                    disposeStack(livingEntity, livingEntity.getStackInHand(Hand.MAIN_HAND));
                    disposeStack(livingEntity, livingEntity.getStackInHand(Hand.OFF_HAND));
                }
            }

            this.playSound(SoundEvents.BLOCK_SNOW_BREAK, 1,1);
            this.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1, (float) (1 + random.nextGaussian() * 0.15f));
        }else {
            ParticleUtil.explodeBall((ClientWorld) getWorld(),this.getPos(), this.random, ParticleTypes.SNOWFLAKE, 0.3, 3, false);
        }
    }

    private void disposeStack(LivingEntity entity, ItemStack stack){
        if (stack.getItem() instanceof IFireworkItem<?>){
            FireworkItemBaseComponent fireworkItemBaseComponent = SEItemComponents.FIREWORK_ITEM_BASE.get(stack);
            if (fireworkItemBaseComponent.isFiring() || fireworkItemBaseComponent.isFusing()) {
                fireworkItemBaseComponent.setFusing(false);
                fireworkItemBaseComponent.setFiring(false);
                entity.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1, 1);
                entity.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1);
            }
        }
    }

    @Override
    protected FrozenBomb getFirework() {
        return FrozenBomb.INSTANCE;
    }

    @Override
    public float modifyExplosionDamage(Explosion explosion, Entity target, float originalDamage, double exposure) {
        return Math.min(3,originalDamage);
    }
}
