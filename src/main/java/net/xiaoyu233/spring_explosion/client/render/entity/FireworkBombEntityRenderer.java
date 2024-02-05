package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.FireworkBombEntityModel;
import net.xiaoyu233.spring_explosion.entity.FireworkBombEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireworkBombEntityRenderer extends GeoEntityRenderer<FireworkBombEntity> {
    public FireworkBombEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FireworkBombEntityModel());
    }
}
