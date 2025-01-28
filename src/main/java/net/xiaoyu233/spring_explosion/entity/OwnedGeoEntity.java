package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Ownable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class OwnedGeoEntity extends BaseGeoEntity implements Ownable {
    @Nullable
    private Entity owner = null;
    @Nullable
    private UUID ownerUuid = null;

    public OwnedGeoEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("Owner")) {
            ownerUuid = nbt.getUuid("Owner");
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        if (this.owner != null) {
            nbt.putUuid("Owner", owner.getUuid());
        }else if (this.ownerUuid != null) {
            nbt.putUuid("Owner", ownerUuid);
        }
    }

    @Nullable
    public Entity getOwner() {
        if (this.owner != null && !this.owner.isRemoved()) {
            return this.owner;
        } else if (this.ownerUuid != null && this.getWorld() instanceof ServerWorld) {
            this.owner = ((ServerWorld)this.getWorld()).getEntity(this.ownerUuid);
            return this.owner;
        } else {
            return null;
        }
    }

    public void setOwner(@Nullable Entity owner) {
        this.owner = owner;
        if (owner != null) this.ownerUuid = owner.getUuid();
    }

    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        Entity entity = this.getOwner();
        return new EntitySpawnS2CPacket(this, entity == null ? 0 : entity.getId());
    }

    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        Entity entity = this.getWorld().getEntityById(packet.getEntityData());
        if (entity != null) {
            this.setOwner(entity);
        }

    }
}
