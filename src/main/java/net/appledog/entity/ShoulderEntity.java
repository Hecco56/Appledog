package net.appledog.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public abstract class ShoulderEntity extends PassiveEntity {
    private static final int READY_TO_SIT_COOLDOWN = 100;
    private int ticks;

    protected ShoulderEntity(EntityType<? extends ApplepupEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean mountOnto(ServerPlayerEntity player) {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putString("id", this.getSavedEntityId());
        this.writeNbt(nbtCompound);
        if (player.addShoulderEntity(nbtCompound)) {
            this.discard();
            return true;
        } else {
            return false;
        }
    }

    public void tick() {
        ++this.ticks;
        super.tick();
    }

    public boolean isReadyToSitOnPlayer() {
        return this.ticks > 100;
    }
}
