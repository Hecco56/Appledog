package net.appledog.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static net.appledog.Appledog.MOD_ID;

public class ADLangProvider extends FabricLanguageProvider {
    Set<String> usedTranslationKeys = new HashSet<>();

    public ADLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
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
        generate(translationBuilder, "itemgroup.appledog", "appledog!");
        generate(translationBuilder, "entity.appledog.appledog", "Appledog");
        generate(translationBuilder, "entity.appledog.applepup", "Applepup");
        generate(translationBuilder, "entity.appledog.applepup", "Applepup");
        generate(translationBuilder, "painting.appledog.caninedy.title", "Caninedy");
        generate(translationBuilder, "painting.appledog.appleduke.title", "Appleduke");
        generate(translationBuilder, "painting.appledog.appleduke.author", "§kappledog");
        generate(translationBuilder, "painting.appledog.caninedy.author", "§kappledog");
        generate(translationBuilder, "death.attack.salted_dogapple_bite", "%1$s's hand was bit off by Salted Dogapple");
        generate(translationBuilder, "subtitles.appledog.appledog.saucify", "Appledog saucifies");
        generate(translationBuilder, "subtitles.appledog.appledog.hurt", "Appledog gets bitten");
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

