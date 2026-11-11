package steelworks.core.item.accessories;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface IAccessoryGemTickEffect {
    void apply(Player player);
}
