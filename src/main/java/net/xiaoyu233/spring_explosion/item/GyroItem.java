package net.xiaoyu233.spring_explosion.item;

import net.xiaoyu233.spring_explosion.client.render.item.GyroItemRenderer;
import net.xiaoyu233.spring_explosion.fireworks.Gyro;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class GyroItem extends BaseFireworkItem<Gyro,GyroItem, GyroItemRenderer> {
    private static final RawAnimation IGNITE_ANIM = RawAnimation.begin().thenPlay("animation.gyro.ignite");
    public static final String IGNITE_ANIM_NAME = "animation.gyro.ignite";
    public static final String IGNITE_CONTROLLER_NAME = "Igniting";

    public GyroItem(Settings settings) {
        super(Gyro.INSTANCE, settings);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, IGNITE_CONTROLLER_NAME, 0, state -> PlayState.STOP).triggerableAnim(IGNITE_ANIM_NAME, IGNITE_ANIM));
    }
}
