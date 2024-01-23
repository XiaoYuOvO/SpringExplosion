package net.xiaoyu233.spring_explosion.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.xiaoyu233.spring_explosion.util.PredicateUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {

    @Shadow @Final private @Nullable Entity entity;

    @Redirect(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getOtherEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Ljava/util/List;"))
    private List<Entity> redirectGetOtherEntities(World instance, Entity entity, Box box){
        Entity causingEntity = this.entity;
        if (causingEntity instanceof Ownable ownable){
            return instance.getOtherEntities(entity, box, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.and(PredicateUtil.getNotSameTeamAsOwnerPredicate(ownable.getOwner())));
        }else {
            return instance.getOtherEntities(entity, box, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.and(PredicateUtil.getNotSameTeamAsOwnerPredicate(causingEntity)));
        }
    }
}
