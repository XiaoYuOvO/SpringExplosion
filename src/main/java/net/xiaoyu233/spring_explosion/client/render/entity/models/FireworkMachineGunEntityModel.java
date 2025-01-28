package net.xiaoyu233.spring_explosion.client.render.entity.models;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.entity.FireworkMachineGunEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class FireworkMachineGunEntityModel extends DefaultedEntityGeoModel<FireworkMachineGunEntity> {
    public FireworkMachineGunEntityModel() {
        super(new Identifier(SpringExplosion.MOD_ID, "firework_machine_gun"));
    }

    @Override
    public void setCustomAnimations(FireworkMachineGunEntity animatable, long instanceId, AnimationState<FireworkMachineGunEntity> animationState) {
        CoreGeoBone main = getAnimationProcessor().getBone("main");

        if (main != null) {
            main.setRotX(animatable.getPitch() * MathHelper.RADIANS_PER_DEGREE);
            main.setRotY(-(180 + animatable.getYaw()) * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
