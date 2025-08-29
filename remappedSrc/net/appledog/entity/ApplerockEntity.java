package net.appledog.entity;

import net.appledog.registry.ADEntities;
import net.appledog.registry.ADItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ApplerockEntity extends ThrowableItemProjectile {

    public ApplerockEntity(EntityType<? extends ApplerockEntity> entityType, Level world) {
        super(entityType, world);
    }

    public ApplerockEntity(Level world, LivingEntity owner) {
        super(ADEntities.APPLEROCK, owner, world);
    }

    public ApplerockEntity(Level world, double x, double y, double z) {
        super(ADEntities.APPLEROCK, x, y, z, world);
    }

    protected Item getDefaultItem() {
        return ADItems.APPLEROCK;
    }


    @Override
    public void tick() {
        if (random.nextFloat() < 0.1f) {
            playSound(SoundEvents.WOLF_AMBIENT, 0.6f, 1.5f);
        }
        super.tick();
    }

    private ParticleOptions getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return new ItemParticleOption(ParticleTypes.ITEM, itemStack);
    }

    public void handleEntityEvent(byte status) {
        if (status == 3) {
            ParticleOptions particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F, 0.0F);
            }
        }

    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        float i = 1 + random.nextIntBetweenInclusive(0, 5);
        if ((entity.getType() == ADEntities.APPLEDOG || entity.getType() == ADEntities.APPLEPUP) && this.getOwner() != null && this.getOwner() instanceof Player player) {
            entity = player;
            if (player.getHealth() > 4) {
                i = player.getHealth() - 1;
            } else {
                i = 10;
            }
        }
        if (entity instanceof Player && entity != this.getOwner()) {
            i = 1;
        }
        entity.hurt(this.damageSources().thrown(this, this), i);
        playSound(SoundEvents.FOX_BITE, 1.2f, 1.5f);
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }
}