package steelworks.core.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class SteelworksCurioUtils {
    public static boolean isCurioEquippedBy(Item curio, LivingEntity entity) {
        return CuriosApi.getCuriosInventory(entity)
                .resolve()
                .flatMap(inv -> inv.findFirstCurio(curio))
                .isPresent();
    }

    public static ItemStack getCuriosStackFrom(Item curio, LivingEntity entity) {
        return CuriosApi.getCuriosInventory(entity)
                .resolve()
                .flatMap(inv -> inv.findFirstCurio(curio))
                .map(SlotResult::stack)
                .orElse(ItemStack.EMPTY);
    }
}
