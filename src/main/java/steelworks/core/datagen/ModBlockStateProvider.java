package steelworks.core.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
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

    private void blockItem(DeferredHolder<Block, ? extends Block> blockHolder) {
        simpleBlockItem(
                blockHolder.get(),
                new ModelFile.UncheckedModelFile(
                        SteelworksWeapons.MODID + ":block/" +
                                BuiltInRegistries.BLOCK.getKey(blockHolder.get()).getPath()
                )
        );
    }

    private void blockWithCustomModel(DeferredHolder<Block, ? extends Block> blockHolder) {
        ModelFile model = new ModelFile.UncheckedModelFile(
                SteelworksWeapons.MODID + ":block/" +
                        BuiltInRegistries.BLOCK.getKey(blockHolder.get()).getPath()
        );

        simpleBlockWithItem(blockHolder.get(), model);
    }

    private void blockWithCustomGeoModel(DeferredHolder<Block, ? extends Block> blockHolder) {
        ModelFile model = new ModelFile.UncheckedModelFile(
                SteelworksWeapons.MODID + ":block/3d/" +
                        BuiltInRegistries.BLOCK.getKey(blockHolder.get()).getPath() + "_3d"
        );

        simpleBlockWithItem(blockHolder.get(), model);
    }

    private void blockWithItem(DeferredHolder<Block, ? extends Block> blockHolder) {
        simpleBlockWithItem(blockHolder.get(), cubeAll(blockHolder.get()));
    }

}
