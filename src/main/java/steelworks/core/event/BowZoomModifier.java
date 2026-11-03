package steelworks.core.event;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import steelworks.SteelworksWeapons;
import steelworks.core.item.TieredBowItem;

@EventBusSubscriber(modid = SteelworksWeapons.MODID)
public class BowZoomModifier {

    @SubscribeEvent
    public static void onComputeFov(ViewportEvent.ComputeFov event) {

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        if (!player.isUsingItem()) return;

        ItemStack useItem = player.getUseItem();
        if (!(useItem.getItem() instanceof TieredBowItem)) return;

        float useDuration = useItem.getUseDuration(player);
        float zoomModifier = getZoomModifier(event, useDuration, player);

        event.setFOV(event.getFOV() * zoomModifier);
    }

    static float getZoomModifier(ViewportEvent.ComputeFov event, float useDuration, Player player) {
        float ticksInUse = useDuration - player.getUseItemRemainingTicks();

        float partialTick = (float) event.getPartialTick();

        float pullProgress = (ticksInUse + partialTick) / 20.0F;
        pullProgress = Math.min(pullProgress, 1.0F);

        if (pullProgress < 1.0F) {
            pullProgress *= pullProgress;
        }

        return 1.0F - pullProgress * 0.1F;
    }

}
