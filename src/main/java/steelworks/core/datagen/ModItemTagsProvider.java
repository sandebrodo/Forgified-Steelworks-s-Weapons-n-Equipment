package steelworks.core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import steelworks.SteelworksWeapons;
import steelworks.core.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, SteelworksWeapons.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.BOW_ENCHANTABLE)
                .add(
                        ModItems.COPPER_BOW.get(),
                        ModItems.IRON_BOW.get(),
                        ModItems.GOLDEN_BOW.get(),
                        ModItems.DIAMOND_BOW.get(),
                        ModItems.NETHERITE_BOW.get(),
                        ModItems.FORGOTTEN_BOW.get()
                );
    }
}
