package steelworks.core.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import steelworks.core.registry.ModItems;

public class BloodEnvelopeItem extends Item {

    public BloodEnvelopeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack mainHandStack = player.getMainHandItem();
        ItemStack offhandStack = player.getOffhandItem();
        CompoundTag data = player.getPersistentData();

        if (mainHandStack.is(this) && offhandStack.isEmpty()) {

            if (!data.getBoolean("isDaggersGiven")) {
                data.putBoolean("isDaggersGiven", true);

                player.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.BLOOD_CULT_DAGGER.get()));
                player.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(ModItems.BLOOD_CULT_DAGGER.get()));
                return InteractionResultHolder.success(mainHandStack);
            }
        }

        return InteractionResultHolder.pass(mainHandStack);
    }
}
