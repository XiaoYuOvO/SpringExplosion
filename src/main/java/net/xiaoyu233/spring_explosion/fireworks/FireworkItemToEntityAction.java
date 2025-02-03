package net.xiaoyu233.spring_explosion.fireworks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.util.EntityUtil;

import java.util.function.BiConsumer;

public class FireworkItemToEntityAction<E extends Entity> {
    public static <E extends Entity> FireworkItemToEntityAction<E> drop(){ return new FireworkItemToEntityAction<E>((baseFireworkEntity, livingEntity) -> EntityUtil.throwEntity(livingEntity, baseFireworkEntity, true,true));}
    public static <E extends BaseFireworkEntity<?,?>> FireworkItemToEntityAction<E> throwFirework(){return new FireworkItemToEntityAction<E>((baseFireworkEntity, user) -> {
        baseFireworkEntity.refreshPositionAndAngles(user.getX(),user.getEyeY(), user.getZ(), user.getYaw(), user.getPitch());
        baseFireworkEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
    });}

    public static <E extends BaseFireworkEntity<?,?>> FireworkItemToEntityAction<E> throwFirework(float speedMul){return new FireworkItemToEntityAction<E>((baseFireworkEntity, user) -> {
        baseFireworkEntity.refreshPositionAndAngles(user.getX(),user.getEyeY(), user.getZ(), user.getYaw(), user.getPitch());
        baseFireworkEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F * speedMul, 1.0F);
    });}
    public static <E extends Entity> FireworkItemToEntityAction<E> dropNoCopyRotation(){return new FireworkItemToEntityAction<E>((baseFireworkEntity, livingEntity) -> EntityUtil.throwEntity(livingEntity, baseFireworkEntity, false, false));}
    public static <E extends Entity> FireworkItemToEntityAction<E> dropCopyYaw(){return new FireworkItemToEntityAction<E>((baseFireworkEntity, livingEntity) -> EntityUtil.throwEntity(livingEntity, baseFireworkEntity, true,false));}
    public static <E extends Entity> FireworkItemToEntityAction<E> offhand(){return new FireworkItemToEntityAction<E>((baseFireworkEntity, livingEntity) -> baseFireworkEntity.refreshPositionAndAngles(livingEntity.getX(),livingEntity.getY(), livingEntity.getZ(), livingEntity.getYaw(), livingEntity.getPitch()));}
    public static <E extends Entity> FireworkItemToEntityAction<E> offhandCopyYaw(){return new FireworkItemToEntityAction<E>((baseFireworkEntity, livingEntity) -> baseFireworkEntity.refreshPositionAndAngles(livingEntity.getX(),livingEntity.getY(), livingEntity.getZ(), livingEntity.getYaw(), baseFireworkEntity.getPitch()));}
    public static <E extends Entity> FireworkItemToEntityAction<E> offhandHeadCopyYaw(){return new FireworkItemToEntityAction<E>((baseFireworkEntity, livingEntity) -> baseFireworkEntity.refreshPositionAndAngles(livingEntity.getX(),livingEntity.getEyeY(), livingEntity.getZ(), livingEntity.getYaw(), baseFireworkEntity.getPitch()));}
    public static <E extends Entity> FireworkItemToEntityAction<E> offhandHead(){return new FireworkItemToEntityAction<E>((baseFireworkEntity, livingEntity) -> baseFireworkEntity.refreshPositionAndAngles(livingEntity.getX(),livingEntity.getEyeY(), livingEntity.getZ(), livingEntity.getYaw(), livingEntity.getPitch()));}
    public static <E extends Entity> FireworkItemToEntityAction<E> offhandNoCopyRotation(){return new FireworkItemToEntityAction<E>((baseFireworkEntity, livingEntity) -> baseFireworkEntity.refreshPositionAndAngles(livingEntity.getX(),livingEntity.getY(), livingEntity.getZ(), baseFireworkEntity.getYaw(), baseFireworkEntity.getPitch()));}
    private final BiConsumer<E, LivingEntity> posingAction;

    FireworkItemToEntityAction(BiConsumer<E, LivingEntity> posingAction) {
        this.posingAction = posingAction;
    }

    public void apply(E firework, LivingEntity entity) {
        this.posingAction.accept(firework, entity);
    }
}
