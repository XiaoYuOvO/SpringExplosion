package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.FireworkBulletEntityModel;
import net.xiaoyu233.spring_explosion.entity.FireworkBulletEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireworkBulletEntityRenderer extends GeoEntityRenderer<FireworkBulletEntity> {
    public FireworkBulletEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FireworkBulletEntityModel());
    }
}
