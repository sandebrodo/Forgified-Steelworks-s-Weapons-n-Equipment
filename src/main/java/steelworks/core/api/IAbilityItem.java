package steelworks.core.api;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IAbilityItem {

    void onKeyPressed(Player player, ItemStack stack);
    int getAbilityCooldown();
}