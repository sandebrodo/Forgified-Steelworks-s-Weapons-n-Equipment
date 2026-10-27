package steelworks.core.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.stringtemplate.v4.ST;
import steelworks.SteelworksWeapons;
import steelworks.core.item.ItemEngraveManager;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;


// WIP
@Mod.EventBusSubscriber(modid = SteelworksWeapons.MODID, value = Dist.CLIENT)
public class EngraveTooltipHandler {

    private static final ItemEngraveManager MANAGER = new ItemEngraveManager();

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        ItemEngraveManager.Engraves engrave = MANAGER.getEngrave(stack);

        if (engrave == null) return;
        if (event.getEntity() == null) return;

        if (!event.getEntity().isShiftKeyDown()) return;

        event.getToolTip().add(Component.translatable("tooltip.steelworks_weapons.engravetooltip", getLocalizedEngraveName(engrave), getLocalizedAttributes(engrave)).withStyle(ChatFormatting.AQUA));
    }

    private static Component getLocalizedEngraveName(ItemEngraveManager.Engraves engrave) {
        String name = engrave.name().toLowerCase();
        return Component.translatable("steelworks_weapons.engrave." + name);
    }



    private static Component getLocalizedAttributes(ItemEngraveManager.Engraves engrave) {
        var attributes = engrave.getAttributes();
        MutableComponent localizedAttributes = Component.empty();

        for (ItemEngraveManager.Engraves.EngraveAttribute attr : attributes) {
            Component attributeComponent = Component.translatable("steelworks_weapons." + attr.getAttributeName(), attr.amount());
            if (attributes.size() > 1) {
                localizedAttributes.append(attributeComponent).append("\n");
            }
        }

        return localizedAttributes;
    }
}
