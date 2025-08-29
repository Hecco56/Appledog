package net.appledog.registry;

import net.appledog.Appledog;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ADModelLayers {
    public static final ModelLayerLocation APPLEDOG = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "appledog"), "main");
    public static final ModelLayerLocation APPLEPUP = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "applepup"), "main");

}
