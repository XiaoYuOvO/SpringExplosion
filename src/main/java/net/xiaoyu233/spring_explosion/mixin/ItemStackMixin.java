package net.xiaoyu233.spring_explosion.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.xiaoyu233.spring_explosion.item.IFireworkItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract Item getItem();

    @Inject(method = "getMaxDamage", at = @At("HEAD"), cancellable = true)
    private void injectGetMaxDamage(CallbackInfoReturnable<Integer> cir){
        if (this.getItem() instanceof IFireworkItem<?> baseFireworkItem){
            cir.setReturnValue(baseFireworkItem.getMaxDamage((ItemStack) (Object)this));
            cir.cancel();
        }
    }
}
