package net.xiaoyu233.spring_explosion.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.xiaoyu233.spring_explosion.item.IFireworkItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Shadow private ItemStack mainHand;

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "applyEquipOffset", at = @At("HEAD"), cancellable = true)
    private void injectApplyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress, CallbackInfo ci){
        if(this.client.player.getMainArm() == arm && this.mainHand.getItem() instanceof IFireworkItem<?>){
            int i = arm == Arm.RIGHT ? 1 : -1;
            matrices.translate((float)i * 0.56F, -0.52F, -0.72F);
            ci.cancel();
        }
    }
}
