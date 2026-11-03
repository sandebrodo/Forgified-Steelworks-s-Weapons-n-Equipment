package steelworks.core.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import steelworks.SteelworksWeapons;
import steelworks.core.block.MoonAltarBlock;
import steelworks.core.block.MoonPebblesBlock;
import steelworks.core.block.MoonPedestalBlock;
import steelworks.core.item.GeoBlockItem;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(SteelworksWeapons.MODID);

    public static final DeferredHolder<Block, Block> FORGOTTEN_BLOCK = registerBlock("forgotten_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)));

    public static final DeferredHolder<Block, MoonPedestalBlock> MOON_PEDESTAL = registerBlock("moon_pedestal",
            () -> new MoonPedestalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final DeferredHolder<Block, MoonAltarBlock> MOON_ALTAR = registerGeoBlock("moon_altar",
            () -> new MoonAltarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final DeferredHolder<Block, Block> MOONSTONE = registerBlock("moonstone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

    public static final DeferredHolder<Block, MoonPebblesBlock> MOON_PEBBLES = registerBlock("moon_pebbles",
            () -> new MoonPebblesBlock(BlockBehaviour.Properties.of()));

    private static <T extends Block> DeferredHolder<Block, T> registerGeoBlock(String name, Supplier<T> block) {
        DeferredHolder<Block, T> toReturn = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new GeoBlockItem(toReturn.get(), new Item.Properties(), name));
        ModCreativeModeTabs.blocks.add(toReturn);
        return toReturn;
    }



    private static <T extends Block> DeferredHolder<Block, T> registerBlock(String name, Supplier<T> block) {
        DeferredHolder<Block, T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        ModCreativeModeTabs.blocks.add(toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredHolder<Block, T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
