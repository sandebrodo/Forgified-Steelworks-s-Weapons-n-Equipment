package steelworks.core.item;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

public class TieredBowItem extends BowItem {

    private final Tier tier;
    private final double arrowBaseDamage;

    public TieredBowItem(double arrowBaseDamage, Tier tier, Properties properties) {
        super(properties.durability(tier.getUses()));
        this.arrowBaseDamage = arrowBaseDamage;
        this.tier = tier;
    }

    @Override
    public @NotNull AbstractArrow customArrow(AbstractArrow arrow) {
        float tierDamageBonus = tier.getAttackDamageBonus() / 2;
        arrow.setBaseDamage(arrowBaseDamage + tierDamageBonus);
        return super.customArrow(arrow);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getEnchantmentValue() {
        return tier.getEnchantmentValue();
    }
}