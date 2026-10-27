package steelworks.core.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface IEngraveHitEffect {
    void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker);
}
