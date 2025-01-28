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
        EntityRendererRegistry.register(SEEntityTypes.FIRE_CIRCLE, FireCircleEntityRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.FIRE_CIRCLE_SUB, FireCircleSubEntityRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.OILY_BOMB, OilyBombEntityRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.GLOWING_BOMB, GlowingBombEntityRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.SMOKE_BOMB, SmokeBombEntityRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.FROZEN_BOMB, FrozenBombEntityRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.FIREWORK_BOMB, FireworkBombEntityRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.FIRECRACKERS, FirecrackersEntityRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.FIREWORK_BULLET, FireworkBulletEntityRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.FIREWORK_MACHINE_GUN, FireworkMachineGunEntityRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.FIREWORK_JETPACK, FireworkJetpackEntityRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.CRACKER, CrackerEntityRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.STICKY_BOMB, StickyBombEntityRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.FIREWORK_MISSILE, FireworkMissileEntityRenderer::new);
        EntityRendererRegistry.register(SEEntityTypes.FIREWORK_MISSILE_BULLET, FireworkMissileBulletEntityRenderer::new);
    }
}
