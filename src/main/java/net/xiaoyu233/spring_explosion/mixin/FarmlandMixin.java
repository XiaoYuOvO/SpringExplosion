package net.xiaoyu233.spring_explosion.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmlandBlock.class)
public class FarmlandMixin {
    @Inject(method = "onLandedUpon", at = @At("HEAD"), cancellable = true)
    private void redirectPlayerCheck(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci){
        if (entity instanceof ServerPlayerEntity player && player.interactionManager.getGameMode() == GameMode.ADVENTURE){
            ci.cancel();
        }
    }
}
