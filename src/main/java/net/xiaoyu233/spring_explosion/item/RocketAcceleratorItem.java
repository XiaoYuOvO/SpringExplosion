package net.xiaoyu233.spring_explosion.item;

import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.xiaoyu233.spring_explosion.client.render.item.RocketAcceleratorArmorRenderer;
import net.xiaoyu233.spring_explosion.fireworks.RocketAccelerator;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class RocketAcceleratorItem extends WearableFireworkItem<RocketAccelerator> {

    public RocketAcceleratorItem(Item.Settings settings) {
        super(ArmorMaterials.IRON, Type.LEGGINGS, settings);
    }

    @Override
    public RocketAccelerator getFirework() {
        return RocketAccelerator.INSTANCE;
    }

    @Override
    protected @NotNull GeoArmorRenderer<?> createArmorRenderer() {
        return new RocketAcceleratorArmorRenderer();
    }
}
