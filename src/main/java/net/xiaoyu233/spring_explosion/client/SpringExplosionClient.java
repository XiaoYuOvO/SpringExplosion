package net.xiaoyu233.spring_explosion.client;

import net.fabricmc.api.ClientModInitializer;
import net.xiaoyu233.spring_explosion.client.render.entity.EntityRenderers;
import net.xiaoyu233.spring_explosion.client.sound.SESoundEvents;

public class SpringExplosionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRenderers.register();
    }
}
