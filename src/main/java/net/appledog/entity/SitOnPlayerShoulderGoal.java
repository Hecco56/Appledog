package net.appledog.entity;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Comparator;

public class SitOnPlayerShoulderGoal extends Goal {
    private final ShoulderEntity shoulderEntity;
    private ServerPlayerEntity owner;
    private boolean mounted;

    public SitOnPlayerShoulderGoal(ShoulderEntity shoulderEntity) {
        this.shoulderEntity = shoulderEntity;
    }

    public boolean canStart() {
        this.owner = this.shoulderEntity.getWorld()
                .getServer()
                .getPlayerManager()
                .getPlayerList()
                .stream()
                .filter(p -> p.isAlive() &&
                        !p.isSpectator() &&
                        !p.getAbilities().flying &&
                        !p.isTouchingWater() &&
                        !p.inPowderSnow)
                .min(Comparator.comparingDouble(p -> p.squaredDistanceTo(this.shoulderEntity)))
                .orElse(null);

        return this.owner != null && this.shoulderEntity.isReadyToSitOnPlayer();
    }

    @Override
    public boolean canStop() {
        return !this.mounted;
    }

    @Override
    public void start() {
        this.mounted = false;
    }

    @Override
    public void tick() {
        if (this.owner == null || this.mounted || this.shoulderEntity.isLeashed()) return;

        if (this.shoulderEntity.getBoundingBox().intersects(this.owner.getBoundingBox())) {
            this.mounted = this.shoulderEntity.mountOnto(this.owner);
        }
    }
}