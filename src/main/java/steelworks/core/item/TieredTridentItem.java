package steelworks.core.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TridentItem;

public class TieredTridentItem extends TridentItem {

    private final Tier tier;
    private float attackDamage;
    private float attackSpeed;

    public TieredTridentItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(properties.defaultDurability(tier.getUses()));
        this.tier = tier;
        this.attackDamage = (float) attackDamage + tier.getAttackDamageBonus();
        this.attackSpeed = attackSpeed;
    }

    public Tier getTier() {
        return tier;
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    public boolean isValidRepairItem(ItemStack stack, ItemStack repairItem) {
        return this.tier.getRepairIngredient().test(stack) || super.isValidRepairItem(stack, repairItem);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {

        if (slot == EquipmentSlot.MAINHAND) {

            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamage, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeed, AttributeModifier.Operation.ADDITION));

            return builder.build();
        }
        return super.getDefaultAttributeModifiers(slot);
    }
}
