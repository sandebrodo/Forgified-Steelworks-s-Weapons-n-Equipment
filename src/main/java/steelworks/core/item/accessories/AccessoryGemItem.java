package steelworks.core.item.accessories;

import net.minecraft.world.item.Item;

public class AccessoryGemItem extends Item {

    private final AccessoryGem gem;

    public AccessoryGemItem(AccessoryGem gem, Properties properties) {
        super(properties);
        this.gem = gem;
    }

    public AccessoryGem get() {
        return gem;
    }
}
