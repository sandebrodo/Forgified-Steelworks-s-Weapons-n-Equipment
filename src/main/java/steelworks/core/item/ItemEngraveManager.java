package steelworks.core.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import java.util.List;
import java.util.UUID;

public class ItemEngraveManager implements IEngraveHitEffect {

    private static final String ENGRAVE_TAG = "engrave_type";

    private static final String SWORD_ENGRAVE_TYPE = "sword";
    private static final String SCYTHE_ENGRAVE_TYPE = "scythe";

    public void addEngrave(ItemStack stack, Engraves engrave) {
        CompoundTag tag = stack.getOrCreateTag();
        Item item = stack.getItem();
        String engraveType = engrave.getEngraveType();

        if (item instanceof SwordItem) {
            if (!engraveType.equals(SWORD_ENGRAVE_TYPE)) return;
            tag.putString(ENGRAVE_TAG, engrave.name());
            return;
        }

        if (item instanceof ScytheItem) {
            if (!engraveType.equals(SCYTHE_ENGRAVE_TYPE)) return;
            tag.putString(ENGRAVE_TAG, engrave.name());
            return;
        }

        if (item instanceof DiggerItem) {
            if (!engraveType.equals(SCYTHE_ENGRAVE_TYPE)) return;
            tag.putString(ENGRAVE_TAG, engrave.name());
        }
    }

    public enum Engraves {

        SHARP("sword", List.of(
                new EngraveAttribute(Attributes.ATTACK_DAMAGE, UUID.fromString(UUID.randomUUID().toString()), 2.0, AttributeModifier.Operation.ADDITION),
                new EngraveAttribute(Attributes.ATTACK_SPEED, UUID.fromString(UUID.randomUUID().toString()), 2.0, AttributeModifier.Operation.ADDITION)
        ), null),
        VENOMOUS("sword", List.of(), ((stack, target, attacker) ->
                target.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0))
                )
        );


        final String engraveType;
        final List<EngraveAttribute> attributes;
        final IEngraveHitEffect hitEffect;

        Engraves(String engraveType, List<EngraveAttribute> attributes, IEngraveHitEffect hitEffect) {
            this.engraveType = engraveType;
            this.attributes = attributes;
            this.hitEffect = hitEffect;
        }

        public void applyHitEffect(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (hitEffect != null) {
                hitEffect.onHit(stack, target, attacker);
            }
        }

        public List<EngraveAttribute> getAttributes() {
            return attributes;
        }

        public String getEngraveType() {
            return engraveType;
        }

        public record EngraveAttribute (Attribute attribute, UUID uuid, double amount, AttributeModifier.Operation operation) {
            public AttributeModifier toModifier(String name) {
                return new AttributeModifier(uuid, name, amount, operation);
            }

            public String getAttributeName () {
                return attribute.getDescriptionId();
            }
        }
    }

    public Engraves getEngrave(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains(ENGRAVE_TAG)) return null;

        try {
            return Engraves.valueOf(tag.getString(ENGRAVE_TAG));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public boolean hasEngrave(ItemStack stack) {
        return getEngrave(stack) != null;
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Engraves engrave = getEngrave(stack);

        if (engrave != null) {
            engrave.applyHitEffect(stack, target, attacker);
        }
    }
}
