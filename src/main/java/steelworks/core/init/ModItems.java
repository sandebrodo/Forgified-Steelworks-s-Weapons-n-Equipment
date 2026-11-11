package steelworks.core.init;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import steelworks.SteelworksWeapons;
import steelworks.core.datagen.ModItemModelProvider;
import steelworks.core.item.*;
import steelworks.core.util.TooltipUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModItems {

    //TODO: Add retrieval of forgotten items

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SteelworksWeapons.MODID);

    public static List<RegistryObject<? extends Item>> bows = new ArrayList<>();

    private static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> supplier) {
        RegistryObject<T> item = ITEMS.register(name, supplier);
        ModCreativeModeTabs.items.add(item);
        ModItemModelProvider.items.add(item);
        return item;
    }

    private static <T extends Item> RegistryObject<T> registerWeapon(String name, WeaponModelType weaponModelType, Supplier<T> supplier) {
        RegistryObject<T> item = ITEMS.register(name, supplier);
        switch (weaponModelType) {
            case BASIC_WEAPON -> ModItemModelProvider.basic_weapons.add(item);
            case SCALED_WEAPON_32 -> ModItemModelProvider.scaled_weapons_32.put(item, new float[]{0f, 4f, 0.5f});
            case SCALED_WEAPON_16 -> ModItemModelProvider.scaled_weapons_16.add(item);
            case CUSTOM -> ModItemModelProvider.custom_parent_model_weapons.add(item);
            case BOW -> bows.add(item);
        }
        ModCreativeModeTabs.weapons.add(item);
        return item;
    }

    private static <T extends Item> RegistryObject<T> registerWeapon(String name, WeaponModelType weaponModelType, float[] translation, Supplier<T> supplier) {
        RegistryObject<T> item = registerWeapon(name, weaponModelType, supplier);
        if (weaponModelType == WeaponModelType.SCALED_WEAPON_32) {
            ModItemModelProvider.scaled_weapons_32.put(item, translation);
        }
        return item;
    }

    private static <T extends Item> RegistryObject<T> registerWeapon(String name, WeaponModelType weaponModelType, boolean addToCreative, Supplier<T> supplier) {
        RegistryObject<T> item = ITEMS.register(name, supplier);
        switch (weaponModelType) {
            case BASIC_WEAPON -> ModItemModelProvider.basic_weapons.add(item);
            case SCALED_WEAPON_32 -> ModItemModelProvider.scaled_weapons_32.put(item, new float[]{0f, 4f, 0.5f});
            case SCALED_WEAPON_16 -> ModItemModelProvider.scaled_weapons_16.add(item);
            case CUSTOM -> ModItemModelProvider.custom_parent_model_weapons.add(item);
            case BOW -> bows.add(item);
        }

        if (addToCreative) ModCreativeModeTabs.weapons.add(item);

        return item;
    }

    private enum WeaponModelType {
        BASIC_WEAPON,
        SCALED_WEAPON_32,
        SCALED_WEAPON_16,
        BOW,
        CUSTOM;
    }

    public static final RegistryObject<Item> MOON_DUST = registerItem("moon_dust", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MOON_GOLD_INGOT = registerItem("moon_gold_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MOON_PEBBLE = registerItem("moon_pebble", () -> new Item(new Item.Properties()) {
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            TooltipUtil.addHoldShiftTooltip(tooltipComponents);
            if (Screen.hasShiftDown()) {
                tooltipComponents.add(Component.translatable("tooltip.steelworks_weapons.moon_pebble"));
            }
        }
    });

    //Unique ability items
    public static final RegistryObject<Item> BLOOD_ENVELOPE = registerItem("blood_envelope", () -> new BloodEnvelopeItem(new Item.Properties().stacksTo(1)) {
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.translatable("tooltip.steelworks_weapons.blood_envelope"));
        }
    });

    //Melee Weapons
    //Default attack speed
    private static final float DEFAULT_SCYTHE_ATTACK_SPEED = -2.9F;
    private static final float DEFAULT_HAMMER_ATTACK_SPEED = -3.1F;
    private static final float DEFAULT_TRIDENT_ATTACK_SPEED = -2.9F;
    private static final float DEFAULT_BIG_SWORD_ATTACK_SPEED = -3.2F;
    private static final float DEFAULT_BATTLE_AXE_ATTACK_SPEED = -3.0F;
    private static final float DEFAULT_PALADIN_SWORD_ATTACK_SPEED = -3.4F;
    private static final float DEFAULT_DAGGER_ATTACK_SPEED = -2.1F;
    private static final float DEFAULT_GLAIVE_ATTACK_SPEED = -2.6F;
    private static final float DEFAULT_CLUB_ATTACK_SPEED = -3.3F;
    private static final float DEFAULT_CLAYMORE_ATTACK_SPEED = -2.8F;
    private static final float DEFAULT_KATANA_ATTACK_SPEED = -2.3F;

    //Base attack damage
    private static final int SCYTHE_BASE_ATTACK_DAMAGE = 4;
    private static final int HAMMER_BASE_ATTACK_DAMAGE = 3;
    private static final int BIG_SWORD_BASE_ATTACK_DAMAGE = 6;
    private static final int BATTLE_AXE_BASE_ATTACK_DAMAGE = 5;
    private static final int PALADIN_SWORD_BASE_ATTACK_DAMAGE = 8;
    private static final int DAGGER_BASE_ATTACK_DAMAGE = 2;
    private static final int GLAIVE_BASE_ATTACK_DAMAGE = 4;
    private static final int CLUB_BASE_ATTACK_DAMAGE = 6;
    private static final int CLAYMORE_BASE_ATTACK_DAMAGE = 5;
    private static final int KATANA_BASE_ATTACK_DAMAGE = 3;

    public static final RegistryObject<Item> COPPER_SCYTHE = registerWeapon("copper_scythe", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(ModTiers.COPPER, SCYTHE_BASE_ATTACK_DAMAGE, DEFAULT_SCYTHE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> IRON_SCYTHE = registerWeapon("iron_scythe", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(Tiers.IRON, SCYTHE_BASE_ATTACK_DAMAGE, DEFAULT_SCYTHE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_SCYTHE = registerWeapon("golden_scythe", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(Tiers.GOLD, SCYTHE_BASE_ATTACK_DAMAGE, DEFAULT_SCYTHE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_SCYTHE = registerWeapon("diamond_scythe", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(Tiers.DIAMOND, SCYTHE_BASE_ATTACK_DAMAGE, DEFAULT_SCYTHE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_SCYTHE = registerWeapon("netherite_scythe", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(Tiers.NETHERITE, SCYTHE_BASE_ATTACK_DAMAGE, DEFAULT_SCYTHE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> FORGOTTEN_SCYTHE = registerWeapon("forgotten_scythe", WeaponModelType.SCALED_WEAPON_32,() -> new SwordItem(ModTiers.FORGOTTEN, SCYTHE_BASE_ATTACK_DAMAGE, DEFAULT_SCYTHE_ATTACK_SPEED, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_HAMMER = registerWeapon("copper_hammer", WeaponModelType.BASIC_WEAPON, () -> new HammerItem(ModTiers.COPPER, HAMMER_BASE_ATTACK_DAMAGE, DEFAULT_HAMMER_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> IRON_HAMMER = registerWeapon("iron_hammer", WeaponModelType.BASIC_WEAPON, () -> new HammerItem(Tiers.IRON, HAMMER_BASE_ATTACK_DAMAGE, DEFAULT_HAMMER_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_HAMMER = registerWeapon("golden_hammer", WeaponModelType.BASIC_WEAPON, () -> new HammerItem(Tiers.GOLD, HAMMER_BASE_ATTACK_DAMAGE, DEFAULT_HAMMER_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_HAMMER = registerWeapon("diamond_hammer", WeaponModelType.BASIC_WEAPON, () -> new HammerItem(Tiers.DIAMOND, HAMMER_BASE_ATTACK_DAMAGE, DEFAULT_HAMMER_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_HAMMER = registerWeapon("netherite_hammer", WeaponModelType.BASIC_WEAPON, () -> new HammerItem(Tiers.NETHERITE, HAMMER_BASE_ATTACK_DAMAGE, DEFAULT_HAMMER_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> FORGOTTEN_HAMMER = registerWeapon("forgotten_hammer", WeaponModelType.BASIC_WEAPON, () -> new HammerItem(ModTiers.FORGOTTEN, HAMMER_BASE_ATTACK_DAMAGE, DEFAULT_HAMMER_ATTACK_SPEED, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_BIG_SWORD = registerWeapon("copper_big_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SwordItem(ModTiers.COPPER, BIG_SWORD_BASE_ATTACK_DAMAGE, DEFAULT_BIG_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> IRON_BIG_SWORD = registerWeapon("iron_big_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SwordItem(Tiers.IRON, BIG_SWORD_BASE_ATTACK_DAMAGE, DEFAULT_BIG_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_BIG_SWORD = registerWeapon("golden_big_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SwordItem(Tiers.GOLD, BIG_SWORD_BASE_ATTACK_DAMAGE, DEFAULT_BIG_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_BIG_SWORD = registerWeapon("diamond_big_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SwordItem(Tiers.DIAMOND, BIG_SWORD_BASE_ATTACK_DAMAGE, DEFAULT_BIG_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_BIG_SWORD = registerWeapon("netherite_big_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SwordItem(Tiers.NETHERITE, BIG_SWORD_BASE_ATTACK_DAMAGE, DEFAULT_BIG_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> FORGOTTEN_BIG_SWORD = registerWeapon("forgotten_big_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SwordItem(ModTiers.FORGOTTEN, BIG_SWORD_BASE_ATTACK_DAMAGE, DEFAULT_BIG_SWORD_ATTACK_SPEED, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_BATTLE_AXE = registerWeapon("copper_battle_axe", WeaponModelType.SCALED_WEAPON_32, () -> new AxeItem(ModTiers.COPPER, BATTLE_AXE_BASE_ATTACK_DAMAGE, DEFAULT_BATTLE_AXE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> IRON_BATTLE_AXE = registerWeapon("iron_battle_axe", WeaponModelType.SCALED_WEAPON_32, () -> new AxeItem(Tiers.IRON, BATTLE_AXE_BASE_ATTACK_DAMAGE, DEFAULT_BATTLE_AXE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_BATTLE_AXE = registerWeapon("golden_battle_axe", WeaponModelType.SCALED_WEAPON_32, () -> new AxeItem(Tiers.GOLD, BATTLE_AXE_BASE_ATTACK_DAMAGE, DEFAULT_BATTLE_AXE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_BATTLE_AXE = registerWeapon("diamond_battle_axe", WeaponModelType.SCALED_WEAPON_32, () -> new AxeItem(Tiers.DIAMOND, BATTLE_AXE_BASE_ATTACK_DAMAGE, DEFAULT_BATTLE_AXE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_BATTLE_AXE = registerWeapon("netherite_battle_axe", WeaponModelType.SCALED_WEAPON_32, () -> new AxeItem(Tiers.NETHERITE, BATTLE_AXE_BASE_ATTACK_DAMAGE, DEFAULT_BATTLE_AXE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> FORGOTTEN_BATTLE_AXE = registerWeapon("forgotten_battle_axe", WeaponModelType.SCALED_WEAPON_32, () -> new AxeItem(ModTiers.FORGOTTEN, BATTLE_AXE_BASE_ATTACK_DAMAGE, DEFAULT_BATTLE_AXE_ATTACK_SPEED, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_PALADIN_SWORD = registerWeapon("copper_paladin_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SwordItem(ModTiers.COPPER, BATTLE_AXE_BASE_ATTACK_DAMAGE, DEFAULT_PALADIN_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> IRON_PALADIN_SWORD = registerWeapon("iron_paladin_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SwordItem(Tiers.IRON, BATTLE_AXE_BASE_ATTACK_DAMAGE, DEFAULT_PALADIN_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_PALADIN_SWORD = registerWeapon("golden_paladin_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SwordItem(Tiers.GOLD, BATTLE_AXE_BASE_ATTACK_DAMAGE, DEFAULT_PALADIN_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_PALADIN_SWORD = registerWeapon("diamond_paladin_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SwordItem(Tiers.DIAMOND, BATTLE_AXE_BASE_ATTACK_DAMAGE, DEFAULT_PALADIN_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_PALADIN_SWORD = registerWeapon("netherite_paladin_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SwordItem(Tiers.NETHERITE, BATTLE_AXE_BASE_ATTACK_DAMAGE, DEFAULT_PALADIN_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> FORGOTTEN_PALADIN_SWORD = registerWeapon("forgotten_paladin_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SwordItem(ModTiers.FORGOTTEN, BATTLE_AXE_BASE_ATTACK_DAMAGE, DEFAULT_PALADIN_SWORD_ATTACK_SPEED, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_DAGGER = registerWeapon("copper_dagger", WeaponModelType.BASIC_WEAPON, () -> new SwordItem(ModTiers.COPPER, DAGGER_BASE_ATTACK_DAMAGE, DEFAULT_DAGGER_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> IRON_DAGGER = registerWeapon("iron_dagger", WeaponModelType.BASIC_WEAPON, () -> new SwordItem(Tiers.IRON, DAGGER_BASE_ATTACK_DAMAGE, DEFAULT_DAGGER_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_DAGGER = registerWeapon("golden_dagger", WeaponModelType.BASIC_WEAPON, () -> new SwordItem(Tiers.GOLD, DAGGER_BASE_ATTACK_DAMAGE, DEFAULT_DAGGER_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_DAGGER = registerWeapon("diamond_dagger", WeaponModelType.BASIC_WEAPON, () -> new SwordItem(Tiers.DIAMOND, DAGGER_BASE_ATTACK_DAMAGE, DEFAULT_DAGGER_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_DAGGER = registerWeapon("netherite_dagger", WeaponModelType.BASIC_WEAPON, () -> new SwordItem(Tiers.NETHERITE, DAGGER_BASE_ATTACK_DAMAGE, DEFAULT_DAGGER_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> FORGOTTEN_DAGGER = registerWeapon("forgotten_dagger", WeaponModelType.BASIC_WEAPON, () -> new SwordItem(ModTiers.FORGOTTEN, DAGGER_BASE_ATTACK_DAMAGE, DEFAULT_DAGGER_ATTACK_SPEED, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_GLAIVE = registerWeapon("copper_glaive", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(ModTiers.COPPER, GLAIVE_BASE_ATTACK_DAMAGE, DEFAULT_GLAIVE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> IRON_GLAIVE = registerWeapon("iron_glaive", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(Tiers.IRON, GLAIVE_BASE_ATTACK_DAMAGE, DEFAULT_GLAIVE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_GLAIVE = registerWeapon("golden_glaive", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(Tiers.GOLD, GLAIVE_BASE_ATTACK_DAMAGE, DEFAULT_GLAIVE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_GLAIVE = registerWeapon("diamond_glaive", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(Tiers.DIAMOND, GLAIVE_BASE_ATTACK_DAMAGE, DEFAULT_GLAIVE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_GLAIVE = registerWeapon("netherite_glaive", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(Tiers.NETHERITE, GLAIVE_BASE_ATTACK_DAMAGE, DEFAULT_GLAIVE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> FORGOTTEN_GLAIVE = registerWeapon("forgotten_glaive", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(ModTiers.FORGOTTEN, GLAIVE_BASE_ATTACK_DAMAGE, DEFAULT_GLAIVE_ATTACK_SPEED, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_CLUB = registerWeapon("copper_club", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(ModTiers.COPPER, CLUB_BASE_ATTACK_DAMAGE, DEFAULT_CLUB_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> IRON_CLUB = registerWeapon("iron_club", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(Tiers.IRON, CLUB_BASE_ATTACK_DAMAGE, DEFAULT_CLUB_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_CLUB = registerWeapon("golden_club", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(Tiers.GOLD, CLUB_BASE_ATTACK_DAMAGE, DEFAULT_CLUB_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_CLUB = registerWeapon("diamond_club", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(Tiers.DIAMOND, CLUB_BASE_ATTACK_DAMAGE, DEFAULT_CLUB_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_CLUB = registerWeapon("netherite_club", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(Tiers.NETHERITE, CLUB_BASE_ATTACK_DAMAGE, DEFAULT_CLUB_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> FORGOTTEN_CLUB = registerWeapon("forgotten_club", WeaponModelType.SCALED_WEAPON_32, () -> new SwordItem(ModTiers.FORGOTTEN, CLUB_BASE_ATTACK_DAMAGE, DEFAULT_CLUB_ATTACK_SPEED, new Item.Properties()));

    private static final float[] CLAYMORE_TRANSLATION = new float[]{0f, 10f, 0.5f};
    public static final RegistryObject<Item> COPPER_CLAYMORE = registerWeapon("copper_claymore", WeaponModelType.SCALED_WEAPON_32, CLAYMORE_TRANSLATION, () -> new SwordItem(ModTiers.COPPER, CLAYMORE_BASE_ATTACK_DAMAGE, DEFAULT_CLAYMORE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> IRON_CLAYMORE = registerWeapon("iron_claymore", WeaponModelType.SCALED_WEAPON_32, CLAYMORE_TRANSLATION, () -> new SwordItem(Tiers.IRON, CLAYMORE_BASE_ATTACK_DAMAGE, DEFAULT_CLAYMORE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_CLAYMORE = registerWeapon("golden_claymore", WeaponModelType.SCALED_WEAPON_32, CLAYMORE_TRANSLATION, () -> new SwordItem(Tiers.GOLD, CLAYMORE_BASE_ATTACK_DAMAGE, DEFAULT_CLAYMORE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_CLAYMORE = registerWeapon("diamond_claymore", WeaponModelType.SCALED_WEAPON_32, CLAYMORE_TRANSLATION, () -> new SwordItem(Tiers.DIAMOND, CLAYMORE_BASE_ATTACK_DAMAGE, DEFAULT_CLAYMORE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_CLAYMORE = registerWeapon("netherite_claymore", WeaponModelType.SCALED_WEAPON_32, CLAYMORE_TRANSLATION, () -> new SwordItem(Tiers.NETHERITE, CLAYMORE_BASE_ATTACK_DAMAGE, DEFAULT_CLAYMORE_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> FORGOTTEN_CLAYMORE = registerWeapon("forgotten_claymore", WeaponModelType.SCALED_WEAPON_32, CLAYMORE_TRANSLATION, () -> new SwordItem(ModTiers.FORGOTTEN, CLAYMORE_BASE_ATTACK_DAMAGE, DEFAULT_CLAYMORE_ATTACK_SPEED, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_KATANA = registerWeapon("copper_katana", WeaponModelType.CUSTOM, CLAYMORE_TRANSLATION, () -> new SwordItem(ModTiers.COPPER, KATANA_BASE_ATTACK_DAMAGE, DEFAULT_KATANA_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> IRON_KATANA = registerWeapon("iron_katana", WeaponModelType.CUSTOM, CLAYMORE_TRANSLATION, () -> new SwordItem(Tiers.IRON, KATANA_BASE_ATTACK_DAMAGE, DEFAULT_KATANA_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_KATANA = registerWeapon("golden_katana", WeaponModelType.CUSTOM, CLAYMORE_TRANSLATION, () -> new SwordItem(Tiers.GOLD, KATANA_BASE_ATTACK_DAMAGE, DEFAULT_KATANA_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_KATANA = registerWeapon("diamond_katana", WeaponModelType.CUSTOM, CLAYMORE_TRANSLATION, () -> new SwordItem(Tiers.DIAMOND, KATANA_BASE_ATTACK_DAMAGE, DEFAULT_KATANA_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_KATANA = registerWeapon("netherite_katana", WeaponModelType.CUSTOM, CLAYMORE_TRANSLATION, () -> new SwordItem(Tiers.NETHERITE, KATANA_BASE_ATTACK_DAMAGE, DEFAULT_KATANA_ATTACK_SPEED, new Item.Properties()));
    public static final RegistryObject<Item> FORGOTTEN_KATANA = registerWeapon("forgotten_katana", WeaponModelType.CUSTOM, CLAYMORE_TRANSLATION, () -> new SwordItem(ModTiers.FORGOTTEN, KATANA_BASE_ATTACK_DAMAGE, DEFAULT_KATANA_ATTACK_SPEED, new Item.Properties()));


    //Ranged Weapons
    private static final double TIERED_BOW_BASE_ARROW_DAMAGE = 3;
    public static final RegistryObject<Item> COPPER_BOW = registerWeapon("copper_bow", WeaponModelType.BOW, () -> new TieredBowItem(TIERED_BOW_BASE_ARROW_DAMAGE, ModTiers.COPPER, new Item.Properties()));
    public static final RegistryObject<Item> IRON_BOW = registerWeapon("iron_bow", WeaponModelType.BOW, () -> new TieredBowItem(TIERED_BOW_BASE_ARROW_DAMAGE, Tiers.IRON, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_BOW = registerWeapon("golden_bow", WeaponModelType.BOW, () -> new TieredBowItem(TIERED_BOW_BASE_ARROW_DAMAGE, Tiers.GOLD, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_BOW = registerWeapon("diamond_bow", WeaponModelType.BOW, () -> new TieredBowItem(TIERED_BOW_BASE_ARROW_DAMAGE, Tiers.DIAMOND, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_BOW = registerWeapon("netherite_bow", WeaponModelType.BOW, () -> new TieredBowItem(TIERED_BOW_BASE_ARROW_DAMAGE, Tiers.NETHERITE, new Item.Properties()));
    public static final RegistryObject<Item> FORGOTTEN_BOW = registerWeapon("forgotten_bow", WeaponModelType.BOW, () -> new TieredBowItem(TIERED_BOW_BASE_ARROW_DAMAGE, ModTiers.FORGOTTEN, new Item.Properties()));

    //Unique Weapons
    public static final RegistryObject<Item> BLOOD_CULT_DAGGER = registerWeapon("blood_cult_dagger", WeaponModelType.BASIC_WEAPON, false,
            () -> new BloodCultDaggerItem(ModTiers.BLOOD_CULT, 2, DEFAULT_DAGGER_ATTACK_SPEED, new Item.Properties().durability(-1)) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.steelworks_weapons.blood_cult_dagger"));
                }
            });

    //Accessories
    //public static final RegistryObject<Item> COPPER_RING = registerItem("copper_ring", () -> new CopperRingItem(new Item.Properties()));
    public static final RegistryObject<Item> MOON_RING_WITH_DIAMOND = registerItem("moon_ring_with_diamond", () -> new MoonRingItem(new Item.Properties(), MoonRingItem.Type.WITH_DIAMOND));
    public static final RegistryObject<Item> MOON_RING_WITH_EMERALD = registerItem("moon_ring_with_emerald", () -> new MoonRingItem(new Item.Properties(), MoonRingItem.Type.WITH_EMERALD));
    public static final RegistryObject<Item> MOON_OCULARS = registerItem("moon_oculars", () -> new MoonOcularsItem(new Item.Properties()));
    public static final RegistryObject<Item> LUNAR_MAGNET_AMULET = registerItem("lunar_magnet_amulet", () -> new LunarMagnetAmuletItem(new Item.Properties()));
    public static final RegistryObject<Item> WHITE_MOON_AMULET = registerItem("white_moon_amulet", () -> new WhiteMoonAmuletItem(new Item.Properties()));

    //Forgotten
    public static final RegistryObject<Item> FORGOTTEN_INGOT = registerItem("forgotten_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FORGOTTEN_SCRAP = registerItem("forgotten_scrap", () -> new Item(new Item.Properties()) {
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            TooltipUtil.addHoldShiftTooltip(tooltipComponents);
            if (Screen.hasShiftDown()) {
                tooltipComponents.add(Component.translatable("tooltip.steelworks_weapons.forgotten_scrap"));
            }
        }
    });
    public static final RegistryObject<Item> FORGOTTEN_NUGGET = registerItem("forgotten_nugget", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}