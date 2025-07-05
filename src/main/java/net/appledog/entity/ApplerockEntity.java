package net.appledog.entity;

import net.appledog.registry.ADEntities;
import net.appledog.registry.ADItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ApplerockEntity extends ThrownItemEntity {

    public ApplerockEntity(EntityType<? extends ApplerockEntity> entityType, World world) {
        super(entityType, world);
    }

    public ApplerockEntity(World world, LivingEntity owner) {
        super(ADEntities.APPLEROCK, owner, world);
    }

    public ApplerockEntity(World world, double x, double y, double z) {
        super(ADEntities.APPLEROCK, x, y, z, world);
    }

    protected Item getDefaultItem() {
        return ADItems.APPLEROCK;
    }


    @Override
    public void tick() {
        if (random.nextFloat() < 0.1f) {
            playSound(SoundEvents.ENTITY_WOLF_AMBIENT, 0.6f, 1.5f);
        }
        super.tick();
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getStack();
        return new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }

    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F, 0.0F);
            }
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        float i = 1 + random.nextBetween(0, 5);
        if ((entity.getType() == ADEntities.APPLEDOG || entity.getType() == ADEntities.APPLEPUP) && this.getOwner() != null && this.getOwner() instanceof PlayerEntity player) {
            entity = player;
            if (player.getHealth() > 4) {
                i = player.getHealth() - 1;
            } else {
                i = 10;
            }
        }
        if (entity instanceof PlayerEntity && entity != this.getOwner()) {
            i = 1;
        }
        entity.damage(this.getDamageSources().thrown(this, this), i);
        playSound(SoundEvents.ENTITY_FOX_BITE, 1.2f, 1.5f);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, (byte)3);
            this.discard();
        }

    }
}