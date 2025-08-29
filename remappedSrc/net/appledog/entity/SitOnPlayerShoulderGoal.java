package net.appledog.entity;

import java.util.Comparator;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.goal.Goal;

public class SitOnPlayerShoulderGoal extends Goal {
    private final ShoulderEntity shoulderEntity;
    private ServerPlayer owner;
    private boolean mounted;

    public SitOnPlayerShoulderGoal(ShoulderEntity shoulderEntity) {
        this.shoulderEntity = shoulderEntity;
    }

    public boolean canUse() {
        this.owner = this.shoulderEntity.level()
                .getServer()
                .getPlayerList()
                .getPlayers()
                .stream()
                .filter(p -> p.isAlive() &&
                        !p.isSpectator() &&
                        !p.getAbilities().flying &&
                        !p.isInWater() &&
                        !p.isInPowderSnow)
                .min(Comparator.comparingDouble(p -> p.distanceToSqr(this.shoulderEntity)))
                .orElse(null);

        return this.owner != null && this.shoulderEntity.isReadyToSitOnPlayer();
    }

    @Override
    public boolean isInterruptable() {
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