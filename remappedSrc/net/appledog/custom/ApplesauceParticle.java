package net.appledog.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ApplesauceParticle extends TextureSheetParticle {

    protected ApplesauceParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, SpriteSet spriteProvider) {
        super(clientWorld, d, e, f, g, h, i);
        this.setSprite(spriteProvider.get(this.random.nextInt(4), 4));
        this.quadSize = 0.1F * (this.random.nextFloat() + 0.5F) * 1.5F;
        this.lifetime = 3000 + clientWorld.random.nextIntBetweenInclusive(0, 100);
        this.age = level.random.nextBoolean() ? 0 : 1;
        this.xd = g;
        this.yd = h;
        this.zd = i;
        this.setSize(0.5F, 0.5F);
        this.gravity = 0.06F;
        this.hasPhysics = true;
        this.setColor(0.9f, 0.9f, 0.9f);
    }


    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
        if (this.age > 2600 && this.alpha > 0.01) {
            this.alpha -= 0.002f;
            this.quadSize -= 0.0002f;
        }
        boolean onGround = false;
        BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
        double d = this.level.getBlockState(blockPos).getCollisionShape(this.level, blockPos).max(Direction.Axis.Y, this.x - (double) blockPos.getX(), this.z - (double) blockPos.getZ()) + 0.5f;
        if (d > 0 && this.y < (double) blockPos.getY() + d) {
            onGround = true;
            if (this.yd > 0.025f) {
                this.yd = Math.abs(this.yd) * 0.5f < 0.01f ? 0 : Math.abs(this.yd) * 0.5f;
                this.xd = Math.abs(this.xd) * 0.4f < 0.01f ? 0 : this.xd * 0.4f;
                this.zd = Math.abs(this.zd) * 0.4f < 0.01f ? 0 : this.zd * 0.4f;
            } else {
                this.xd = 0;
                this.yd = 0;
                this.zd = 0;
            }
        }
        if (this.onGround) {
            this.zd = 0;
            this.yd = 0;
            this.xd = 0;
        }
        if (!onGround) {
            this.yd -= 0.03f;
        }
        this.move(this.xd, this.yd, this.zd);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    protected int getLightColor(float tint) {
        return super.getLightColor(tint);
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet SPRITES;
        public Factory(SpriteSet spriteProvider) {
            this.SPRITES = spriteProvider;
        }
        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new ApplesauceParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.SPRITES);
        }
    }
}