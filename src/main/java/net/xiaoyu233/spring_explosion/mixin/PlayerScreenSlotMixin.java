package net.xiaoyu233.spring_explosion.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.item.ItemStack;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.item.WearableFireworkItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/screen/PlayerScreenHandler$1")
public class PlayerScreenSlotMixin {
    @WrapOperation(method = "canTakeItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;hasBindingCurse(Lnet/minecraft/item/ItemStack;)Z"))
    private boolean redirectCheckBindingCurse(ItemStack stack, Operation<Boolean> original){
        if (stack.getItem() instanceof WearableFireworkItem<?>){
            return SEItemComponents.FIREWORK_ITEM_BASE.get(stack).isFiring();
        }
        return original.call(stack);
    }
}
