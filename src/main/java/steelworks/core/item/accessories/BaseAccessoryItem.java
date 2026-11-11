package steelworks.core.item.accessories;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import steelworks.core.item.CurioBaseItem;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class BaseAccessoryItem extends CurioBaseItem {

    public BaseAccessoryItem(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {

        Multimap<Attribute, AttributeModifier> map = LinkedHashMultimap.create();

        ItemStack gemStack = getGem(stack);

        if (gemStack.getItem() instanceof AccessoryGemItem gemItem) {

            var attributes = gemItem.get().attributes();

            for (AccessoryGem.AccessoryAttribute acsAttr : attributes) {
                map.put(acsAttr.attribute(), acsAttr.toModifier(acsAttr.getName()));
            }
        }

        return map;
    }

    public void addGem(ItemStack stack, ItemStack gemStack) {

        if (gemStack.getItem() instanceof AccessoryGemItem gemItem) {
            stack.getOrCreateTag().putString("gem", gemItem.get().id().toString());
        }

    }

    public static ItemStack getGem(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains("gem", Tag.TAG_COMPOUND)) return ItemStack.EMPTY;
        return ItemStack.of(tag.getCompound("gem"));
    }
}
