package steelworks.core.item.accessories;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record AccessoryGem(ResourceLocation id, List<AccessoryAttribute> attributes, List<IAccessoryGemHitEffect> hitEffects, List<IAccessoryGemTickEffect> tickEffects, List<IAccessoryDamageTakenEffect> damageTakenEffects) {

    public record AccessoryAttribute(UUID uuid, Attribute attribute, double amount, AttributeModifier.Operation operation) {

        public AttributeModifier toModifier(String name) {
            return new AttributeModifier(uuid, name, amount, operation);
        }

        public String getName() {
            return attribute.getDescriptionId();
        }
    }

    public void applyHit(LivingEntity target, LivingEntity source) {
        for (IAccessoryGemHitEffect effect : hitEffects) {
            effect.apply(target, source);
        }
    }

    public void applyTick(Player player) {
        for (IAccessoryGemTickEffect effect : tickEffects) {
            effect.apply(player);
        }
    }

    public static class Builder {

        private final ResourceLocation id;
        private final List<AccessoryAttribute> attributes = new ArrayList<>();
        private final List<IAccessoryGemHitEffect> hitEffects = new ArrayList<>();
        private final List<IAccessoryGemTickEffect> tickEffects = new ArrayList<>();
        private final List<IAccessoryDamageTakenEffect> damageTakenEffects = new ArrayList<>();

        public Builder(ResourceLocation id) {
            this.id = id;
        }

        public Builder attribute(Attribute attribute, double amount, AttributeModifier.Operation operation) {
            UUID uuid = UUID.nameUUIDFromBytes((attribute.getDescriptionId()).getBytes(StandardCharsets.UTF_8));
            attributes.add(new AccessoryAttribute(uuid, attribute, amount, operation));
            return this;
        }

        public Builder hitEffect(IAccessoryGemHitEffect effect) {
            this.hitEffects.add(effect);
            return this;
        }

        public Builder tickEffect(IAccessoryGemTickEffect effect) {
            this.tickEffects.add(effect);
            return this;
        }

        public Builder damageTakenEffect(IAccessoryDamageTakenEffect effect) {
            this.damageTakenEffects.add(effect);
            return this;
        }

        public AccessoryGem build() {

            if (id == null) {
                throw new IllegalArgumentException("Gem Effect ID is null!");
            }

            return new AccessoryGem(id, List.copyOf(attributes), List.copyOf(hitEffects), List.copyOf(tickEffects), List.copyOf(damageTakenEffects));
        }
    }
}