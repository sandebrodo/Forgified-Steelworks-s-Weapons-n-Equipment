package steelworks.core.registry;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import steelworks.SteelworksWeapons;
import steelworks.core.effects.BleedingEffect;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SteelworksWeapons.MODID);

    public static final RegistryObject<MobEffect> BLEEDING = MOB_EFFECTS.register("bleeding", BleedingEffect::new);

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
