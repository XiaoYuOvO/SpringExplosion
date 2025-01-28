package net.xiaoyu233.spring_explosion.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SnowBlock;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.xiaoyu233.spring_explosion.entity.FirecrackersEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowBlock.class)
public class SnowBlockMixin {
    @Shadow @Final protected static VoxelShape[] LAYERS_TO_SHAPE;

    @Shadow @Final public static IntProperty LAYERS;

    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    private void injectFirecrackersCollision(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir){
        if (context instanceof EntityShapeContext entityShapeContext && entityShapeContext.getEntity() instanceof FirecrackersEntity){
            cir.setReturnValue(LAYERS_TO_SHAPE[state.get(LAYERS)]);
        }
    }

    @ModifyConstant(method = "randomTick", constant = @Constant(intValue = 11))
    private int modifyNoSmelt(int constant){
        return Integer.MAX_VALUE;
    }
}
