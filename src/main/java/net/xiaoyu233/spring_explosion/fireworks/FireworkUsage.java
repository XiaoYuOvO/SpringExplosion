package net.xiaoyu233.spring_explosion.fireworks;

public enum FireworkUsage {
    NONE,
    HAND,
    ENTITY,
    BOTH;
    public boolean canUseOnEntity() {
        return this == BOTH || this == ENTITY;
    }

    public boolean canUseOnHand() {
        return this == BOTH || this == HAND;
    }
}
