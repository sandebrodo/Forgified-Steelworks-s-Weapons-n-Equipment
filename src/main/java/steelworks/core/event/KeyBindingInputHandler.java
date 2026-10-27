package steelworks.core.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import steelworks.SteelworksWeapons;
import steelworks.core.network.AbilityKeyPacket;
import steelworks.core.registry.ModNetwork;

@Mod.EventBusSubscriber(modid = SteelworksWeapons.MODID, value = Dist.CLIENT)
public class KeyBindingInputHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBindingsRegisterEvent.ABILITY_KEY != null && KeyBindingsRegisterEvent.ABILITY_KEY.consumeClick()) {
            ModNetwork.CHANNEL.sendToServer(new AbilityKeyPacket(true));
        }
    }
}
