package steelworks.core.event;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import steelworks.SteelworksWeapons;
import steelworks.core.item.ItemEngraveManager;

//WIP
@Mod.EventBusSubscriber(modid = SteelworksWeapons.MODID)
public class EngraveAttributeHandler {

    private static final ItemEngraveManager MANAGER = new ItemEngraveManager();

    @SubscribeEvent
    public static void onAttributeModifiers(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        EquipmentSlot slot = event.getSlotType();

        if (slot != EquipmentSlot.MAINHAND) return;

        ItemEngraveManager.Engraves engrave = MANAGER.getEngrave(stack);
        if (engrave == null) return;

        for (ItemEngraveManager.Engraves.EngraveAttribute bonus : engrave.getAttributes()) {
            Attribute attribute = bonus.attribute();

            AttributeModifier existingModifier = event.getOriginalModifiers().get(attribute).stream()
                    .findFirst().orElse(null);

            if (existingModifier != null) {
                event.removeModifier(attribute, existingModifier);
                event.addModifier(attribute, new AttributeModifier(
                        existingModifier.getId(),
                        existingModifier.getName(),
                        existingModifier.getAmount() + bonus.amount(),
                        existingModifier.getOperation()
                ));
            } else {
                event.addModifier(attribute, bonus.toModifier("Engrave modifier: " + engrave.name()));
            }
        }
    }
}
