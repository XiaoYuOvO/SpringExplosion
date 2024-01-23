package net.xiaoyu233.spring_explosion.client.render.entity.models;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.entity.FireworkMortarEntity;
import net.xiaoyu233.spring_explosion.entity.GyroEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class FireworkMortarModel extends DefaultedEntityGeoModel<FireworkMortarEntity> {
    public FireworkMortarModel() {
        super(new Identifier(SpringExplosion.MOD_ID, "firework_mortar"));
    }

    @Override
    public void setCustomAnimations(FireworkMortarEntity animatable, long instanceId, AnimationState<FireworkMortarEntity> animationState) {
        CoreGeoBone main = getAnimationProcessor().getBone("main");

        if (main != null) {
            main.setRotX(-animatable.getPitch() * MathHelper.RADIANS_PER_DEGREE);
            main.setRotY(-animatable.getYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
