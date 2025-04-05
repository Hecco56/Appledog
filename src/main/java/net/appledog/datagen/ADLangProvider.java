package net.appledog.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static net.appledog.Appledog.MOD_ID;

public class ADLangProvider extends FabricLanguageProvider {
    Set<String> usedTranslationKeys = new HashSet<>();

    public ADLangProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, "en_us");
    }

    private void generate(TranslationBuilder translationBuilder, String key, String translation) {
        if(usedTranslationKeys.contains(key)) {
            return;
        }
        translationBuilder.add(key, translation);
        usedTranslationKeys.add(key);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        generate(translationBuilder, "itemgroup.appledog.appledog", "the appledog mod");
        generate(translationBuilder, "entity.appledog.appledog", "Appledog");
        generate(translationBuilder, "entity.appledog.applepup", "Applepup");
        for (Identifier id : allItemIdsInNamespace(MOD_ID)) {
            String key = Registry.ITEM.get(id).getTranslationKey();
            if (usedTranslationKeys.contains(key)) {
                continue;
            }

            usedTranslationKeys.add(key);
            translationBuilder.add(key, toSentanceCase(id.getPath()));
        }
    }

    public static Set<Identifier> allItemIdsInNamespace(String namespace) {
        Set<Identifier> set = Registry.ITEM.getIds();
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

