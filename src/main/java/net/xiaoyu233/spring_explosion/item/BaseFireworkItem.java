package net.xiaoyu233.spring_explosion.item;

import com.google.common.base.Suppliers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.components.items.FireworkItemBaseComponent;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.fireworks.BaseFirework;
import net.xiaoyu233.spring_explosion.fireworks.FireworkItemToEntityAction;
import net.xiaoyu233.spring_explosion.fireworks.FireworkUsage;
import net.xiaoyu233.spring_explosion.util.ItemUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class BaseFireworkItem<F extends BaseFirework<?, I, IR>, I extends BaseFireworkItem<F,I,IR>, IR extends GeoRenderer<I>>  extends DefaultGeoItem<I, IR> implements IFireworkItem<F>, IDropUseItem{
    private final F firework;
    private final Supplier<List<Text>> lazyTooltip;
    public BaseFireworkItem(F firework, Settings settings) {
        super(settings);
        this.firework = firework;
        this.lazyTooltip = createTooltipCache(Optional.of(firework), this);
    }

    public static @NotNull Supplier<List<Text>> createTooltipCache(Optional<BaseFirework<?, ?, ?>> fireworkOpt, Item baseFireworkItem) {
        return Suppliers.memoize(() -> {
            ArrayList<Text> tooltip = new ArrayList<>();
            fireworkOpt.ifPresent(firework ->{
                FireworkUsage fuseUsage = firework.getFuseUsage();
                if (fuseUsage != FireworkUsage.NONE) {
                    tooltip.add(Text.translatable("item.spring_explosion.firework.desc.fusing",
                                    Text.literal(String.format("%.1f", firework.getFusingTime() / 20f))
                                            .formatted(Formatting.YELLOW, Formatting.BOLD),
                                    Text.translatable("item.spring_explosion.firework.desc.firework_usage." + fuseUsage.name()
                                            .toLowerCase()).formatted(Formatting.GRAY, Formatting.BOLD))
                            .formatted(Formatting.YELLOW));
                } else {
                    tooltip.add(Text.translatable("item.spring_explosion.firework.desc.fusing_immediate")
                            .formatted(Formatting.LIGHT_PURPLE));
                }
                tooltip.add(Text.translatable("item.spring_explosion.firework.desc.firing",
                        Text.literal(String.format("%.1f", firework.getFiringTime() / 20f))
                                .formatted(Formatting.RED, Formatting.BOLD),
                        Text.translatable("item.spring_explosion.firework.desc.firework_usage." + firework.getFireUsage()
                                .name()
                                .toLowerCase()).formatted(Formatting.GRAY, Formatting.BOLD)).formatted(Formatting.RED));
                tooltip.add(Text.literal(""));
            });
            for (String s : I18n.translate("item.spring_explosion.%s.desc".formatted(Registries.ITEM.getId(baseFireworkItem)
                    .getPath())).split(",")) {
                tooltip.add(Text.literal(s).formatted(Formatting.GRAY));
            }
            return tooltip;
        });
    }

    @Override
    @Environment(EnvType.CLIENT)
    Supplier<IR> getRendererSupplier() {
        return firework.getRenderer();
    }

    public int getItemBarStep(ItemStack stack) {
        return Math.round(13.0F - (float)stack.getDamage() * 13.0F / (float)this.getMaxDamage(stack));
    }

    public F getFirework(){
        return this.firework;
    }

    public void createAndThrowFirework(World world, ItemStack stack, LivingEntity user, FireworkItemToEntityAction<BaseFireworkEntity<?,?>> action){
        BaseFireworkEntity<?, ?> fireworkEntity = this.firework.createEntity(world, stack, user);
        action.apply(fireworkEntity, user);
        world.spawnEntity(fireworkEntity);
    }

    @Override
    public ItemEntity useOnDrop(World world, ItemStack stack, LivingEntity user) {
        BaseFireworkEntity<?, ?> fusingEntity = this.getFirework().createEntity(world, stack, user);
        this.getFirework().getDropAction().apply(fusingEntity, user);
        world.spawnEntity(fusingEntity);
        return null;
    }

    @Override
    public boolean canUseOnDrop(ItemStack stack) {
        return SEItemComponents.FIREWORK_ITEM_BASE.get(stack).isUsed();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.addAll(lazyTooltip.get());
    }

    @Override
    //@Environment(EnvType.CLIENT, EnvType.SERVER)
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof LivingEntity livingEntity){
            FireworkItemBaseComponent fireworkItemBaseComponent = SEItemComponents.FIREWORK_ITEM_BASE.get(stack);
            if (fireworkItemBaseComponent.isFusing()){
                FireworkUsage fuseUsage = firework.getFuseUsage();
                if (fuseUsage.canUseOnHand() && selected) {
                    if (!world.isClient) {
                        ItemUtil.damageItem(stack, 1, livingEntity);
                    }
                    this.firework.onItemFusing(stack, livingEntity, slot);
                    if (stack.getMaxDamage() - stack.getDamage() == 1) {
                        stack.setDamage(0);
                        fireworkItemBaseComponent.setFusing(false);
                        fireworkItemBaseComponent.setFiring(true);
                    }
                }else if (fuseUsage == FireworkUsage.ENTITY){ //Thrown firework
                    createAndThrowFirework(world, stack, livingEntity, firework.getDropAction());
                    fireworkItemBaseComponent.setFusing(false);
                    decrement(stack, livingEntity);
                }else if (!selected){
                    if (livingEntity.getStackInHand(Hand.OFF_HAND) != stack) {
                        createAndThrowFirework(world, stack, livingEntity, firework.getOffhandAction());
                    }else {
                        createAndThrowFirework(world, stack, livingEntity, firework.getFusingToEntityFiringAction());
                    }
                    fireworkItemBaseComponent.setFusing(false);
                    stack.decrement(1);
                }else {
                    fireworkItemBaseComponent.setFusing(false);
                    fireworkItemBaseComponent.setFiring(true);
                }
            }else if (fireworkItemBaseComponent.isFiring()){
                FireworkUsage fireUsage = firework.getFireUsage();
                if (fireUsage.canUseOnHand() && selected) {
                    this.firework.onItemFiring(world, stack, livingEntity, slot, (float) (this.getMaxDamage() - stack.getDamage()) / this.getMaxDamage());
                    if (!world.isClient) {
                        ItemUtil.damageItem(stack, 1, livingEntity);
                    }
                }else {
                    if (fireUsage == FireworkUsage.ENTITY) {
                        createAndThrowFirework(world, stack, livingEntity, firework.getFusingToEntityFiringAction());
                    } else if (fireUsage == FireworkUsage.BOTH && !selected) {
                        createAndThrowFirework(world, stack, livingEntity, firework.getOffhandAction());
                    }
                    stack.decrement(1);
                    fireworkItemBaseComponent.setFiring(false);
                    fireworkItemBaseComponent.setFusing(false);
                    livingEntity.swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }

    private void decrement(ItemStack stack, LivingEntity livingEntity) {
        if (!(livingEntity instanceof PlayerEntity player && player.isCreative())){
            stack.decrement(1);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stackInHand = user.getStackInHand(hand);
        FireworkItemBaseComponent fireworkItemBaseComponent = SEItemComponents.FIREWORK_ITEM_BASE.get(stackInHand);
        if (!fireworkItemBaseComponent.isFusing() && !fireworkItemBaseComponent.isFiring()){
            fireworkItemBaseComponent.setFusing(true);
            this.firework.onStartUsing((I) this, stackInHand, world, user, hand);
            return TypedActionResult.success(stackInHand, false);
        }else if (canUseOnDrop(stackInHand)){
            this.useOnDrop(world,stackInHand,user);
            stackInHand.decrement(1);
            if (stackInHand.isEmpty()) return TypedActionResult.consume(ItemStack.EMPTY);
            return TypedActionResult.success(stackInHand);
        }
        return TypedActionResult.pass(stackInHand);
    }

}
