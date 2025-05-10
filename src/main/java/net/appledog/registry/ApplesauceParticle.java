package net.appledog.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ApplesauceParticle extends SpriteBillboardParticle {

    protected ApplesauceParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, SpriteProvider spriteProvider) {
        super(clientWorld, d, e, f, g, h, i);
        this.setSprite(spriteProvider.getSprite(this.random.nextInt(4), 4));
        this.scale = 0.1F * (this.random.nextFloat() + 0.5F) * 1.5F;
        this.maxAge = 3000 + clientWorld.random.nextBetween(0, 100);
        this.age = world.random.nextBoolean() ? 0 : 1;
        this.velocityX = g;
        this.velocityY = h;
        this.velocityZ = i;
        this.setBoundingBoxSpacing(0.5F, 0.5F);
        this.gravityStrength = 0.06F;
        this.collidesWithWorld = true;
        this.setColor(0.9f, 0.9f, 0.9f);
    }


    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        }
        if (this.age > 2600 && this.alpha > 0.01) {
            this.alpha -= 0.002f;
            this.scale -= 0.0002f;
        }
        boolean onGround = false;
        BlockPos blockPos = BlockPos.ofFloored(this.x, this.y, this.z);
        double d = this.world.getBlockState(blockPos).getCollisionShape(this.world, blockPos).getEndingCoord(Direction.Axis.Y, this.x - (double) blockPos.getX(), this.z - (double) blockPos.getZ()) + 0.5f;
        if (d > 0 && this.y < (double) blockPos.getY() + d) {
            onGround = true;
            if (this.velocityY > 0.025f) {
                this.velocityY = Math.abs(this.velocityY) * 0.5f < 0.01f ? 0 : Math.abs(this.velocityY) * 0.5f;
                this.velocityX = Math.abs(this.velocityX) * 0.4f < 0.01f ? 0 : this.velocityX * 0.4f;
                this.velocityZ = Math.abs(this.velocityZ) * 0.4f < 0.01f ? 0 : this.velocityZ * 0.4f;
            } else {
                this.velocityX = 0;
                this.velocityY = 0;
                this.velocityZ = 0;
            }
        }
        if (this.onGround) {
            this.velocityZ = 0;
            this.velocityY = 0;
            this.velocityX = 0;
        }
        if (!onGround) {
            this.velocityY -= 0.03f;
        }
        this.move(this.velocityX, this.velocityY, this.velocityZ);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    protected int getBrightness(float tint) {
        return super.getBrightness(tint);
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider SPRITES;
        public Factory(SpriteProvider spriteProvider) {
            this.SPRITES = spriteProvider;
        }
        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new ApplesauceParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.SPRITES);
        }
    }
}