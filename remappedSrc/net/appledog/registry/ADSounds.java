package net.appledog.registry;

import net.appledog.Appledog;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ADSounds {

    public static final SoundEvent APPLEDOG_HURT = registerSoundEvent("appledog_hurt");
    public static final SoundEvent APPLEDOG_SAUCIFY = registerSoundEvent("appledog_saucify");

    public static SoundEvent registerSoundEvent(String name) {
        ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier));
    }

    public static void loadSounds() {

    }
}
