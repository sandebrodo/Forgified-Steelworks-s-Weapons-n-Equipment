package steelworks.core.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import steelworks.SteelworksWeapons;
import steelworks.core.effect.BleedingEffect;

import java.util.function.Supplier;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, SteelworksWeapons.MODID);

    public static final Holder<MobEffect> BLEEDING = MOB_EFFECTS.register("bleeding", BleedingEffect::new);

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
