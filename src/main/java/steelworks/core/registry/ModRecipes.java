package steelworks.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import steelworks.SteelworksWeapons;
import steelworks.core.recipe.MoonAltarRecipe;

import java.util.function.Supplier;

public class ModRecipes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, SteelworksWeapons.MODID);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, SteelworksWeapons.MODID);


    public static final Supplier<RecipeType<MoonAltarRecipe>> MOON_ALTAR_RECIPE_TYPE =
            RECIPE_TYPES.register("moon_altar", () -> RecipeType.simple(SteelworksWeapons.resourceLocation("moon_altar")));

    public static final Supplier<RecipeSerializer<MoonAltarRecipe>> MOON_ALTAR_RECIPE_SERIALIZER =
            RECIPE_SERIALIZERS.register("moon_altar", MoonAltarRecipe.Serializer::new);

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
