package net.xiaoyu233.spring_explosion.client.render.entity.models;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.entity.RocketAcceleratorEntity;
import net.xiaoyu233.spring_explosion.entity.SparkSwordEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class RocketAcceleratorModel extends DefaultedEntityGeoModel<RocketAcceleratorEntity> {
    public RocketAcceleratorModel() {
        super(new Identifier(SpringExplosion.MOD_ID, "rocket_accelerator"));
    }


    @Override
    public boolean crashIfBoneMissing() {
        return true;
    }

    @Override
    public void setCustomAnimations(RocketAcceleratorEntity animatable, long instanceId, AnimationState<RocketAcceleratorEntity> animationState) {
        CoreGeoBone main = getAnimationProcessor().getBone("main");
        if (main != null) {
            main.setRotX(animatable.getPitch() * MathHelper.RADIANS_PER_DEGREE);
            main.setRotY((-animatable.getYaw() + 180) * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
