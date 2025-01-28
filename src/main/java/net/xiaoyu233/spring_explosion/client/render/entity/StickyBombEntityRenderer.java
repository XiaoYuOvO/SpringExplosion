package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.StickyBombEntityModel;
import net.xiaoyu233.spring_explosion.entity.StickyBombEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class StickyBombEntityRenderer extends GeoEntityRenderer<StickyBombEntity> {
    public StickyBombEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new StickyBombEntityModel());
    }
}
