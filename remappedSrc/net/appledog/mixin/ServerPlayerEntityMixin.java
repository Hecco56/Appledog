
package net.appledog.mixin;

import net.appledog.registry.ADBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "setSpawnPoint", at = @At("HEAD"), cancellable = true)
    private void preventCustomBedSpawnPoint(ResourceKey<Level> dimension, @Nullable BlockPos pos, float angle, boolean forced, boolean sendMessage, CallbackInfo ci) {
        if (((ServerPlayer)(Object)this).level().getBlockState(pos).is(ADBlocks.COIR_BED)) {
            ci.cancel();
        }
    }
}