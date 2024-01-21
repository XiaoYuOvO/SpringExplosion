package net.xiaoyu233.spring_explosion.item;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.fireworks.RocketAccelerator;

public class SEItems {
    public static final SparkSwordItem SPARK_SWORD = register("spark_sword", new SparkSwordItem(new Item.Settings().maxCount(1).maxDamage(100)));
    public static final GyroItem GYRO = register("gyro", new GyroItem(new Item.Settings().maxCount(1).maxDamage(50)));
    public static final RocketAcceleratorItem ROCKET_ACCELERATOR = register("rocket_accelerator", new RocketAcceleratorItem(new Item.Settings().maxCount(1).maxDamage(40)));
    public static void registerItems() {
        //Empty but for static class loading
    }

    private static <T extends Item> T register(String path, T item){
        return Registry.register(Registries.ITEM, id(path), item);
    }

    private static Identifier id(String path) {
        return new Identifier(SpringExplosion.MOD_ID, path);
    }
}
