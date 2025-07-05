package net.appledog.mixin;

import net.appledog.registry.ADBlocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "setSpawnPoint", at = @At("HEAD"), cancellable = true)
    private void preventCustomBedSpawnPoint(RegistryKey<World> dimension, @Nullable BlockPos pos, float angle, boolean forced, boolean sendMessage, CallbackInfo ci) {
        if (((ServerPlayerEntity)(Object)this).getWorld().getBlockState(pos).isOf(ADBlocks.COIR_BED)) {
            ci.cancel();
        }
    }
}