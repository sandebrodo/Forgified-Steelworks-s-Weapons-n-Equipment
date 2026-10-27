package steelworks.core.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import steelworks.SteelworksWeapons;

@Mod.EventBusSubscriber(modid = SteelworksWeapons.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyBindingsRegisterEvent {

    public static KeyMapping ABILITY_KEY;

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        ABILITY_KEY = new KeyMapping(
          "key.steelworks_weapons.item_ability",
          KeyConflictContext.IN_GAME,
          InputConstants.Type.KEYSYM,
          GLFW.GLFW_KEY_H,
          "key.categories.steelworks_weapons"
        );
        event.register(ABILITY_KEY);
    }

}
