package net.xiaoyu233.spring_explosion.mixin.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.Hand;
import net.xiaoyu233.spring_explosion.item.IFireworkItem;
import net.xiaoyu233.spring_explosion.item.SEItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(method = "getArmPose", at = @At("HEAD"), cancellable = true)
    private static void injectGetArmPose(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir){
        if (player.getStackInHand(hand).getItem() instanceof IFireworkItem<?> fireworkItem){
            cir.setReturnValue(fireworkItem.getFirework().getArmPose());
            cir.cancel();
        }
    }
}
