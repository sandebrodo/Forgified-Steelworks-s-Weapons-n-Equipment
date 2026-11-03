package steelworks.core.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import steelworks.SteelworksWeapons;

public class ItemFlagUtil {

    private static final String PREFIX = "steelworks_weapons_";

    public static CustomData getCustomData(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
    }

    public static void setTimerFlag(ItemStack stack, int durationTicks, String flagName, Level level) {
        String key = PREFIX + flagName;
        long expiresAt = level.getGameTime() + durationTicks;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putLong(key, expiresAt));
    }

    public static void removeExpiredTimerFlag(ItemStack stack, String flagName, Level level) {
        if (hasFlag(stack, flagName)) return;
        String key = PREFIX + flagName;
        long gameTime = level.getGameTime();

        CustomData data = getCustomData(stack);
        long expiresAt = data.copyTag().getLong(key);

        if (gameTime >= expiresAt) {
            data.copyTag().remove(key);
        }
    }

    public static boolean hasFlag(ItemStack stack, String flagName) {
        String key = PREFIX + flagName;
        boolean hasFlag = getCustomData(stack).contains(key);
        if (!hasFlag) {
            SteelworksWeapons.LOGGER.debug("ItemStack {} has no flag {}", stack, flagName);
        }

        return hasFlag;
    }

    public static void removeFlag(ItemStack stack, String flagName) {
        if (hasFlag(stack, flagName)) return;
        String key = PREFIX + flagName;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.remove(key));
    }

    public static void setBoolFlag(ItemStack stack, String flagName, boolean state) {
        String key = PREFIX + flagName;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putBoolean(key, state));
    }
}
