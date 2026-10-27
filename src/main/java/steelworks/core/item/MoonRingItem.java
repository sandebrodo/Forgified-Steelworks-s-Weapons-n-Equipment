package steelworks.core.item;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

//Todo: Fully rewrite jewelry system. Add jeweller's table.
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
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {

        Multimap<Attribute, AttributeModifier> map = LinkedHashMultimap.create();

        switch (ringType) {
            case WITH_DIAMOND -> map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "moon_ring_with_diamond_movement_speed", 0.01F, AttributeModifier.Operation.ADDITION));
            case WITH_EMERALD -> {
                map.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, "moon_ring_with_emerald_attack_speed", 0.05F, AttributeModifier.Operation.ADDITION));
                map.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(uuid, "moon_ring_with_emerald_entity_reach", 1, AttributeModifier.Operation.ADDITION));
                map.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(uuid, "moon_ring_with_emerald_block_reach", 1, AttributeModifier.Operation.ADDITION));
            }
        }

        return map;
    }
}
