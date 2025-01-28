package net.xiaoyu233.spring_explosion.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.effect.SEEffects;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract @Nullable StatusEffectInstance getStatusEffect(StatusEffect effect);

    @Inject(method = "damage", at = @At("HEAD"))
    private void injectOilyDamage(DamageSource source, float dmg, CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true) LocalFloatRef amount){
        if (source.isIn(DamageTypeTags.IS_EXPLOSION)  || source.isIn(DamageTypeTags.IS_FIRE)) {
            StatusEffectInstance statusEffect = this.getStatusEffect(SEEffects.OILY);
            if (statusEffect != null) {
                if (source.isIn(DamageTypeTags.IS_EXPLOSION)) {
                    this.setFireTicks((int) (amount.get() * 20));
                }
                amount.set((amount.get() * (1 + 0.4f * (statusEffect.getAmplifier() + 1))));
            }
        }
    }
}
