package net.xiaoyu233.spring_explosion.client.render.entity.models;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.entity.StickyBombEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class StickyBombEntityModel extends DefaultedEntityGeoModel<StickyBombEntity> {
    public StickyBombEntityModel() {
        super(new Identifier(SpringExplosion.MOD_ID, "sticky_bomb"));
    }

    @Override
    public void setCustomAnimations(StickyBombEntity animatable, long instanceId, AnimationState<StickyBombEntity> animationState) {
        CoreGeoBone main = getAnimationProcessor().getBone("main");

        if (main != null) {
            Vec3i multiply = animatable.getStuckFacing().getVector().multiply(90);
            main.setRotX(-multiply.getZ() * MathHelper.RADIANS_PER_DEGREE);
            main.setRotZ(multiply.getX() * MathHelper.RADIANS_PER_DEGREE);
        }
    }

}
