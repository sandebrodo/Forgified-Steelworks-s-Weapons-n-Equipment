package steelworks.core.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BleedingEffect extends MobEffect {
    public BleedingEffect() {
        super(MobEffectCategory.HARMFUL, 0x8B0000);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        int interval = Math.max(5, 30 - (5 * amplifier));

        if (entity.tickCount % interval == 0) {

            float damage = 1.0F;

            entity.hurt(entity.damageSources().generic(), damage);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
