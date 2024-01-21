package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.fireworks.RocketAccelerator;

public class RocketAcceleratorEntity extends BaseFireworkEntity<RocketAcceleratorEntity, RocketAccelerator> {
    public RocketAcceleratorEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void pushAwayFrom(Entity other) {
        pushAndDamageTarget(other);
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (player.getWorld().isClient) return;
        Entity owner = this.getOwner();
        if (player == owner || (owner != null && owner.getScoreboardTeam() != null && player.getScoreboardTeam() == owner.getScoreboardTeam())) return;
        pushAwayFrom(player);
    }

    private void pushAndDamageTarget(Entity other) {
        Entity owner = this.getOwner();
        if (other == owner || (owner != null && owner.getScoreboardTeam() != null && other.getScoreboardTeam() == owner.getScoreboardTeam())) return;
        other.damage(this.getWorld().getDamageSources().explosion(this, owner), (float) (this.getVelocity().horizontalLength() * 10));
        other.setVelocity(other.getVelocity().add(other.getPos().subtract(this.getPos()).multiply(2)));
    }

    @Override
    protected RocketAccelerator getFirework() {
        return RocketAccelerator.INSTANCE;
    }
}
