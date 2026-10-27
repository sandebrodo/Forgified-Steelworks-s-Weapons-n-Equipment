package steelworks.core.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import steelworks.SteelworksWeapons;
import steelworks.core.registry.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SteelworksWeapons.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.FORGOTTEN_BLOCK);
        blockWithCustomModel(ModBlocks.MOON_PEDESTAL);
        blockWithCustomGeoModel(ModBlocks.MOON_ALTAR);
        blockWithItem(ModBlocks.MOONSTONE);
        blockWithCustomModel(ModBlocks.MOON_PEBBLES);
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(SteelworksWeapons.MODID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockWithCustomModel(RegistryObject<Block> blockRegistryObject) {
        ModelFile model = new ModelFile.UncheckedModelFile(SteelworksWeapons.MODID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath());

        simpleBlockWithItem(blockRegistryObject.get(), model);
    }

    private void blockWithCustomGeoModel(RegistryObject<Block> blockRegistryObject) {
        ModelFile model = new ModelFile.UncheckedModelFile(SteelworksWeapons.MODID +
                ":block/3d/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath() + "_3d");

        simpleBlockWithItem(blockRegistryObject.get(), model);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}