package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.explosion.Explosion;

public interface IFireworkEntity {
    byte EXPLODE_STATUS = 64;
    default void disposeOnWater() {
        this.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH,1,1);
        this.playSound(SoundEvents.ENTITY_ITEM_BREAK,1,1);
        this.discardFirework();
    }

    void discardFirework();

    void playSound(SoundEvent blockFireExtinguish, float i, float i1);

    default float modifyExplosionDamage(Explosion explosion, Entity target, float originalDamage, double exposure){
        return originalDamage;
    };
}
