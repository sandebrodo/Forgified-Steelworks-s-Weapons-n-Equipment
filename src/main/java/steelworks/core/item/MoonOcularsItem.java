package steelworks.core.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import steelworks.core.util.TooltipUtil;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class MoonOcularsItem extends CurioBaseItem {
    public MoonOcularsItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        var entity = slotContext.entity();
        if (!(entity instanceof Player player)) return;

        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, false, false));

        super.curioTick(slotContext, stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        TooltipUtil.addHoldShiftTooltip(tooltipComponents);
        if (Screen.hasShiftDown()) {
            TooltipUtil.addIndentations(tooltipComponents, 1);
            TooltipUtil.addAbilityTooltip(tooltipComponents);
            TooltipUtil.addPassiveAbilityTooltip(tooltipComponents);
            TooltipUtil.addIndentations(tooltipComponents, 1);
            tooltipComponents.add(Component.translatable("tooltip.steelworks_weapons.moon_oculars"));
        }
    }
}
