package net.xiaoyu233.spring_explosion.item;

import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.FirecrackersItemRenderer;
import net.xiaoyu233.spring_explosion.components.items.FirecrackerItemComponent;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.entity.FirecrackersEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.util.ItemUtil;

import java.util.function.Supplier;

public class FirecrackersItem extends DefaultGeoItem<FirecrackersItem, FirecrackersItemRenderer>{
    public FirecrackersItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return SEItemComponents.FIRECRACKER.get(stack).isDeploying();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stackInHand = user.getStackInHand(hand);
        FirecrackerItemComponent firecrackerItemComponent = SEItemComponents.FIRECRACKER.get(stackInHand);
        if (!firecrackerItemComponent.isDeploying()){
            FirecrackersEntity newFirecracker = SEEntityTypes.FIRECRACKERS.create(world);
            newFirecracker.updatePositionAndAngles(user.getX(), user.getY(), user.getZ(), 0f, 0f);
            newFirecracker.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, user.getPos());
            newFirecracker.setOwner(user);
            firecrackerItemComponent.setLastFirecracker(newFirecracker);
            world.spawnEntity(newFirecracker);
            firecrackerItemComponent.setDeploying(true);
            return TypedActionResult.success(stackInHand);
        }else return TypedActionResult.pass(stackInHand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient && entity instanceof LivingEntity livingEntity) {
            FirecrackerItemComponent firecrackerItemComponent = SEItemComponents.FIRECRACKER.get(stack);
            if (firecrackerItemComponent.isDeploying()) {
                firecrackerItemComponent.getLastFirecracker(((ServerWorld) world)).ifPresent((firecracker) -> {
                    if (entity.squaredDistanceTo(firecracker) > 0.75d){
                        FirecrackersEntity newFirecracker = SEEntityTypes.FIRECRACKERS.create(world);
                        newFirecracker.updatePositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), 0f, 0f);
                        newFirecracker.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, firecracker.getPos());
                        newFirecracker.setLastFirecracker(firecracker);
                        newFirecracker.setOwner(entity);
                        firecracker.setNextFirecracker(newFirecracker);
                        firecrackerItemComponent.setLastFirecracker(newFirecracker);
                        world.spawnEntity(newFirecracker);
                        ItemUtil.damageItem(stack, 1, livingEntity);
                    }
                });
            }
        }
    }

    @Override
    Supplier<FirecrackersItemRenderer> getRendererSupplier() {
        return FirecrackersItemRenderer::new;
    }


}
