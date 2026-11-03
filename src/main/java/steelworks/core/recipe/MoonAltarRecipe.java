package steelworks.core.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import steelworks.core.container.MoonAltarContainer;
import steelworks.core.registry.ModRecipes;

import java.util.ArrayList;
import java.util.List;

public record MoonAltarRecipe(Ingredient catalystItem, List<Ingredient> ingredients, ItemStack result) implements Recipe<MoonAltarRecipeInput> {

    @Override
    public boolean matches(MoonAltarRecipeInput input, Level level) {

        if (!catalystItem.test(input.catalyst())) return false;
        if (ingredients.size() > 8) return false;
        if (input.isEmpty()) return false;

        List<ItemStack> pool = new ArrayList<>();
        for (ItemStack stack : input.items()) {
            if (!stack.isEmpty()) {
                pool.add(stack.copy());
            }
        }

        if (pool.size() != ingredients.size()) return false;

        for (Ingredient ingredient : ingredients) {
            boolean found = false;
            for (ItemStack stack : pool) {
                if (!stack.isEmpty() && ingredient.test(stack)) {
                    stack.shrink(1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(MoonAltarRecipeInput moonAltarRecipeInput, HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.MOON_ALTAR_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.MOON_ALTAR_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<MoonAltarRecipe> {

        public static final MapCodec<MoonAltarRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("catalyst").forGetter(MoonAltarRecipe::catalystItem),
                Ingredient.CODEC.listOf().optionalFieldOf("ingredients", List.of()).forGetter(MoonAltarRecipe::ingredients),
                ItemStack.CODEC.fieldOf("result").forGetter(MoonAltarRecipe::result)
        ).apply(instance, MoonAltarRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, MoonAltarRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, MoonAltarRecipe::catalystItem,
                Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), MoonAltarRecipe::ingredients,
                ItemStack.STREAM_CODEC, MoonAltarRecipe::result,
                MoonAltarRecipe::new
        );


        @Override
        public MapCodec<MoonAltarRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MoonAltarRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
