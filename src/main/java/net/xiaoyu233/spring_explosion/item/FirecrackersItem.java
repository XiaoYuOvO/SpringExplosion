package net.xiaoyu233.spring_explosion.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.FirecrackersItemRenderer;
import net.xiaoyu233.spring_explosion.components.items.FirecrackerItemComponent;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.entity.FirecrackersEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.util.ItemUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class FirecrackersItem extends DefaultGeoItem<FirecrackersItem, FirecrackersItemRenderer> implements IDropUseItem{
    private static final ImmutableMultimap<EntityAttribute, EntityAttributeModifier> SPEED_MODIFIER = new ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier>()
            .put(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                    new EntityAttributeModifier(UUID.fromString("edcacefb-72c8-4664-a8ca-3af128c3512c"), "Firecrackers Deploy Speed", 0.03d, EntityAttributeModifier.Operation.ADDITION))
            .build();
    private final @NotNull Supplier<List<Text>> lazyTooltip;

    public FirecrackersItem(Settings settings) {
        super(settings);
        this.lazyTooltip = BaseFireworkItem.createTooltipCache(Optional.empty(), this);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return SEItemComponents.FIRECRACKER.get(stack).isDeploying();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.addAll(this.lazyTooltip.get());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stackInHand = user.getStackInHand(hand);
        FirecrackerItemComponent firecrackerItemComponent = SEItemComponents.FIRECRACKER.get(stackInHand);
        if (!firecrackerItemComponent.isDeploying()){
            FirecrackersEntity newFirecracker = SEEntityTypes.FIRECRACKERS.create(world);
            newFirecracker.refreshPositionAndAngles(user.getX(), user.getY(), user.getZ(), 0f, 0f);
            newFirecracker.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, user.getPos());
            newFirecracker.setOwner(user);
            firecrackerItemComponent.setLastFirecracker(newFirecracker);
            world.spawnEntity(newFirecracker);
            firecrackerItemComponent.setDeploying(true);
            return TypedActionResult.success(stackInHand);
        }else return TypedActionResult.pass(stackInHand);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        FirecrackerItemComponent firecrackerItemComponent = SEItemComponents.FIRECRACKER.get(stack);
        if (firecrackerItemComponent.isDeploying()){
            return SPEED_MODIFIER;
        }
        return super.getAttributeModifiers(stack, slot);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient && entity instanceof LivingEntity livingEntity) {
            FirecrackerItemComponent firecrackerItemComponent = SEItemComponents.FIRECRACKER.get(stack);
            if (firecrackerItemComponent.isDeploying()) {
                firecrackerItemComponent.getLastFirecracker(((ServerWorld) world)).ifPresent((firecracker) -> {
                    if (entity.squaredDistanceTo(firecracker) > 0.75d){
                        FirecrackersEntity newFirecracker = SEEntityTypes.FIRECRACKERS.create(world);
                        newFirecracker.refreshPositionAndAngles(entity.getX(), entity.getY() + 0.5d, entity.getZ(), 0f, 0f);
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


    @Override
    public ItemEntity useOnDrop(World world, ItemStack stack, LivingEntity user) {
        return null;
    }

    @Override
    public boolean canUseOnDrop(ItemStack stack) {
        return true;
    }
}
