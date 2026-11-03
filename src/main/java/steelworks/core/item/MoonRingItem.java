package steelworks.core.item;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.data.fixes.NeoForgeEntityLegacyAttributesFix;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import top.theillusivec4.curios.api.SlotContext;

public class MoonRingItem extends CurioBaseItem {

    private final Type ringType;
    public MoonRingItem(Properties properties, Type ringType) {
        super(properties);
        this.ringType = ringType;
    }

    public enum Type {
        WITH_DIAMOND(),
        WITH_EMERALD()
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {

        Multimap<Holder<Attribute>, AttributeModifier> map = LinkedHashMultimap.create();

        switch (ringType) {
            case WITH_DIAMOND -> map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(id, 0.01F, AttributeModifier.Operation.ADD_VALUE));
            case WITH_EMERALD -> {
                map.put(Attributes.ATTACK_SPEED, new AttributeModifier(id, 0.05F, AttributeModifier.Operation.ADD_VALUE));
                map.put(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(id, 1, AttributeModifier.Operation.ADD_VALUE));
                map.put(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(id, 1, AttributeModifier.Operation.ADD_VALUE));
            }
        }

        return map;
    }
}
