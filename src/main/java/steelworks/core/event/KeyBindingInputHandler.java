package steelworks.core.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import steelworks.SteelworksWeapons;
import steelworks.core.network.AbilityKeyPayload;
import steelworks.core.registry.ModNetwork;

@EventBusSubscriber(modid = SteelworksWeapons.MODID)
public class KeyBindingInputHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBindingsRegisterEvent.ABILITY_KEY != null && KeyBindingsRegisterEvent.ABILITY_KEY.consumeClick()) {
            AbilityKeyPayload.send();
        }
    }
}
