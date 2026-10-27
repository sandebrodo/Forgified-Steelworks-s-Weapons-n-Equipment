package steelworks.core.event;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import steelworks.SteelworksWeapons;
import steelworks.core.item.TieredBowItem;

@Mod.EventBusSubscriber (modid = SteelworksWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class BowZoomModifier {

    @SubscribeEvent
    public static void onComputeFov(ViewportEvent.ComputeFov event) {

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        if (!player.isUsingItem()) return;

        ItemStack useItem = player.getUseItem();
        if (!(useItem.getItem() instanceof TieredBowItem)) return;

        float useDuration = useItem.getUseDuration();
        float zoomModifier = getZoomModifier(event, useDuration, player);

        event.setFOV(event.getFOV() * zoomModifier);
    }

    private static float getZoomModifier(ViewportEvent.ComputeFov event, float useDuration, Player player) {
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
