package steelworks.core.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import steelworks.core.util.ItemFlagUtil;
import steelworks.core.util.TooltipUtil;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class WhiteMoonAmuletItem extends CurioBaseItem {

    public static final String IS_ACTIVE = "is_white_moon_amulet_active";

    public WhiteMoonAmuletItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        var entity = slotContext.entity();
        if (!(entity instanceof Player player)) return;
        var cooldowns = player.getCooldowns();
        if (!isActive(stack) && !cooldowns.isOnCooldown(this)) {
            ItemFlagUtil.setBoolFlag(stack, IS_ACTIVE, true);
        }

        super.curioTick(slotContext, stack);
    }

    public boolean isActive(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(IS_ACTIVE) && tag.getBoolean(IS_ACTIVE);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        TooltipUtil.addHoldShiftTooltip(tooltipComponents);
        if (Screen.hasShiftDown()) {
            TooltipUtil.addIndentations(tooltipComponents, 1);
            TooltipUtil.addAbilityTooltip(tooltipComponents);
            TooltipUtil.addPassiveAbilityTooltip(tooltipComponents);
            TooltipUtil.addIndentations(tooltipComponents, 1);
            tooltipComponents.add(Component.translatable("tooltip.steelworks_weapons.white_moon_amulet"));
        }
    }
}
