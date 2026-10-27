package steelworks.core.registry;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import steelworks.SteelworksWeapons;
import steelworks.core.recipe.MoonAltarRecipe;

public class ModRecipes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, SteelworksWeapons.MODID);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SteelworksWeapons.MODID);

    public static final RegistryObject<RecipeType<MoonAltarRecipe>> MOON_ALTAR_RECIPE_TYPE =
            RECIPE_TYPES.register("moon_altar", () -> MoonAltarRecipe.Type.INSTANCE);

    public static final RegistryObject<RecipeSerializer<MoonAltarRecipe>> MOON_ALTAR_RECIPE_SERIALIZER =
            RECIPE_SERIALIZERS.register("moon_altar", () -> MoonAltarRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
