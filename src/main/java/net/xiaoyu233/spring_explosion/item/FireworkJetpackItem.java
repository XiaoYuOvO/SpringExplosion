package net.xiaoyu233.spring_explosion.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
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
    protected boolean canSpawnEntity() {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

    }

    @Override
    @Environment(EnvType.CLIENT)
    protected @NotNull GeoArmorRenderer<?> createArmorRenderer() {
        return new FireworkJetpackArmorRenderer();
    }
}
