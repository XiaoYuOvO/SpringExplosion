package net.xiaoyu233.spring_explosion;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.xiaoyu233.spring_explosion.client.sound.SESoundEvents;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.item.SEItems;

public class SpringExplosion implements ModInitializer {
    public static final String MOD_ID = "spring_explosion";
    @Override
    public void onInitialize() {
        SEItems.registerItems();
        SEEntityTypes.registerEntities();
        SESoundEvents.registerSounds();
        ServerWorldEvents.LOAD.register((server, world) -> {
            SEItemComponents.FIREWORK_ITEM_BASE.get(SEItems.SPARK_SWORD.getDefaultStack());
        });
    }
}
