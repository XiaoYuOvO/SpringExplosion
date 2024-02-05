package net.xiaoyu233.spring_explosion.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.fireworks.FireCircle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SEItems {
    private static final List<Item> FIREWORK_ITEMS = new ArrayList<>();
    public static final SparkSwordItem SPARK_SWORD = register("spark_sword", new SparkSwordItem(new Item.Settings().maxCount(1).maxDamage(100)));
    public static final GyroItem GYRO = register("gyro", new GyroItem(new Item.Settings().maxCount(1).maxDamage(50)));
    public static final RocketAcceleratorItem ROCKET_ACCELERATOR = register("rocket_accelerator", new RocketAcceleratorItem(new Item.Settings().maxCount(1).maxDamage(40)));
    public static final FireworkMortarItem FIREWORK_MORTAR = register("firework_mortar", new FireworkMortarItem(new Item.Settings().maxCount(1).maxDamage(40)));
    public static final MineItem FIREWORK_MINE = register("firework_mine", new MineItem(new Item.Settings().maxCount(1).maxDamage(0)));
    public static final MineControllerItem FIREWORK_MINE_CONTROLLER = register("firework_mine_controller", new MineControllerItem(new Item.Settings().maxCount(1).maxDamage(MineItem.PREPARE_TIME)));
    public static final FireCircleItem FIRE_CIRCLE = register("fire_circle", new FireCircleItem(new Item.Settings().maxCount(1).maxDamage(FireCircle.FUSE_TIME)));
    public static final FireworkBombItem FIREWORK_BOMB = register("firework_bomb", new FireworkBombItem(new Item.Settings().maxCount(32)));
    public static final OilyBombItem OILY_BOMB = register("oily_bomb", new OilyBombItem(new Item.Settings().maxCount(16)));
    public static final GlowingBombItem GLOWING_BOMB = register("glowing_bomb", new GlowingBombItem(new Item.Settings().maxCount(16)));
    public static final SmokeBombItem SMOKE_BOMB = register("smoke_bomb", new SmokeBombItem(new Item.Settings().maxCount(16)));
    public static final FrozenBombItem FROZEN_BOMB = register("frozen_bomb", new FrozenBombItem(new Item.Settings().maxCount(16)));
    public static final FirecrackersItem FIRECRACKERS = register("firecrackers", new FirecrackersItem(new Item.Settings().maxDamage(20)));
    public static final FireworkMachineGunItem FIREWORK_MACHINE_GUN = register("firework_machine_gun", new FireworkMachineGunItem(new Item.Settings().maxDamage(300)));
    public static final FireworkJetpackItem FIREWORK_JETPACK = register("firework_jetpack", new FireworkJetpackItem(new Item.Settings().maxDamage(180)));
    public static void registerItems() {
        //Empty but for static class loading
        ItemGroup build = FabricItemGroup.builder().displayName(Text.translatable("itemGroup.spring_explosion.fireworks")).icon(() -> new ItemStack(FIREWORK_MORTAR)).entries((displayContext, entries) -> entries.addAll(FIREWORK_ITEMS.stream().map(Item::getDefaultStack).collect(Collectors.toList()))).build();
        Registry.register(Registries.ITEM_GROUP, new Identifier(SpringExplosion.MOD_ID, "fireworks_group"), build);
    }

    private static <T extends Item> T register(String path, T item){
        FIREWORK_ITEMS.add(item);
        return Registry.register(Registries.ITEM, id(path), item);
    }

    private static Identifier id(String path) {
        return new Identifier(SpringExplosion.MOD_ID, path);
    }
}
