package steelworks.core.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import steelworks.SteelworksWeapons;
import steelworks.core.particle.MoonAltarParticle;
import steelworks.core.registry.ModParticles;

@Mod.EventBusSubscriber(modid = SteelworksWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleRegistryEvent {

    @SubscribeEvent
    public static void registerProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.MOON_ALTAR_PARTICLE_1.get(), MoonAltarParticle.Provider::new);
        event.registerSpriteSet(ModParticles.MOON_ALTAR_PARTICLE_2.get(), MoonAltarParticle.Provider::new);
        event.registerSpriteSet(ModParticles.MOON_ALTAR_PARTICLE_3.get(), MoonAltarParticle.Provider::new);
    }

}
