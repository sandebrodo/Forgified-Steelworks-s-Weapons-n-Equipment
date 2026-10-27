package steelworks.core.api;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import steelworks.SteelworksWeapons;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(modid = SteelworksWeapons.MODID)
public class ItemAbilityEventProvider {

    @SubscribeEvent
    public static void onAbility(ItemAbilityEvent event) {
        Player player = event.getPlayer();

        //Todo: Add mainHandItem ability support
        ItemStack mainHandItem = player.getMainHandItem();

        var curiosInventory = CuriosApi.getCuriosInventory(player).resolve();

        if (curiosInventory.isPresent()) {

            var handler = curiosInventory.get().getCurios();
            if (handler != null) {
                handler.forEach((identifier, slotInventory) -> {

                    if (slotInventory != null) {

                        for (int i = 0; i < slotInventory.getSlots(); i++) {

                            ItemStack stack = slotInventory.getStacks().getStackInSlot(i);

                            if (stack.getItem() instanceof IAbilityItem abilityItem) {
                                if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
                                    abilityItem.onKeyPressed(player, stack);
                                    player.getCooldowns().addCooldown(stack.getItem(), abilityItem.getAbilityCooldown());
                                    return;
                                }
                            }
                        }
                    }
                });
            }
        }
    }

}
