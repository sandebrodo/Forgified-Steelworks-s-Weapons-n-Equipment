package steelworks.core.item;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

public class TieredBowItem extends BowItem {

    private final double arrowBaseDamage;

    public TieredBowItem(double arrowBaseDamage, Tier tier, Properties properties) {
        super(properties.durability(tier.getUses()));
        this.arrowBaseDamage = arrowBaseDamage;
    }

    @Override
    public @NotNull AbstractArrow customArrow(AbstractArrow arrow) {
        arrow.setBaseDamage(arrowBaseDamage);
        return super.customArrow(arrow);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}