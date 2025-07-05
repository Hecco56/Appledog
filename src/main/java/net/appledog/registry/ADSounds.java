package net.appledog.registry;

import net.appledog.Appledog;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ADSounds {

    public static final SoundEvent APPLEDOG_HURT = registerSoundEvent("appledog_hurt");
    public static final SoundEvent APPLEDOG_SAUCIFY = registerSoundEvent("appledog_saucify");

    public static SoundEvent registerSoundEvent(String name) {
        Identifier identifier = Identifier.of(Appledog.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void loadSounds() {

    }
}
