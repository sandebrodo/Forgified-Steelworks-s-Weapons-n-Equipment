package steelworks;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import steelworks.core.registry.*;
import top.theillusivec4.curios.api.CuriosApi;

@Mod(SteelworksWeapons.MODID)
@SuppressWarnings("removal")
public class SteelworksWeapons {

    public static final String MODID = "steelworks_weapons";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SteelworksWeapons() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEffects.register(modEventBus);
        ModTileEntities.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModParticles.register(modEventBus);
        ModNetwork.register();
    }

    public static ResourceLocation resourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
