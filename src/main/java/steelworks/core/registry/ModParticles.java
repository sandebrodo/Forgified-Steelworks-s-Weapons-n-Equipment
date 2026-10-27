package steelworks.core.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import steelworks.SteelworksWeapons;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SteelworksWeapons.MODID);

    public static final RegistryObject<SimpleParticleType> MOON_ALTAR_PARTICLE_1 =
            PARTICLES.register("moon_altar_particle_1", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> MOON_ALTAR_PARTICLE_2 =
            PARTICLES.register("moon_altar_particle_2", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> MOON_ALTAR_PARTICLE_3 =
            PARTICLES.register("moon_altar_particle_3", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}
