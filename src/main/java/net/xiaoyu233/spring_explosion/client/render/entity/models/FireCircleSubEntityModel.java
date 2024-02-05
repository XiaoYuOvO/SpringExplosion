package net.xiaoyu233.spring_explosion.client.render.entity.models;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.entity.FireCircleSubEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class FireCircleSubEntityModel extends DefaultedEntityGeoModel<FireCircleSubEntity> {
    public FireCircleSubEntityModel() {
        super(new Identifier(SpringExplosion.MOD_ID, "fire_circle_sub"));
    }

    @Override
    public void setCustomAnimations(FireCircleSubEntity animatable, long instanceId, AnimationState<FireCircleSubEntity> animationState) {
        CoreGeoBone main = getAnimationProcessor().getBone("main");

        if (main != null) {
            main.setRotX(animatable.getPitch() * MathHelper.RADIANS_PER_DEGREE);
            main.setRotY(animatable.getYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
