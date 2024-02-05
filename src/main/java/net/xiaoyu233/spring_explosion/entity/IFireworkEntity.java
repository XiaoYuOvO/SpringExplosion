package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public interface IFireworkEntity {
    byte EXPLODE_STATUS = 64;
    default void disposeOnWater() {
        this.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH,1,1);
        this.playSound(SoundEvents.ENTITY_ITEM_BREAK,1,1);
        this.discard();
    }

    void discard();

    void playSound(SoundEvent blockFireExtinguish, float i, float i1);
}
