package steelworks;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import steelworks.core.registry.*;

@Mod(SteelworksWeapons.MODID)
public class SteelworksWeapons {


    public static final String MODID = "steelworks_weapons";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SteelworksWeapons(IEventBus modEventBus, ModContainer modContainer) {

        ModItems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModEffects.register(modEventBus);
        ModTileEntities.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModParticles.register(modEventBus);

    }

    public static ResourceLocation resourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
