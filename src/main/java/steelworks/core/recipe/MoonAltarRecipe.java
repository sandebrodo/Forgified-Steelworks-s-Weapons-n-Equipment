package steelworks.core.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.Nullable;
import steelworks.SteelworksWeapons;
import steelworks.core.block.tile.MoonAltarTileEntity;
import steelworks.core.container.MoonAltarContainer;

import java.util.ArrayList;
import java.util.List;

public record MoonAltarRecipe(ResourceLocation id, Ingredient catalystItem, NonNullList<Ingredient> ingredients, ItemStack result) implements Recipe<MoonAltarContainer> {


    @Override
    public boolean matches(MoonAltarContainer container, Level level) {

        if (level.isClientSide) {
            return false;
        }

        if (!catalystItem.test(container.getCatalystSource() instanceof MoonAltarTileEntity altar
                 ? altar.inventory.getStackInSlot(0) : ItemStack.EMPTY)) {
            return false;
        }

        if (ingredients.size() > 8) {
            return false;
        }

        List<ItemStack> pool = new ArrayList<>();
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                pool.add(stack.copy());
            }
        }

        if (pool.size() != ingredients.size()) {
            return false;
        }

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

    public NonNullList<Ingredient> ingredients() {
        return ingredients;
    }

    public ItemStack result() {
        return result;
    }

    @Override
    public ItemStack assemble(MoonAltarContainer container, RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<MoonAltarRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "moon_altar";
    }

    public static class Serializer implements RecipeSerializer<MoonAltarRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(SteelworksWeapons.MODID, "moon_altar");


        @Override
        public MoonAltarRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {

            NonNullList<Ingredient> ingredients = NonNullList.create();
            if (GsonHelper.isArrayNode(serializedRecipe, "ingredients")) {
                JsonArray jsonIngredients = GsonHelper.getAsJsonArray(serializedRecipe, "ingredients");
                for (var element : jsonIngredients) {
                    ingredients.add(Ingredient.fromJson(element));
                }
            }

            JsonObject catalystJson = GsonHelper.getAsJsonObject(serializedRecipe, "catalyst");
            Ingredient catalyst = Ingredient.fromJson(catalystJson);

            JsonObject resultJson = GsonHelper.getAsJsonObject(serializedRecipe, "result");
            ItemStack result = CraftingHelper.getItemStack(resultJson, true);

            return new MoonAltarRecipe(recipeId, catalyst, ingredients, result);
        }

        @Override
        public @Nullable MoonAltarRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buf) {
            Ingredient catalyst = Ingredient.fromNetwork(buf);

            int size = buf.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);

            for (int i = 0; i < size; i++) {
                ingredients.set(i, Ingredient.fromNetwork(buf));
            }
            ItemStack result = buf.readItem();

            return new MoonAltarRecipe(recipeId, catalyst, ingredients, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, MoonAltarRecipe recipe) {
            recipe.catalystItem.toNetwork(buf);
            buf.writeVarInt(recipe.ingredients().size());
            for (Ingredient ingredient : recipe.ingredients()) {
                ingredient.toNetwork(buf);
            }
            buf.writeItem(recipe.result());
        }
    }
}
