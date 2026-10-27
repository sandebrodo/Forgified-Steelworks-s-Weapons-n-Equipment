package steelworks.core.datagen;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import steelworks.core.registry.ModBlocks;
import steelworks.core.registry.ModItems;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.FORGOTTEN_BLOCK.get());
        this.dropSelf(ModBlocks.MOON_ALTAR.get());
        this.dropSelf(ModBlocks.MOON_PEDESTAL.get());
        this.dropSelf(ModBlocks.MOONSTONE.get());

        this.add(ModBlocks.MOON_PEBBLES.get(), block ->
                createOreLikeDrops(ModBlocks.MOON_PEBBLES.get(), ModItems.MOON_PEBBLE.get(), 3));
    }

    private LootTable.Builder createOreLikeDrops(Block block, Item item, int maxCount) {
        return createSilkTouchDispatchTable(block,
                this.applyExplosionDecay(block,
                        LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, maxCount))))
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
