package steelworks.core.registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import steelworks.SteelworksWeapons;
import steelworks.core.block.tile.MoonAltarTileEntity;
import steelworks.core.block.tile.PedestalTileEntity;

public class ModTileEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SteelworksWeapons.MODID);

    public static final RegistryObject<BlockEntityType<PedestalTileEntity>> PEDESTAL_TE = TILE_ENTITIES.register("pedestal_te", () ->
            BlockEntityType.Builder.of(PedestalTileEntity::new, ModBlocks.MOON_PEDESTAL.get()).build(null));

    public static final RegistryObject<BlockEntityType<MoonAltarTileEntity>> MOON_ALTAR_TE = TILE_ENTITIES.register("moon_altar_te", () ->
            BlockEntityType.Builder.of(MoonAltarTileEntity::new, ModBlocks.MOON_ALTAR.get()).build(null));


    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}
