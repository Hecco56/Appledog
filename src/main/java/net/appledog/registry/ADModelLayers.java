package net.appledog.registry;

import net.appledog.Appledog;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ADModelLayers {
    public static final EntityModelLayer APPLEDOG = new EntityModelLayer(Identifier.of(Appledog.MOD_ID, "appledog"), "main");
    public static final EntityModelLayer APPLEPUP = new EntityModelLayer(Identifier.of(Appledog.MOD_ID, "applepup"), "main");

}
