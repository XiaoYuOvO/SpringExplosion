package net.xiaoyu233.spring_explosion.client.render.entity;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;

public class EntityRenderers {
    public static void register() {
        EntityRendererRegistry.register(SEEntityTypes.SPARK_SWORD, SparkSwordRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.GYRO, GyroRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.ROCKET_ACCELERATOR, RocketAcceleratorRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.FIREWORK_MORTAR, FireworkMortarRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.FIREWORK_MINE, MineRenderer::new);
    }
}
