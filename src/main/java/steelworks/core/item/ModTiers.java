package steelworks.core.item;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import steelworks.core.registry.ModItems;

import java.util.function.Supplier;

public enum ModTiers implements Tier {


    COPPER(2, 190, 2f, 1.5f, 10, () -> {return Ingredient.of(Items.COPPER_INGOT);}),
    FORGOTTEN(5, 2500, 10f, 5f, 25, () -> {return Ingredient.of(ModItems.FORGOTTEN_INGOT.get());});

    //параметры идут по порядку
    private final int level; //уровень добычи
    private final int durability; //прочность
    private final float speed; //скорость добычи
    private final float damage; //урон (рассчитывается по формуле 1 + бонус тира + значенение регистрации)
    private final int enchantmentValue; //значение зачаровываемости, чем больше - тем больше шанс на крутые чары в столе
    private final LazyLoadedValue<Ingredient> repairIngredient; //предмет для починки

    ModTiers(int level, int durability, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.durability = durability;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
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
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
