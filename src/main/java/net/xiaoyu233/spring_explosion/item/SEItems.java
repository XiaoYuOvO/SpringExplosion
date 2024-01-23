package net.xiaoyu233.spring_explosion.item;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.fireworks.FireworkMortar;
import net.xiaoyu233.spring_explosion.fireworks.RocketAccelerator;

public class SEItems {
    public static final SparkSwordItem SPARK_SWORD = register("spark_sword", new SparkSwordItem(new Item.Settings().maxCount(1).maxDamage(100)));
    public static final GyroItem GYRO = register("gyro", new GyroItem(new Item.Settings().maxCount(1).maxDamage(50)));
    public static final RocketAcceleratorItem ROCKET_ACCELERATOR = register("rocket_accelerator", new RocketAcceleratorItem(new Item.Settings().maxCount(1).maxDamage(40)));
    public static final FireworkMortarItem FIREWORK_MORTAR = register("firework_mortar", new FireworkMortarItem(new Item.Settings().maxCount(1).maxDamage(40)));
    public static final MineItem FIREWORK_MINE = register("firework_mine", new MineItem(new Item.Settings().maxCount(1).maxDamage(0)));
    public static final MineControllerItem FIREWORK_MINE_CONTROLLER = register("firework_mine_controller", new MineControllerItem(new Item.Settings().maxCount(1).maxDamage(MineItem.PREPARE_TIME)));
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
