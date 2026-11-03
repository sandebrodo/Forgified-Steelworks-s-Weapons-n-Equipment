package steelworks.core.item;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import org.jetbrains.annotations.NotNull;

public class TieredBowItem extends BowItem {
    private final double arrowBaseDamage;

    public TieredBowItem(double arrowBaseDamage, Tier tier, Properties properties) {
        super(properties.durability(tier.getUses()));
        this.arrowBaseDamage = arrowBaseDamage;
    }

    @Override
    public AbstractArrow customArrow(AbstractArrow arrow, ItemStack projectileStack, ItemStack weaponStack) {
        arrow.setBaseDamage(this.arrowBaseDamage);
        return super.customArrow(arrow, projectileStack, weaponStack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}
