package steelworks.core.item;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class SteelworksSwordItem extends SwordItem {

    private final Tier tier;

    public SteelworksSwordItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, properties.attributes(createAttributes(tier, attackDamage, attackSpeed)));
        this.tier = tier;
    }

    @Override
    public int getEnchantmentValue() {
        return tier.getEnchantmentValue();
    }
}
