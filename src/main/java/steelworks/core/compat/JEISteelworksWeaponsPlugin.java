package steelworks.core.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import steelworks.SteelworksWeapons;
import steelworks.core.recipe.MoonAltarRecipe;
import steelworks.core.registry.ModBlocks;

import java.util.List;

@JeiPlugin
public class JEISteelworksWeaponsPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(SteelworksWeapons.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MoonAltarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<MoonAltarRecipe> moonAltarRecipes = recipeManager.getAllRecipesFor(MoonAltarRecipe.Type.INSTANCE);

        registration.addRecipes(MoonAltarRecipeCategory.MOON_ALTAR_RECIPE_TYPE, moonAltarRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        IModPlugin.super.registerGuiHandlers(registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.MOON_ALTAR.get()), MoonAltarRecipeCategory.MOON_ALTAR_RECIPE_TYPE);
    }
}
