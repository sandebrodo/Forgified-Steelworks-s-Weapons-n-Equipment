package steelworks.core.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import steelworks.core.registry.ModItems;
import net.neoforged.neoforge.common.util.Lazy;

import java.util.Objects;
import java.util.function.Supplier;

public enum ModTiers implements Tier {

    COPPER(BlockTags.INCORRECT_FOR_STONE_TOOL, 2, 190, 2f, 1.5f, 10, () -> Ingredient.of(Items.COPPER_INGOT)),
    FORGOTTEN(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 5, 2500, 10f, 5f, 25, () -> Ingredient.of(ModItems.FORGOTTEN_INGOT.get())),
    BLOOD_CULT(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 5, 2500, 10f, 5f, 25, Ingredient::of);

    //параметры идут по порядку
    private final TagKey<Block> incorrectBlocksForDrops;
    private final int level; //уровень добычи
    private final int durability; //прочность
    private final float speed; //скорость добычи
    private final float damage; //урон (рассчитывается по формуле 1 + бонус тира + значенение регистрации)
    private final int enchantmentValue; //значение зачаровываемости, чем больше - тем больше шанс на крутые чары в столе
    private final Lazy<Ingredient> repairIngredient; //предмет для починки

    ModTiers(TagKey<Block> incorrectBlocksForDrops, int level, int durability, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        this.level = level;
        this.durability = durability;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = Lazy.of(repairIngredient);
    }



    @Override
    public int getUses() {
        return this.durability;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
        return Objects.requireNonNull(this.incorrectBlocksForDrops);
    }

    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }


}
