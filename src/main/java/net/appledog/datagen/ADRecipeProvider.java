package net.appledog.datagen;

import net.appledog.registry.ADBlocks;
import net.appledog.registry.ADItems;
import net.appledog.registry.ADTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class ADRecipeProvider extends FabricRecipeProvider {

    public ADRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> recipeExporter) {
        offerBarkBlockRecipe(recipeExporter, ADBlocks.APPLOOD, ADBlocks.APPLOG);
        offerPlanksRecipe(recipeExporter, Items.OAK_PLANKS, ADTags.APPLOGS, 4);
        offerSlabRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, ADBlocks.APPLOG_SLAB, ADBlocks.APPLOG);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ADBlocks.APPLECOG, 4)
                .pattern(" # ")
                .pattern("#A#")
                .pattern(" # ")
                .input('#', Items.IRON_INGOT)
                .input('A', Items.APPLE)
                .criterion(hasItem(ADItems.DOGAPPLE), conditionsFromItem(ADItems.DOGAPPLE))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ADItems.SALTED_DOGAPPLE)
                .pattern("#")
                .pattern("/")
                .input('#', ADItems.DOGAPPLE)
                .input('/', Items.WATER_BUCKET)
                .criterion(hasItem(ADItems.DOGAPPLE), conditionsFromItem(ADItems.DOGAPPLE))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ADItems.CANDIED_DOGAPPLE)
                .pattern(" / ")
                .pattern("/#/")
                .pattern(" / ")
                .input('#', ADItems.DOGAPPLE)
                .input('/', Items.SUGAR)
                .criterion(hasItem(ADItems.DOGAPPLE), conditionsFromItem(ADItems.DOGAPPLE))
                .offerTo(recipeExporter);
    }
}
