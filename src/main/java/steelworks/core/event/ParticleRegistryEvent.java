package steelworks.core.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import steelworks.SteelworksWeapons;
import steelworks.core.particle.MoonAltarParticle;
import steelworks.core.registry.ModParticles;

@EventBusSubscriber(modid = SteelworksWeapons.MODID)
public class ParticleRegistryEvent {

    @SubscribeEvent
    public static void registerProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.MOON_ALTAR_PARTICLE_1.get(), MoonAltarParticle.Provider::new);
        event.registerSpriteSet(ModParticles.MOON_ALTAR_PARTICLE_2.get(), MoonAltarParticle.Provider::new);
        event.registerSpriteSet(ModParticles.MOON_ALTAR_PARTICLE_3.get(), MoonAltarParticle.Provider::new);
    }
}
