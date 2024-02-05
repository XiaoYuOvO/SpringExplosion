package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.OilyBombEntityModel;
import net.xiaoyu233.spring_explosion.entity.OilyBombEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class OilyBombEntityRenderer extends GeoEntityRenderer<OilyBombEntity> {
    public OilyBombEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new OilyBombEntityModel());
    }
}
