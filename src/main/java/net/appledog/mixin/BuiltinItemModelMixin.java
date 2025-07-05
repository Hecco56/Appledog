package net.appledog.mixin;

import net.appledog.custom.CoirBedBlockEntity;
import net.appledog.registry.ADBlocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BuiltinModelItemRenderer.class)
public abstract class BuiltinItemModelMixin {

    @Shadow @Final
    private BlockEntityRenderDispatcher blockEntityRenderDispatcher;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"), cancellable = true)
    private void appledog$renderCoirBed(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        Item item = stack.getItem();
        if (item instanceof BlockItem blockItem && blockItem.getBlock() == ADBlocks.COIR_BED) {
            this.blockEntityRenderDispatcher.renderEntity(new CoirBedBlockEntity(BlockPos.ORIGIN, ADBlocks.COIR_BED.getDefaultState()), matrices, vertexConsumers, light, overlay);
            ci.cancel();
        }
    }
}