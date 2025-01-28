package net.xiaoyu233.spring_explosion.client.render.entity.models;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.entity.FireworkMissileBulletEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class FireworkMissileBulletEntityModel extends DefaultedEntityGeoModel<FireworkMissileBulletEntity> {
    public FireworkMissileBulletEntityModel() {
        super(new Identifier(SpringExplosion.MOD_ID, "firework_bullet"));
    }

    @Override
    public void setCustomAnimations(FireworkMissileBulletEntity animatable, long instanceId, AnimationState<FireworkMissileBulletEntity> animationState) {
        CoreGeoBone main = getAnimationProcessor().getBone("main");

        if (main != null) {
            main.setRotX(-animatable.getPitch() * MathHelper.RADIANS_PER_DEGREE);
            main.setRotY(-animatable.getYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
