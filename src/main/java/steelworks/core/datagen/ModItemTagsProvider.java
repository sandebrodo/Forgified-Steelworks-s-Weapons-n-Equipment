package steelworks.core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import steelworks.SteelworksWeapons;
import steelworks.core.item.HammerItem;
import steelworks.core.item.SteelworksAxeItem;
import steelworks.core.item.SteelworksSwordItem;
import steelworks.core.item.TieredBowItem;
import steelworks.core.registry.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, SteelworksWeapons.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        tag(ItemTags.BOW_ENCHANTABLE).addAll(getItemFromClass(TieredBowItem.class));
        tag(ItemTags.DURABILITY_ENCHANTABLE).addAll(getItemFromClass(TieredBowItem.class));
        tag(ItemTags.SWORDS).addAll(getItemFromClass(SteelworksSwordItem.class));
        tag(ItemTags.SWORDS).addAll(getItemFromClass(SteelworksAxeItem.class));
        tag(ItemTags.PICKAXES).addAll(getItemFromClass(HammerItem.class));
        tag(ItemTags.SHOVELS).addAll(getItemFromClass(HammerItem.class));
    }

    static List<ResourceKey<Item>> getItemFromClass(Class<? extends Item> type) {
        return ModItems.ITEMS.getEntries().stream()
                .filter(item -> type.isInstance(item.get()))
                .map(DeferredHolder::getKey)
                .toList();
    }
}
