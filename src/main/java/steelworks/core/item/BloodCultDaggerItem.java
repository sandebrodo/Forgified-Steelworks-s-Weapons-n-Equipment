package steelworks.core.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import steelworks.core.registry.ModEffects;

public class BloodCultDaggerItem extends SwordItem {
    public BloodCultDaggerItem(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {

        target.addEffect(new MobEffectInstance(ModEffects.BLEEDING.get(), 200, 0, false, false));
        attacker.heal(1.0F);

        return super.hurtEnemy(itemStack, target, attacker);
    }
}
