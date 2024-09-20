package net.appledog.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

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

    public ADLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        generate(translationBuilder, "entity.appledog.appledog", "Appledog");
        for (Identifier id : allItemIdsInNamespace(MOD_ID)) {
            String key = Registries.ITEM.get(id).getTranslationKey();
            if (usedTranslationKeys.contains(key)) {
                continue;
            }

            usedTranslationKeys.add(key);
            translationBuilder.add(key, toSentanceCase(id.getPath()));
        }
    }

    public static Set<Identifier> allItemIdsInNamespace(String namespace) {
        Set<Identifier> set = Registries.ITEM.getIds();
        Set<Identifier> a = new HashSet<>();
        for (Identifier id : set) {
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

