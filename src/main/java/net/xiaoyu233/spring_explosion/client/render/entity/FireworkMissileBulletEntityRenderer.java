package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.FireworkMissileBulletEntityModel;
import net.xiaoyu233.spring_explosion.entity.FireworkMissileBulletEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireworkMissileBulletEntityRenderer extends GeoEntityRenderer<FireworkMissileBulletEntity> {
    public FireworkMissileBulletEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FireworkMissileBulletEntityModel());
    }
}
