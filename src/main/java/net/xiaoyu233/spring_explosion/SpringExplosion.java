package net.xiaoyu233.spring_explosion;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.xiaoyu233.spring_explosion.client.sound.SESoundEvents;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.effect.SEEffects;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.item.SEItems;
import net.xiaoyu233.spring_explosion.particle.SEParticles;

public class SpringExplosion implements ModInitializer {
    public static final String MOD_ID = "spring_explosion";
    @Override
    public void onInitialize() {
        SEItems.registerItems();
        SEEntityTypes.registerEntities();
        SESoundEvents.registerSounds();
        SEEffects.register();
        SEParticles.initialize();
        ServerWorldEvents.LOAD.register((server, world) -> {
            //Prevent for crash :)
            SEItemComponents.FIREWORK_ITEM_BASE.get(SEItems.SPARK_SWORD.getDefaultStack());
            SEItemComponents.FIREWORK_ITEM_BASE.get(SEItems.GYRO.getDefaultStack());
            SEItemComponents.FIREWORK_ITEM_BASE.get(SEItems.ROCKET_ACCELERATOR.getDefaultStack());
            SEItemComponents.FIREWORK_ITEM_BASE.get(SEItems.FIREWORK_MORTAR.getDefaultStack());
            SEItemComponents.FIREWORK_ITEM_BASE.get(SEItems.FIRE_CIRCLE.getDefaultStack());
            SEItemComponents.FIREWORK_ITEM_BASE.get(SEItems.FIREWORK_BOMB.getDefaultStack());
            SEItemComponents.FIREWORK_ITEM_BASE.get(SEItems.FROZEN_BOMB.getDefaultStack());
            SEItemComponents.FIREWORK_ITEM_BASE.get(SEItems.GLOWING_BOMB.getDefaultStack());
            SEItemComponents.FIREWORK_ITEM_BASE.get(SEItems.OILY_BOMB.getDefaultStack());
            SEItemComponents.FIREWORK_ITEM_BASE.get(SEItems.SMOKE_BOMB.getDefaultStack());
            SEItemComponents.FIREWORK_ITEM_BASE.get(SEItems.FIREWORK_MACHINE_GUN.getDefaultStack());
            SEItemComponents.FIREWORK_MINE_CONTROLLER.get(SEItems.FIREWORK_MINE_CONTROLLER.getDefaultStack());
            SEItemComponents.FIRECRACKER.get(SEItems.FIRECRACKERS.getDefaultStack());
        });
    }
}
