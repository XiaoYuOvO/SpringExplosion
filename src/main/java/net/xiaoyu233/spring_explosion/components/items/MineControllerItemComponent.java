package net.xiaoyu233.spring_explosion.components.items;

import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.entity.MineEntity;
import net.xiaoyu233.spring_explosion.item.MineItem;

import java.util.Optional;

public class MineControllerItemComponent extends ItemComponent  {
    private static final String PREPARE_TIME_KEY = "PrepareTime";
    private static final String TARGET_KEY = "Target";
    public MineControllerItemComponent(ItemStack stack) {
        super(stack);
    }

    public int getPrepareTime() {
        //dont use putInt, it will remove the tag when prepare time is 0
        if (!this.hasTag(PREPARE_TIME_KEY)) this.getOrCreateRootTag().putInt(PREPARE_TIME_KEY, MineItem.PREPARE_TIME);
        return getInt(PREPARE_TIME_KEY);
    }

    public Optional<MineEntity> getTarget(ServerWorld world) {
        if (!this.hasTag(TARGET_KEY)) return Optional.empty();
        Entity entity = world.getEntity(this.getUuid(TARGET_KEY));
        if (entity instanceof MineEntity mineEntity){
            return Optional.of(mineEntity);
        }
        return Optional.empty();
    }

    public void setPrepareTime(int time) {
        //dont use putInt, it will remove the tag when prepare time is 0
        this.getOrCreateRootTag().putInt(PREPARE_TIME_KEY, time);
    }

    public void setTarget(MineEntity entity) {
        this.putUuid(TARGET_KEY, entity.getUuid());
    }
}
