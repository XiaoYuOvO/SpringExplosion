package net.xiaoyu233.spring_explosion.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.xiaoyu233.spring_explosion.entity.FireworkExplosionBehavior;
import net.xiaoyu233.spring_explosion.entity.IExplosionEntityRecord;
import net.xiaoyu233.spring_explosion.util.PredicateUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(Explosion.class)
public abstract class ExplosionMixin implements IExplosionEntityRecord {
    @Unique
    private List<Entity> affectedEntities;
    @Inject(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)V", at = @At("RETURN"))
    private void injectInit(World world, Entity entity, DamageSource damageSource, ExplosionBehavior behavior, double x, double y, double z, float power, boolean createFire, Explosion.DestructionType destructionType, CallbackInfo ci){
        this.affectedEntities = new ArrayList<>();
    }
    @Override
    public List<Entity> springExplosion$getAffectedEntities() {
        return affectedEntities;
    }

    @Shadow @Final private @Nullable Entity entity;

    @Shadow @Final private ExplosionBehavior behavior;

    @Redirect(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getOtherEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Ljava/util/List;"))
    private List<Entity> redirectGetOtherEntities(World instance, Entity entity, Box box){
        Entity causingEntity = this.entity;
        if (causingEntity instanceof Ownable ownable){
            return instance.getOtherEntities(entity, box, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.and(PredicateUtil.getNotSameTeamAsOwnerPredicate(ownable.getOwner())));
        }else {
            return instance.getOtherEntities(entity, box, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.and(PredicateUtil.getNotSameTeamAsOwnerPredicate(causingEntity)));
        }
    }

    @Inject(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", shift = At.Shift.AFTER))
    private void injectRecordEntity(CallbackInfo ci, @Local Entity entity){
        this.affectedEntities.add(entity);
    }

    @WrapOperation(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private boolean wrapModifyExplosionEntityDamage(Entity instance, DamageSource source, float amount, Operation<Boolean> original, @Local(ordinal = 5) double exposure){
        if (behavior instanceof FireworkExplosionBehavior<?> fireworkExplosionBehavior) {
            return original.call(instance, source, fireworkExplosionBehavior.modifyEntityExplosionDamage((Explosion)((Object)this), instance, amount, exposure));
        }
        return original.call(instance, source, amount);
    }
}
