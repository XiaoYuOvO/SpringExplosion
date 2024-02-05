package net.xiaoyu233.spring_explosion.components.items;

import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.xiaoyu233.spring_explosion.entity.FirecrackersEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FirecrackerItemComponent extends ItemComponent {
    private static final String DEPLOY_KEY = "IsDeploying";
    private static final String FIRECRACKER_KEY = "LastFirecracker";
    @Nullable
    private FirecrackersEntity lastFirecrackerCache;
    public FirecrackerItemComponent(ItemStack stack) {
        super(stack);
    }

    public boolean isDeploying() {
        if (!this.hasTag(DEPLOY_KEY)) this.putBoolean(DEPLOY_KEY, false);
        return getBoolean(DEPLOY_KEY);
    }

    public void setDeploying(boolean b){
        this.putBoolean(DEPLOY_KEY, b);
    }

    @Override
    public void onTagInvalidated() {
        super.onTagInvalidated();
        this.lastFirecrackerCache = null;
    }

    public void setLastFirecracker(FirecrackersEntity firecracker){
        this.lastFirecrackerCache = firecracker;
        this.putUuid(FIRECRACKER_KEY, firecracker.getUuid());
    }

    public Optional<FirecrackersEntity> getLastFirecracker(ServerWorld serverWorld){
        if (!this.hasTag(FIRECRACKER_KEY)) return Optional.empty();
        if (this.lastFirecrackerCache == null){
            Entity entity = serverWorld.getEntity(this.getUuid(FIRECRACKER_KEY));
            if (entity instanceof FirecrackersEntity firecracker){
                this.lastFirecrackerCache = firecracker;
            }
        }
        return Optional.ofNullable(lastFirecrackerCache);
    }

}
