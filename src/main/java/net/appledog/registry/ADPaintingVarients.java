package net.appledog.registry;

import net.appledog.Appledog;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ADPaintingVarients {
    public static final PaintingVariant CANINEDY = Registry.register(Registries.PAINTING_VARIANT, Identifier.of(Appledog.MOD_ID, "caninedy"), new PaintingVariant(64, 64));
    public static final PaintingVariant APPLEDUKE = Registry.register(Registries.PAINTING_VARIANT, Identifier.of(Appledog.MOD_ID, "appleduke"), new PaintingVariant(16, 16));

    public static void loadPaintingVarients() {
    }
}
