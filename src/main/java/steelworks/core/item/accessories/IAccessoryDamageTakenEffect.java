package steelworks.core.item.accessories;

import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface
public interface IAccessoryDamageTakenEffect {
    void apply(LivingEntity target, LivingEntity source);
}
