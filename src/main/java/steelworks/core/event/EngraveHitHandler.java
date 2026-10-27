package steelworks.core.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import steelworks.SteelworksWeapons;
import steelworks.core.item.ItemEngraveManager;

//WIP
@Mod.EventBusSubscriber(modid = SteelworksWeapons.MODID)
public class EngraveHitHandler {

    private static final ItemEngraveManager MANAGER = new ItemEngraveManager();

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {

        DamageSource damageSource = event.getSource();
        LivingEntity target = event.getEntity();

        if (!(damageSource.getEntity() instanceof LivingEntity attacker)) return;

        ItemStack weapon = attacker.getMainHandItem();
        if (weapon.isEmpty()) return;

        ItemEngraveManager.Engraves engrave = MANAGER.getEngrave(weapon);
        if (engrave != null) {
            engrave.applyHitEffect(weapon, target, attacker);
        }
    }
}
