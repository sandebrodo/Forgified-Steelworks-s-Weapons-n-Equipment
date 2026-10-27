package steelworks.core.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.checkerframework.checker.guieffect.qual.UI;
import steelworks.SteelworksWeapons;
import steelworks.core.recipe.MoonAltarRecipe;
import steelworks.core.registry.ModBlocks;

import java.util.List;

public class MoonAltarRecipeCategory implements IRecipeCategory<MoonAltarRecipe> {

    public static final ResourceLocation UID = SteelworksWeapons.resourceLocation("moon_altar");
    public static final ResourceLocation TEXTURE = SteelworksWeapons.resourceLocation("textures/gui/moon_altar_jei.png");

    public static final RecipeType<MoonAltarRecipe> MOON_ALTAR_RECIPE_TYPE =  new RecipeType<>(UID, MoonAltarRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public MoonAltarRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(TEXTURE, 0, 0, 128, 144).setTextureSize(128, 144).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MOON_ALTAR.get()));
    }

    @Override
    public RecipeType<MoonAltarRecipe> getRecipeType() {
        return MOON_ALTAR_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.steelworks_weapons.moon_altar_recipe");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    private static final int[][] INPUT_POSITIONS = {
            {40, 16},
            {72, 16},
            {16, 40},
            {96, 40},
            {16, 88},
            {96, 88},
            {40, 112},
            {72, 112}
    };

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MoonAltarRecipe recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> ingredients = recipe.ingredients();
        int count = Math.min(ingredients.size(), INPUT_POSITIONS.length);
        for (int i = 0; i < count; i++) {
            builder.addSlot(RecipeIngredientRole.INPUT, INPUT_POSITIONS[i][0], INPUT_POSITIONS[i][1])
                    .addIngredients(ingredients.get(i));
        }
        builder.addSlot(RecipeIngredientRole.INPUT, 56, 56).addIngredients(recipe.catalystItem());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 56, 85).addItemStack(recipe.result());
    }


}
