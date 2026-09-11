package steelworks;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import steelworks.core.registry.ModBlocks;
import steelworks.core.registry.ModCreativeModeTabs;
import steelworks.core.registry.ModEffects;
import steelworks.core.registry.ModItems;

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
    }

}
