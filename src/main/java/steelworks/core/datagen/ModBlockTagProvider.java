package steelworks.core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import steelworks.SteelworksWeapons;
import steelworks.core.registry.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {

    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, SteelworksWeapons.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.FORGOTTEN_BLOCK.get(),
                        ModBlocks.MOON_ALTAR.get(),
                        ModBlocks.MOON_PEDESTAL.get(),
                        ModBlocks.MOON_PEBBLES.get(),
                        ModBlocks.MOONSTONE.get());

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.FORGOTTEN_BLOCK.get());

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.MOON_ALTAR.get(),
                        ModBlocks.MOON_PEDESTAL.get(),
                        ModBlocks.MOONSTONE.get());
    }
}
