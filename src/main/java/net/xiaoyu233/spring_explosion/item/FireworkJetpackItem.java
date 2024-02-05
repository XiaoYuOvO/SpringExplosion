package net.xiaoyu233.spring_explosion.item;

import net.minecraft.item.ArmorMaterials;
import net.xiaoyu233.spring_explosion.client.render.item.FireworkJetpackArmorRenderer;
import net.xiaoyu233.spring_explosion.fireworks.FireworkJetpack;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class FireworkJetpackItem extends WearableFireworkItem<FireworkJetpack>{

    public FireworkJetpackItem(Settings settings) {
        super(ArmorMaterials.IRON, Type.CHESTPLATE, settings);
    }

    @Override
    public FireworkJetpack getFirework() {
        return FireworkJetpack.INSTANCE;
    }

    @Override
    protected @NotNull GeoArmorRenderer<?> createArmorRenderer() {
        return new FireworkJetpackArmorRenderer();
    }
}
