package steelworks.core.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import steelworks.SteelworksWeapons;
import steelworks.core.block.MoonAltarBlock;
import steelworks.core.block.MoonPebblesBlock;
import steelworks.core.block.MoonPedestalBlock;
import steelworks.core.item.GeoBlockItem;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SteelworksWeapons.MODID);

    public static final RegistryObject<Block> FORGOTTEN_BLOCK = registerBlock("forgotten_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK)));

    public static final RegistryObject<Block> MOON_PEDESTAL = registerBlock("moon_pedestal",
            () -> new MoonPedestalBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> MOON_ALTAR = registerGeoBlock("moon_altar",
            () -> new MoonAltarBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> MOONSTONE = registerBlock("moonstone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> MOON_PEBBLES = registerBlock("moon_pebbles",
            () -> new MoonPebblesBlock(BlockBehaviour.Properties.of()));


    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
        ModCreativeModeTabs.blocks.add(toReturn);
        return toReturn;
    }

    public static <T extends Block> RegistryObject<T> registerGeoBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new GeoBlockItem(toReturn.get(), new Item.Properties(), name));
        ModCreativeModeTabs.blocks.add(toReturn);
        return toReturn;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
