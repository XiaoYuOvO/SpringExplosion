package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.FireworkJetpackEntityModel;
import net.xiaoyu233.spring_explosion.entity.FireworkJetpackEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireworkJetpackEntityRenderer extends GeoEntityRenderer<FireworkJetpackEntity> {
    public FireworkJetpackEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FireworkJetpackEntityModel());
    }
}
