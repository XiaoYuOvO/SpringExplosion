package net.xiaoyu233.spring_explosion.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.ItemEntity;
import net.minecraft.server.command.LootCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LootCommand.class)
public class LootCommandMixin {
    @WrapOperation(method = "method_13179", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;setToDefaultPickupDelay()V"))
    private static void injectStaticSpawn(ItemEntity instance, Operation<Void> original){
        instance.setVelocity(0,0.2,0);
        original.call(instance);
    }
}
