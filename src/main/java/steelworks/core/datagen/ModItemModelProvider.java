package steelworks.core.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import steelworks.SteelworksWeapons;
import steelworks.core.registry.ModItems;

import java.util.*;

@SuppressWarnings("removal")
public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SteelworksWeapons.MODID, existingFileHelper);
    }

    public static List<RegistryObject<? extends Item>> basic_weapons = new ArrayList<>();
    public static Map<RegistryObject<? extends Item>, float[]> scaled_weapons_32 = new HashMap<>();
    public static List<RegistryObject<? extends Item>> scaled_weapons_16 = new ArrayList<>();
    public static List<RegistryObject<? extends Item>> items = new ArrayList<>();
    public static List<RegistryObject<? extends Item>> custom_parent_model_weapons = new ArrayList<>();
    public static List<RegistryObject<? extends Item>> bows = new ArrayList<>();

    @Override
    protected void registerModels() {
        basic_weapons.forEach(item -> handheldItem(item.get()));
        for (Map.Entry<RegistryObject<? extends Item>, float[]> entry : scaled_weapons_32.entrySet()) {
            RegistryObject<? extends Item> item = entry.getKey();
            float[] translation = entry.getValue();
            float transX = translation[0];
            float transY = translation[1];
            float transZ = translation[2];
            scaledHandheldItem32(item.get(), transX, transY, transZ);
        }
        scaled_weapons_16.forEach(item -> scaledHandheldItem16(item.get()));
        items.forEach(item -> basicItem(item.get()));
        custom_parent_model_weapons.forEach(item -> customParentModelItem(item.get()));
        ModItems.bows.forEach(item -> bowItem(item.get()));
    }


    public ItemModelBuilder handheldItem(Item item) {
        return handheldItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
    }

    public ItemModelBuilder handheldItem(ResourceLocation item) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", new ResourceLocation(item.getNamespace(), "item/16x/" + item.getPath()));
    }

    public ItemModelBuilder scaledHandheldItem32(Item item, float transX, float transY, float transZ) {
        return scaledHandheldItem32(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)), transX, transY, transZ);
    }

    public ItemModelBuilder scaledHandheldItem32(ResourceLocation item, float transX, float transY, float transZ) {

        ItemModelBuilder handheld = new ItemModelBuilder(
                new ResourceLocation(SteelworksWeapons.MODID, "item/32x/" + item.getPath()),
                existingFileHelper
        ).parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", new ResourceLocation(modid, "item/32x/" + item.getPath()));

        ItemModelBuilder icon = new ItemModelBuilder(
                new ResourceLocation(SteelworksWeapons.MODID, "item/16x/" + item.getPath()),
                existingFileHelper
        ).parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(modid, "item/16x/" + item.getPath()));

        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .customLoader(SeparateTransformsModelBuilder::begin)
                .base(handheld.transforms()
                        .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                        .scale(2f, 2f, 1f)
                        .rotation(0f, -90f, 55f)
                        .translation(transX, transY, transZ)
                        .end()
                        .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
                        .scale(2f, 2f, 1f)
                        .rotation(0f, 90f, -55f)
                        .translation(transX, transY, transZ)
                        .end()

                        .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                        .scale(1.5f, 1.5f, 1f)
                        .rotation(0f, -90f, 55f)
                        .translation(0f, 4f, 0.5f)
                        .end()
                        .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                        .scale(1.5f, 1.5f, 1f)
                        .rotation(0f, 90f, -55f)
                        .translation(0f, 4f, 0.5f)
                        .end()
                        .end())
                .perspective(ItemDisplayContext.GUI, icon)
                .perspective(ItemDisplayContext.FIXED, icon)
                .perspective(ItemDisplayContext.GROUND, icon)
                .end();
    }

    public ItemModelBuilder scaledHandheldItem16(Item item) {
        return scaledHandheldItem16(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
    }

    public ItemModelBuilder scaledHandheldItem16(ResourceLocation item) {


        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", new ResourceLocation(modid, "item/16x/" + item.getPath()))
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .scale(1.1f, 1.1f, 1f)
                .rotation(0f, -90f, 55f)
                .translation(0f, 5f, 0.5f)
                .end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
                .scale(1.1f, 1.1f, 1f)
                .rotation(0f, 90f, -55f)
                .translation(0f, 5f, 0.5f)
                .end()
                .end();
    }

    public ItemModelBuilder customParentModelItem(Item item) {
        return customParentModelItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)), removePrefix(item.toString()));
    }

    public ItemModelBuilder customParentModelItem(ResourceLocation item, String weapon_type) {
        String itemPath = item.getPath();

        ItemModelBuilder base = new ItemModelBuilder(
                new ResourceLocation(SteelworksWeapons.MODID, "item/3d/" + "mod_" + weapon_type),
                existingFileHelper
        ).parent(new ModelFile.UncheckedModelFile(SteelworksWeapons.MODID + ":item/" + "mod_" + weapon_type))
                .texture("layer0", new ResourceLocation(modid, "item/custom_models/3d/" + itemPath + "_3d"));

        ItemModelBuilder icon = new ItemModelBuilder(
                new ResourceLocation(SteelworksWeapons.MODID, "item/2d/" + itemPath),
                existingFileHelper
        ).parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(modid, "item/custom_models/2d/" + itemPath + "_2d"));

        return this.getBuilder(itemPath)
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .customLoader(SeparateTransformsModelBuilder::begin)
                .base(base)
                .perspective(ItemDisplayContext.GUI, icon)
                .perspective(ItemDisplayContext.GROUND, icon)
                .perspective(ItemDisplayContext.FIXED, icon)
                .end();
    }

    public static String removePrefix(String input) {
        return input.replaceFirst("^[^_]+_", "");
    }

    @Override
    public ItemModelBuilder basicItem(ResourceLocation item) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(item.getNamespace(), "item/16x/" + item.getPath()));
    }

    public void bowItem(Item item) {
        bowItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
    }

    public ItemModelBuilder bowItem(ResourceLocation item) {
        String name = item.getPath();

        ItemModelBuilder pull0 = bowPulling(0, item);
        ItemModelBuilder pull1 = bowPulling(1, item);
        ItemModelBuilder pull2 = bowPulling(2, item);

        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(modid, "item/16x/" + name))
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .rotation(-80f, 260f, -40f)
                .translation(-1f, -2f, 2.5f)
                .scale(0.9f, 0.9f, 0.9f)
                .end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
                .rotation(-80f, -280f, 40f)
                .translation(-1f, -2f, 2.5f)
                .scale(0.9f, 0.9f, 0.9f)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0f, -90f, 25f)
                .translation(1.13f, 3.2f, 1.13f)
                .scale(0.68f, 0.68f, 0.68f)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                .rotation(0f, 90f, -25f)
                .translation(1.13f, 3.2f, 1.13f)
                .scale(0.68f, 0.68f, 0.68f)
                .end()
                .end()
                .override()
                .predicate(new ResourceLocation("pulling"), 1.0f)
                .model(pull0)
                .end()
                .override()
                .predicate(new ResourceLocation("pulling"), 1.0f)
                .predicate(new ResourceLocation("pull"), 0.65f)
                .model(pull1)
                .end()
                .override()
                .predicate(new ResourceLocation("pulling"), 1.0f)
                .predicate(new ResourceLocation("pull"), 0.9f)
                .model(pull2)
                .end();
    }

    public ItemModelBuilder bowPulling(int number, ResourceLocation item) {

        String modelPath = item.getNamespace() + ":item/" + item.getPath() + "_pulling_" + number;

        return getBuilder(modelPath)
                .parent(new ModelFile.UncheckedModelFile("item/bow"))
                .texture("layer0", new ResourceLocation(item.getNamespace(), "item/16x/" + item.getPath()) + "_pulling_" + number);
    }
}
