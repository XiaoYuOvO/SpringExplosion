package net.xiaoyu233.spring_explosion.item;

import net.xiaoyu233.spring_explosion.client.render.item.FireworkMortarItemRenderer;
import net.xiaoyu233.spring_explosion.fireworks.FireworkMortar;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class FireworkMortarItem extends BaseFireworkItem<FireworkMortar, FireworkMortarItem, FireworkMortarItemRenderer>{
    private static final RawAnimation IGNITE_ANIM = RawAnimation.begin().thenPlay("animation.firework_mortar.ignite");
    public static final String IGNITE_ANIM_NAME = "animation.firework_mortar.ignite";
    public static final String IGNITE_CONTROLLER_NAME = "Igniting";
    public FireworkMortarItem(Settings settings) {
        super(FireworkMortar.INSTANCE, settings);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, IGNITE_CONTROLLER_NAME, 0, state -> PlayState.STOP).triggerableAnim(IGNITE_ANIM_NAME, IGNITE_ANIM));
    }
}
