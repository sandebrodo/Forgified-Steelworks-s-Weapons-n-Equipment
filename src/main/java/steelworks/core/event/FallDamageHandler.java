package steelworks.core.event;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import steelworks.SteelworksWeapons;
import steelworks.core.item.WhiteMoonAmuletItem;
import steelworks.core.registry.ModItems;
import steelworks.core.util.ItemFlagUtil;
import steelworks.core.util.SteelworksCurioUtils;

@Mod.EventBusSubscriber(modid = SteelworksWeapons.MODID)
public class FallDamageHandler {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        var entity = event.getEntity();
        var source = event.getSource();
        if (!(entity instanceof Player player)) return;
        ItemStack stack = SteelworksCurioUtils.getCuriosStackFrom(ModItems.WHITE_MOON_AMULET.get(), player);

        if (source.is(DamageTypes.FALL) && SteelworksCurioUtils.isCurioEquippedBy(ModItems.WHITE_MOON_AMULET.get(), player)
                && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            event.setCanceled(true);
            player.getCooldowns().addCooldown(stack.getItem(), 1200);
            ItemFlagUtil.removeFlag(stack, WhiteMoonAmuletItem.IS_ACTIVE);
            Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
            if (player.level() instanceof ServerLevel serverLevel) {
                serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ENDER_EYE_DEATH, SoundSource.NEUTRAL, 0.5F, 0.4F / (serverLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            }
        }
    }
}
