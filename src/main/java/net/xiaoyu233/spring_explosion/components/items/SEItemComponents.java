package net.xiaoyu233.spring_explosion.components.items;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;

public class SEItemComponents {
    public static final ComponentKey<FireworkItemBaseComponent> FIREWORK_ITEM_BASE = ComponentRegistry.getOrCreate(new Identifier(SpringExplosion.MOD_ID, "firework_item_base"), FireworkItemBaseComponent.class);
    public static final ComponentKey<MineControllerItemComponent> FIREWORK_MINE_CONTROLLER = ComponentRegistry.getOrCreate(new Identifier(SpringExplosion.MOD_ID, "firework_mine_controller"), MineControllerItemComponent.class);

}
