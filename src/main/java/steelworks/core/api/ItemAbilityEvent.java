package steelworks.core.api;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class ItemAbilityEvent extends Event {

    private final Player player;
    public ItemAbilityEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
