package net.appledog.datagen;

import net.appledog.registry.ADBlocks;
import net.appledog.registry.ADItems;
import net.appledog.registry.ADTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import java.util.concurrent.CompletableFuture;

public class ADRecipeProvider extends FabricRecipeProvider {
    public ADRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeExporter) {
        woodFromLogs(recipeExporter, ADBlocks.APPLOOD, ADBlocks.APPLOG);
        planksFromLogs(recipeExporter, Items.OAK_PLANKS, ADTags.APPLOGS, 4);
        slab(recipeExporter, RecipeCategory.BUILDING_BLOCKS, ADBlocks.APPLOG_SLAB, ADBlocks.APPLOG);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ADBlocks.APPLECOG, 4)
                .pattern(" # ")
                .pattern("#A#")
                .pattern(" # ")
                .define('#', Items.IRON_INGOT)
                .define('A', Items.APPLE)
                .unlockedBy(getHasName(ADItems.DOGAPPLE), has(ADItems.DOGAPPLE))
                .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ADItems.SALTED_DOGAPPLE)
                .pattern("#")
                .pattern("/")
                .define('#', ADItems.DOGAPPLE)
                .define('/', Items.WATER_BUCKET)
                .unlockedBy(getHasName(ADItems.DOGAPPLE), has(ADItems.DOGAPPLE))
                .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ADItems.CANDIED_DOGAPPLE)
                .pattern(" / ")
                .pattern("/#/")
                .pattern(" / ")
                .define('#', ADItems.DOGAPPLE)
                .define('/', Items.SUGAR)
                .unlockedBy(getHasName(ADItems.DOGAPPLE), has(ADItems.DOGAPPLE))
                .save(recipeExporter);
    }
}
