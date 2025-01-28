package net.xiaoyu233.spring_explosion.mixin.client;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.xiaoyu233.spring_explosion.components.items.FireworkItemBaseComponent;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.fireworks.SparkSword;
import net.xiaoyu233.spring_explosion.item.SEItems;
import net.xiaoyu233.spring_explosion.util.CollisionUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OtherClientPlayerEntity.class)
public abstract class OtherClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public OtherClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void injectTickOtherPlayers(CallbackInfo ci){
        ItemStack stackInHand = this.getStackInHand(this.getActiveHand());
        if (stackInHand.isOf(SEItems.SPARK_SWORD)) {
            FireworkItemBaseComponent fireworkItemBaseComponent = SEItemComponents.FIREWORK_ITEM_BASE.get(stackInHand);
            if (fireworkItemBaseComponent.isFiring()) {
                SparkSword.damageEntityAndSpawnParticles(this.getWorld(), CollisionUtil.PosingMethod.EYE, this, this, (float) (stackInHand.getMaxDamage() - stackInHand.getDamage()) / stackInHand.getMaxDamage());
            }
        }
    }
}
