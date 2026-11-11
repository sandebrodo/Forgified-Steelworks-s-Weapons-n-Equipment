package steelworks.core.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import steelworks.SteelworksWeapons;

@Mod.EventBusSubscriber(modid = SteelworksWeapons.MODID)
public class AccessoryGemHitHandler {

    @SubscribeEvent
    public static void onHit(LivingHurtEvent event) {

        var sourceEntity = event.getSource().getEntity();
        var target = event.getEntity();





    }
}
