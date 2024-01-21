package net.xiaoyu233.spring_explosion.fireworks;

import net.minecraft.entity.LivingEntity;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.util.EntityUtil;

import java.util.function.BiConsumer;

public enum FireworkItemToEntityAction {
    THROW((baseFireworkEntity, livingEntity) -> EntityUtil.throwEntity(livingEntity, baseFireworkEntity, true,true)),
    THROW_NO_COPY_ROTATION((baseFireworkEntity, livingEntity) -> EntityUtil.throwEntity(livingEntity, baseFireworkEntity, false, false)),
    THROW_COPY_YAW((baseFireworkEntity, livingEntity) -> EntityUtil.throwEntity(livingEntity, baseFireworkEntity, true,false)),
    DROP((baseFireworkEntity, livingEntity) -> baseFireworkEntity.updatePositionAndAngles(livingEntity.getX(),livingEntity.getY(), livingEntity.getZ(), livingEntity.getYaw(), livingEntity.getPitch())),
    DROP_COPY_YAW((baseFireworkEntity, livingEntity) -> baseFireworkEntity.updatePositionAndAngles(livingEntity.getX(),livingEntity.getY(), livingEntity.getZ(), livingEntity.getYaw(), baseFireworkEntity.getPitch())),
    DROP_NO_COPY_ROTATION((baseFireworkEntity, livingEntity) -> baseFireworkEntity.updatePositionAndAngles(livingEntity.getX(),livingEntity.getY(), livingEntity.getZ(), baseFireworkEntity.getYaw(), baseFireworkEntity.getPitch()));
    private final BiConsumer<BaseFireworkEntity<?, ?>, LivingEntity> posingAction;

    FireworkItemToEntityAction(BiConsumer<BaseFireworkEntity<?, ?>, LivingEntity> posingAction) {
        this.posingAction = posingAction;
    }

    public void apply(BaseFireworkEntity<?, ?> firework, LivingEntity entity) {
        this.posingAction.accept(firework, entity);
    }
}
