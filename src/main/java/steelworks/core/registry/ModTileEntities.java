package steelworks.core.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import steelworks.SteelworksWeapons;
import steelworks.core.block.tile.MoonAltarTileEntity;
import steelworks.core.block.tile.PedestalTileEntity;

import java.util.function.Supplier;

public class ModTileEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES
            = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, SteelworksWeapons.MODID);

    public static final Supplier<BlockEntityType<PedestalTileEntity>> PEDESTAL_TE = TILE_ENTITIES.register("pedestal_te",
            () -> BlockEntityType.Builder.of(PedestalTileEntity::new, ModBlocks.MOON_PEDESTAL.get()).build(null));

    public static final Supplier<BlockEntityType<MoonAltarTileEntity>> MOON_ALTAR_TE = TILE_ENTITIES.register("moon_altar_te",
            () -> BlockEntityType.Builder.of(MoonAltarTileEntity::new, ModBlocks.MOON_ALTAR.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}
