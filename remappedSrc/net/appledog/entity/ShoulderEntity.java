package net.appledog.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class ShoulderEntity extends AgeableMob {
    private static final int READY_TO_SIT_COOLDOWN = 100;
    private int ticks;

    protected ShoulderEntity(EntityType<? extends ApplepupEntity> entityType, Level world) {
        super(entityType, world);
    }

    public boolean mountOnto(ServerPlayer player) {
        CompoundTag nbtCompound = new CompoundTag();
        nbtCompound.putString("id", this.getEncodeId());
        this.saveWithoutId(nbtCompound);
        if (player.setEntityOnShoulder(nbtCompound)) {
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
