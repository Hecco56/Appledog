package net.appledog.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static net.appledog.Appledog.MOD_ID;

public class ADLangProvider extends FabricLanguageProvider {
    Set<String> usedTranslationKeys = new HashSet<>();
    private void generate(TranslationBuilder translationBuilder, String key, String translation) {
        if(usedTranslationKeys.contains(key)) {
            return;
        }
        translationBuilder.add(key, translation);
        usedTranslationKeys.add(key);
    }

    public ADLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        generate(translationBuilder, "itemgroup.appledog", "appledog!");
        generate(translationBuilder, "entity.appledog.appledog", "Appledog");
        generate(translationBuilder, "entity.appledog.applepup", "Applepup");
        generate(translationBuilder, "entity.appledog.applepup", "Applepup");
        generate(translationBuilder, "entity.appledog.applerock", "Applerock");
        generate(translationBuilder, "painting.appledog.caninedy.title", "Caninedy");
        generate(translationBuilder, "painting.appledog.caninedy.author", "§kappledog");
        generate(translationBuilder, "painting.appledog.appleduke.title", "Appleduke");
        generate(translationBuilder, "painting.appledog.appleduke.author", "§kappledog");
        generate(translationBuilder, "death.attack.salted_dogapple_bite", "%1$s's hand was bit off by Salted Dogapple");
        generate(translationBuilder, "subtitles.appledog.appledog.saucify", "Appledog saucifies");
        generate(translationBuilder, "subtitles.appledog.appledog.hurt", "Appledog gets bitten");
        for (ResourceLocation id : allItemIdsInNamespace(MOD_ID)) {
            String key = BuiltInRegistries.ITEM.get(id).getDescriptionId();
            if (usedTranslationKeys.contains(key)) {
                continue;
            }

            usedTranslationKeys.add(key);
            translationBuilder.add(key, toSentanceCase(id.getPath()));
        }
    }

    public static Set<ResourceLocation> allItemIdsInNamespace(String namespace) {
        Set<ResourceLocation> set = BuiltInRegistries.ITEM.keySet();
        Set<ResourceLocation> a = new HashSet<>();
        for (ResourceLocation id : set) {
            if(Objects.equals(id.getNamespace(), namespace)) {
                a.add(id);
            }
        }
        return a;
    }

    public static String toSentanceCase(String s) {
        String words[] = s.split("[\\s|_]");
        StringBuilder capitalizeWord = new StringBuilder();
        for (String w : words){
            String first = w.substring(0,1);
            String afterfirst = w.substring(1);
            capitalizeWord
                    .append(first.toUpperCase())
                    .append(afterfirst)
                    .append(" ");
        }
        return capitalizeWord.toString().trim();
    }
}

