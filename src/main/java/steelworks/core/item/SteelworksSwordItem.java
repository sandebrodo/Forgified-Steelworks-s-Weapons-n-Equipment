package steelworks.core.item;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class SteelworksSwordItem extends SwordItem {
    public SteelworksSwordItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, properties);
        createAttributes(tier, attackDamage, attackSpeed);
    }
}
