package steelworks.core.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import steelworks.SteelworksWeapons;

import java.util.function.Supplier;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, SteelworksWeapons.MODID);

    public static final Supplier<SimpleParticleType> MOON_ALTAR_PARTICLE_1 =
            PARTICLES.register("moon_altar_particle_1", () -> new SimpleParticleType(false));

    public static final Supplier<SimpleParticleType> MOON_ALTAR_PARTICLE_2 =
            PARTICLES.register("moon_altar_particle_2", () -> new SimpleParticleType(false));

    public static final Supplier<SimpleParticleType> MOON_ALTAR_PARTICLE_3 =
            PARTICLES.register("moon_altar_particle_3", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }

}
