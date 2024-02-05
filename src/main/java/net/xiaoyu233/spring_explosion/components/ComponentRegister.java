package net.xiaoyu233.spring_explosion.components;

import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer;
import net.xiaoyu233.spring_explosion.components.items.FirecrackerItemComponent;
import net.xiaoyu233.spring_explosion.components.items.FireworkItemBaseComponent;
import net.xiaoyu233.spring_explosion.components.items.MineControllerItemComponent;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.item.IFireworkItem;
import net.xiaoyu233.spring_explosion.item.SEItems;

public class ComponentRegister implements ItemComponentInitializer {
    @Override
    public void registerItemComponentFactories(ItemComponentFactoryRegistry registry) {
        registry.register(item -> item instanceof IFireworkItem<?>, SEItemComponents.FIREWORK_ITEM_BASE, FireworkItemBaseComponent::new);
        registry.register(SEItems.FIREWORK_MINE_CONTROLLER,  SEItemComponents.FIREWORK_MINE_CONTROLLER,  MineControllerItemComponent::new);
        registry.register(SEItems.FIRECRACKERS,  SEItemComponents.FIRECRACKER,  FirecrackerItemComponent::new);
    }
}
