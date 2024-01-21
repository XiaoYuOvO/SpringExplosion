package net.xiaoyu233.spring_explosion.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.item.BaseFireworkItem;
import net.xiaoyu233.spring_explosion.item.IFireworkItem;
import net.xiaoyu233.spring_explosion.util.EntityUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract @Nullable ItemEntity dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership);

    @Redirect(method = "dropItem(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/ItemEntity;", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;"))
    private ItemEntity injectRedirectDropItem(PlayerEntity instance, ItemStack stack, boolean throwRandomly, boolean retainOwnership){
        if (retainOwnership && stack.getItem() instanceof IFireworkItem<?> baseFireworkItem && SEItemComponents.FIREWORK_ITEM_BASE.get(stack).isUsed()){
            return dropFireworkEntity(baseFireworkItem, stack);
        }else{
            return this.dropItem(stack, false, retainOwnership);
        }
    }

    @Unique
    @Nullable
    private ItemEntity dropFireworkEntity(IFireworkItem<?> item, ItemStack stack) {
        BaseFireworkEntity<?, ?> fusingEntity = item.getFirework().createEntity(this.getWorld(), stack, this);
        this.getWorld().spawnEntity(fusingEntity);
        item.getFirework().getDropAction().apply(fusingEntity, this);
        return null;
    }

    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("HEAD"), cancellable = true)
    private void injectDropSparkSword(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir){
        if (retainOwnership && stack.getItem() instanceof IFireworkItem<?> baseFireworkItem && SEItemComponents.FIREWORK_ITEM_BASE.get(stack).isUsed()){
            cir.setReturnValue(dropFireworkEntity(baseFireworkItem, stack));
            cir.cancel();
        }
    }
}
