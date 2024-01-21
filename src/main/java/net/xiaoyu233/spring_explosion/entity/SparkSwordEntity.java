package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.fireworks.SparkSword;

public class SparkSwordEntity extends BaseFireworkEntity<SparkSwordEntity, SparkSword> {
    public SparkSwordEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected SparkSword getFirework() {
        return SparkSword.INSTANCE;
    }

    @Override
    protected Box calculateBoundingBox() {
        return super.calculateBoundingBox().contract(0.5,0,0);
    }

    @Override
    public void setOnGround(boolean onGround) {
        super.setOnGround(onGround);
        if (onGround) {
            BlockState blockState = this.getLandingBlockState();
            if (blockState.isIn(BlockTags.STAIRS)) {
                if (blockState.get(StairsBlock.HALF) == BlockHalf.BOTTOM) {
                    Direction stairDirection = blockState.get(StairsBlock.FACING);
                    Direction thisDirection = this.getHorizontalFacing();
                    if (thisDirection == stairDirection) {
                        this.setRotation(this.getYaw(), -45);
                    }else if (thisDirection.getOpposite() == stairDirection){
                        this.setRotation(this.getYaw(), 45);
                    }else {
                        this.setRotation(this.getYaw(), -5);
                    }
                }
            }else {
                this.setRotation(this.getYaw(), -5);
            }
        }
    }

}
