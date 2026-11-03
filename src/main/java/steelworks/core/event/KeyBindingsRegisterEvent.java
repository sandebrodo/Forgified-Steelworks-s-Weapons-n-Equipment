package steelworks.core.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;
import steelworks.SteelworksWeapons;

@EventBusSubscriber(modid = SteelworksWeapons.MODID)
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
