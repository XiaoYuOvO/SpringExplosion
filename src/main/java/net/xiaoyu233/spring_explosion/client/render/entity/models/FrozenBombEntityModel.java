package net.xiaoyu233.spring_explosion.client.render.entity.models;

import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.entity.FrozenBombEntity;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class FrozenBombEntityModel extends DefaultedEntityGeoModel<FrozenBombEntity> {
    public FrozenBombEntityModel() {
        super(new Identifier(SpringExplosion.MOD_ID, "frozen_bomb"));
    }

    @Override
    public void setCustomAnimations(FrozenBombEntity animatable, long instanceId, AnimationState<FrozenBombEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        this.getBone("main").ifPresent(bone -> {
            bone.setScaleX(0.5f);
            bone.setScaleY(0.5f);
            bone.setScaleZ(0.5f);
        });
    }
}
