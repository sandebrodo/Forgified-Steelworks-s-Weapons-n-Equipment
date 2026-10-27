package steelworks.core.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ItemFlagUtil {

    private static final String PREFIX = "steelworks_weapons";

    public static void setTimerFlag(ItemStack stack, int durationTicks, String flagName, Level level) {
        CompoundTag tag = stack.getOrCreateTag();
        String key = PREFIX + flagName;
        long expiresAt = level.getGameTime() + durationTicks;
        tag.putLong(key, expiresAt);
    }

    public static boolean removeExpiredTimerFlag(ItemStack stack, String flagName, Level level) {
        if (!hasFlag(stack, flagName)) return false;
        String key = PREFIX + flagName;
        long gameTime = level.getGameTime();
        CompoundTag tag = stack.getTag();
        if (gameTime >= tag.getLong(key)) {
            tag.remove(key);
            return true;
        }
        return false;
    }

    public static boolean hasFlag(ItemStack stack, String flagName) {
        CompoundTag tag = stack.getTag();
        String key = PREFIX + flagName;
        return tag != null && tag.contains(key);
    }

    public static void removeFlag(ItemStack stack, String flagName) {
        if (hasFlag(stack, flagName)) return;
        CompoundTag tag = stack.getTag();
        String key = PREFIX + flagName;
        tag.remove(key);
    }

    public static void setBoolFlag(ItemStack stack, String flagName, boolean state) {
        CompoundTag tag = stack.getOrCreateTag();
        String key = PREFIX + flagName;
        tag.putBoolean(key, state);
    }
}
