package steelworks.core.item;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;

public class SteelworksAxeItem extends AxeItem {
    public SteelworksAxeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, properties);
        createAttributes(tier, attackDamage, attackSpeed);
    }
}
