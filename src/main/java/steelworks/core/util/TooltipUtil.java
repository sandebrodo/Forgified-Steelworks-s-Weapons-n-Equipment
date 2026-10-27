package steelworks.core.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import steelworks.core.event.KeyBindingsRegisterEvent;

import java.util.List;

public class TooltipUtil {

    public static void addAbilityKeybindTooltip(List<Component> tooltipComponents) {
        Component key = KeyBindingsRegisterEvent.ABILITY_KEY.getTranslatedKeyMessage();
        tooltipComponents.add(Component.translatable("tooltip.steelworks_weapons.item_special_ability", key.copy().withStyle(ChatFormatting.GRAY)));
    }

    public static void addPassiveAbilityTooltip(List<Component> tooltipComponents) {
        tooltipComponents.add(Component.translatable("tooltip.steelworks_weapons.item_special_ability_is_passive"));
    }

    public static void addAbilityTooltip(List<Component> tooltipComponents) {
        tooltipComponents.add(Component.translatable("tooltip.steelworks_weapons.has_special_ability"));
    }

    public static void addHoldShiftTooltip(List<Component> tooltipComponents) {
        tooltipComponents.add(Component.translatable("tooltip.steelworks_weapons.hold_shift_for_more_info"));
    }

    public static void addIndentations(List<Component> tooltipComponents, int count) {
        MutableComponent component = Component.empty();
        if (count <= 0) return;
        for (int i = 0; i < count; i++) {
            if (i > 0) {
                component.append("\n");
            }
        }
        tooltipComponents.add(component);
    }
}
