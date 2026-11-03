package steelworks.core.registry;


import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
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

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SteelworksWeapons.MODID);

    public static List<Supplier<? extends Item>> bows = new ArrayList<>();

    private static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> supplier) {
        Supplier<T> item = ITEMS.register(name, supplier);
        ModCreativeModeTabs.items.add(item);
        ModItemModelProvider.items.add(item);
        return item;
    }

    private static <T extends Item> Supplier<T> registerWeapon(String name, WeaponModelType weaponModelType, Supplier<T> supplier) {
        Supplier<T> item = ITEMS.register(name, supplier);
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

    private static <T extends Item> Supplier<T> registerWeapon(String name, WeaponModelType weaponModelType, float[] translation, Supplier<T> supplier) {
        Supplier<T> item = registerWeapon(name, weaponModelType, supplier);
        if (weaponModelType == WeaponModelType.SCALED_WEAPON_32) {
            ModItemModelProvider.scaled_weapons_32.put(item, translation);
        }
        return item;
    }

    private static <T extends Item> Supplier<T> registerWeapon(String name, WeaponModelType weaponModelType, boolean addToCreative, Supplier<T> supplier) {
        Supplier<T> item = ITEMS.register(name, supplier);
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

    public static final Supplier<Item> MOON_DUST = registerItem("moon_dust", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> MOON_GOLD_INGOT = registerItem("moon_gold_ingot", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> MOON_PEBBLE = registerItem("moon_pebble", () -> new Item(new Item.Properties()) {
        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            TooltipUtil.addHoldShiftTooltip(tooltipComponents);
            if (Screen.hasShiftDown()) {
                tooltipComponents.add(Component.translatable("tooltip.steelworks_weapons.moon_pebble"));
            }
        }
    });

    //Unique ability items
    public static final Supplier<Item> BLOOD_ENVELOPE = registerItem("blood_envelope", () -> new BloodEnvelopeItem(new Item.Properties().stacksTo(1)) {
        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            tooltipComponents.add(Component.translatable("tooltip.steelworks_weapons.blood_envelope"));
        }
    });

    //Melee Weapons
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

    public static final Supplier<Item> COPPER_SCYTHE = registerWeapon("copper_scythe", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(ModTiers.COPPER, 4, DEFAULT_SCYTHE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> IRON_SCYTHE = registerWeapon("iron_scythe", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(Tiers.IRON, 4, DEFAULT_SCYTHE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> GOLDEN_SCYTHE = registerWeapon("golden_scythe", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(Tiers.GOLD, 4, DEFAULT_SCYTHE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> DIAMOND_SCYTHE = registerWeapon("diamond_scythe", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(Tiers.DIAMOND, 4, DEFAULT_SCYTHE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> NETHERITE_SCYTHE = registerWeapon("netherite_scythe", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(Tiers.NETHERITE, 4, DEFAULT_SCYTHE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> FORGOTTEN_SCYTHE = registerWeapon("forgotten_scythe", WeaponModelType.SCALED_WEAPON_32,() -> new SteelworksSwordItem(ModTiers.FORGOTTEN, 4, DEFAULT_SCYTHE_ATTACK_SPEED, new Item.Properties()));

    public static final Supplier<Item> COPPER_HAMMER = registerWeapon("copper_hammer", WeaponModelType.BASIC_WEAPON, () -> new HammerItem(ModTiers.COPPER, 3, DEFAULT_HAMMER_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> IRON_HAMMER = registerWeapon("iron_hammer", WeaponModelType.BASIC_WEAPON, () -> new HammerItem(Tiers.IRON, 3, DEFAULT_HAMMER_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> GOLDEN_HAMMER = registerWeapon("golden_hammer", WeaponModelType.BASIC_WEAPON, () -> new HammerItem(Tiers.GOLD, 3, DEFAULT_HAMMER_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> DIAMOND_HAMMER = registerWeapon("diamond_hammer", WeaponModelType.BASIC_WEAPON, () -> new HammerItem(Tiers.DIAMOND, 3, DEFAULT_HAMMER_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> NETHERITE_HAMMER = registerWeapon("netherite_hammer", WeaponModelType.BASIC_WEAPON, () -> new HammerItem(Tiers.NETHERITE, 3, DEFAULT_HAMMER_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> FORGOTTEN_HAMMER = registerWeapon("forgotten_hammer", WeaponModelType.BASIC_WEAPON, () -> new HammerItem(ModTiers.FORGOTTEN, 3, DEFAULT_HAMMER_ATTACK_SPEED, new Item.Properties()));

//   TODO: Add thrown trident projectile models (DEPRECATED, MARKED FOR REMOVE)
//    public static final RegistryObject<Item> COPPER_TRIDENT = registerWeapon("copper_trident", WeaponModelType.CUSTOM, () -> new TieredTridentItem(ModTiers.COPPER, 3, DEFAULT_TRIDENT_ATTACK_SPEED, new Item.Properties()));
//    public static final RegistryObject<Item> IRON_TRIDENT = registerWeapon("iron_trident", WeaponModelType.CUSTOM, () -> new TieredTridentItem(Tiers.IRON, 3, DEFAULT_TRIDENT_ATTACK_SPEED, new Item.Properties()));
//    public static final RegistryObject<Item> GOLDEN_TRIDENT = registerWeapon("golden_trident", WeaponModelType.CUSTOM, () -> new TieredTridentItem(Tiers.GOLD, 3, DEFAULT_TRIDENT_ATTACK_SPEED, new Item.Properties()));
//    public static final RegistryObject<Item> DIAMOND_TRIDENT = registerWeapon("diamond_trident", WeaponModelType.CUSTOM, () -> new TieredTridentItem(Tiers.DIAMOND, 3, DEFAULT_TRIDENT_ATTACK_SPEED, new Item.Properties()));
//    public static final RegistryObject<Item> NETHERITE_TRIDENT = registerWeapon("netherite_trident", WeaponModelType.CUSTOM, () -> new TieredTridentItem(Tiers.NETHERITE, 3, DEFAULT_TRIDENT_ATTACK_SPEED, new Item.Properties()));
//    public static final RegistryObject<Item> FORGOTTEN_TRIDENT = registerWeapon("forgotten_trident", WeaponModelType.CUSTOM, () -> new TieredTridentItem(ModTiers.FORGOTTEN, 3, DEFAULT_TRIDENT_ATTACK_SPEED, new Item.Properties()));

    public static final Supplier<Item> COPPER_BIG_SWORD = registerWeapon("copper_big_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SteelworksSwordItem(ModTiers.COPPER, 6, DEFAULT_BIG_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> IRON_BIG_SWORD = registerWeapon("iron_big_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SteelworksSwordItem(Tiers.IRON, 6, DEFAULT_BIG_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> GOLDEN_BIG_SWORD = registerWeapon("golden_big_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SteelworksSwordItem(Tiers.GOLD, 6, DEFAULT_BIG_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> DIAMOND_BIG_SWORD = registerWeapon("diamond_big_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SteelworksSwordItem(Tiers.DIAMOND, 6, DEFAULT_BIG_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> NETHERITE_BIG_SWORD = registerWeapon("netherite_big_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SteelworksSwordItem(Tiers.NETHERITE, 6, DEFAULT_BIG_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> FORGOTTEN_BIG_SWORD = registerWeapon("forgotten_big_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SteelworksSwordItem(ModTiers.FORGOTTEN, 6, DEFAULT_BIG_SWORD_ATTACK_SPEED, new Item.Properties()));

    public static final Supplier<Item> COPPER_BATTLE_AXE = registerWeapon("copper_battle_axe", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksAxeItem(   ModTiers.COPPER, 4, DEFAULT_BATTLE_AXE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> IRON_BATTLE_AXE = registerWeapon("iron_battle_axe", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksAxeItem(Tiers.IRON, 4, DEFAULT_BATTLE_AXE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> GOLDEN_BATTLE_AXE = registerWeapon("golden_battle_axe", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksAxeItem(Tiers.GOLD, 4, DEFAULT_BATTLE_AXE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> DIAMOND_BATTLE_AXE = registerWeapon("diamond_battle_axe", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksAxeItem(Tiers.DIAMOND, 4, DEFAULT_BATTLE_AXE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> NETHERITE_BATTLE_AXE = registerWeapon("netherite_battle_axe", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksAxeItem(Tiers.NETHERITE, 4, DEFAULT_BATTLE_AXE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> FORGOTTEN_BATTLE_AXE = registerWeapon("forgotten_battle_axe", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksAxeItem(ModTiers.FORGOTTEN, 4, DEFAULT_BATTLE_AXE_ATTACK_SPEED, new Item.Properties()));

    public static final Supplier<Item> COPPER_PALADIN_SWORD = registerWeapon("copper_paladin_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SteelworksSwordItem(ModTiers.COPPER, 8, DEFAULT_PALADIN_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> IRON_PALADIN_SWORD = registerWeapon("iron_paladin_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SteelworksSwordItem(Tiers.IRON, 8, DEFAULT_PALADIN_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> GOLDEN_PALADIN_SWORD = registerWeapon("golden_paladin_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SteelworksSwordItem(Tiers.GOLD, 8, DEFAULT_PALADIN_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> DIAMOND_PALADIN_SWORD = registerWeapon("diamond_paladin_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SteelworksSwordItem(Tiers.DIAMOND, 8, DEFAULT_PALADIN_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> NETHERITE_PALADIN_SWORD = registerWeapon("netherite_paladin_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SteelworksSwordItem(Tiers.NETHERITE, 8, DEFAULT_PALADIN_SWORD_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> FORGOTTEN_PALADIN_SWORD = registerWeapon("forgotten_paladin_sword", WeaponModelType.SCALED_WEAPON_16, () -> new SteelworksSwordItem(ModTiers.FORGOTTEN, 8, DEFAULT_PALADIN_SWORD_ATTACK_SPEED, new Item.Properties()));

    public static final Supplier<Item> COPPER_DAGGER = registerWeapon("copper_dagger", WeaponModelType.BASIC_WEAPON, () -> new SteelworksSwordItem(ModTiers.COPPER, 2, DEFAULT_DAGGER_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> IRON_DAGGER = registerWeapon("iron_dagger", WeaponModelType.BASIC_WEAPON, () -> new SteelworksSwordItem(Tiers.IRON, 2, DEFAULT_DAGGER_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> GOLDEN_DAGGER = registerWeapon("golden_dagger", WeaponModelType.BASIC_WEAPON, () -> new SteelworksSwordItem(Tiers.GOLD, 2, DEFAULT_DAGGER_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> DIAMOND_DAGGER = registerWeapon("diamond_dagger", WeaponModelType.BASIC_WEAPON, () -> new SteelworksSwordItem(Tiers.DIAMOND, 2, DEFAULT_DAGGER_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> NETHERITE_DAGGER = registerWeapon("netherite_dagger", WeaponModelType.BASIC_WEAPON, () -> new SteelworksSwordItem(Tiers.NETHERITE, 2, DEFAULT_DAGGER_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> FORGOTTEN_DAGGER = registerWeapon("forgotten_dagger", WeaponModelType.BASIC_WEAPON, () -> new SteelworksSwordItem(ModTiers.FORGOTTEN, 2, DEFAULT_DAGGER_ATTACK_SPEED, new Item.Properties()));

    public static final Supplier<Item> COPPER_GLAIVE = registerWeapon("copper_glaive", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(ModTiers.COPPER, 4, DEFAULT_GLAIVE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> IRON_GLAIVE = registerWeapon("iron_glaive", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(Tiers.IRON, 4, DEFAULT_GLAIVE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> GOLDEN_GLAIVE = registerWeapon("golden_glaive", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(Tiers.GOLD, 4, DEFAULT_GLAIVE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> DIAMOND_GLAIVE = registerWeapon("diamond_glaive", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(Tiers.DIAMOND, 4, DEFAULT_GLAIVE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> NETHERITE_GLAIVE = registerWeapon("netherite_glaive", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(Tiers.NETHERITE, 4, DEFAULT_GLAIVE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> FORGOTTEN_GLAIVE = registerWeapon("forgotten_glaive", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(ModTiers.FORGOTTEN, 4, DEFAULT_GLAIVE_ATTACK_SPEED, new Item.Properties()));

    public static final Supplier<Item> COPPER_CLUB = registerWeapon("copper_club", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(ModTiers.COPPER, 6, DEFAULT_CLUB_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> IRON_CLUB = registerWeapon("iron_club", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(Tiers.IRON, 6, DEFAULT_CLUB_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> GOLDEN_CLUB = registerWeapon("golden_club", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(Tiers.GOLD, 6, DEFAULT_CLUB_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> DIAMOND_CLUB = registerWeapon("diamond_club", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(Tiers.DIAMOND, 6, DEFAULT_CLUB_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> NETHERITE_CLUB = registerWeapon("netherite_club", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(Tiers.NETHERITE, 6, DEFAULT_CLUB_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> FORGOTTEN_CLUB = registerWeapon("forgotten_club", WeaponModelType.SCALED_WEAPON_32, () -> new SteelworksSwordItem(ModTiers.FORGOTTEN, 6, DEFAULT_CLUB_ATTACK_SPEED, new Item.Properties()));

    private static final float[] CLAYMORE_TRANSLATION = new float[]{0f, 10f, 0.5f};
    public static final Supplier<Item> COPPER_CLAYMORE = registerWeapon("copper_claymore", WeaponModelType.SCALED_WEAPON_32, CLAYMORE_TRANSLATION, () -> new SteelworksSwordItem(ModTiers.COPPER, 5, DEFAULT_CLAYMORE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> IRON_CLAYMORE = registerWeapon("iron_claymore", WeaponModelType.SCALED_WEAPON_32, CLAYMORE_TRANSLATION, () -> new SteelworksSwordItem(Tiers.IRON, 5, DEFAULT_CLAYMORE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> GOLDEN_CLAYMORE = registerWeapon("golden_claymore", WeaponModelType.SCALED_WEAPON_32, CLAYMORE_TRANSLATION, () -> new SteelworksSwordItem(Tiers.GOLD, 5, DEFAULT_CLAYMORE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> DIAMOND_CLAYMORE = registerWeapon("diamond_claymore", WeaponModelType.SCALED_WEAPON_32, CLAYMORE_TRANSLATION, () -> new SteelworksSwordItem(Tiers.DIAMOND, 5, DEFAULT_CLAYMORE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> NETHERITE_CLAYMORE = registerWeapon("netherite_claymore", WeaponModelType.SCALED_WEAPON_32, CLAYMORE_TRANSLATION, () -> new SteelworksSwordItem(Tiers.NETHERITE, 5, DEFAULT_CLAYMORE_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> FORGOTTEN_CLAYMORE = registerWeapon("forgotten_claymore", WeaponModelType.SCALED_WEAPON_32, CLAYMORE_TRANSLATION, () -> new SteelworksSwordItem(ModTiers.FORGOTTEN, 5, DEFAULT_CLAYMORE_ATTACK_SPEED, new Item.Properties()));

    public static final Supplier<Item> COPPER_KATANA = registerWeapon("copper_katana", WeaponModelType.CUSTOM, CLAYMORE_TRANSLATION, () -> new SteelworksSwordItem(ModTiers.COPPER, 5, DEFAULT_KATANA_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> IRON_KATANA = registerWeapon("iron_katana", WeaponModelType.CUSTOM, CLAYMORE_TRANSLATION, () -> new SteelworksSwordItem(Tiers.IRON, 5, DEFAULT_KATANA_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> GOLDEN_KATANA = registerWeapon("golden_katana", WeaponModelType.CUSTOM, CLAYMORE_TRANSLATION, () -> new SteelworksSwordItem(Tiers.GOLD, 5, DEFAULT_KATANA_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> DIAMOND_KATANA = registerWeapon("diamond_katana", WeaponModelType.CUSTOM, CLAYMORE_TRANSLATION, () -> new SteelworksSwordItem(Tiers.DIAMOND, 5, DEFAULT_KATANA_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> NETHERITE_KATANA = registerWeapon("netherite_katana", WeaponModelType.CUSTOM, CLAYMORE_TRANSLATION, () -> new SteelworksSwordItem(Tiers.NETHERITE, 5, DEFAULT_KATANA_ATTACK_SPEED, new Item.Properties()));
    public static final Supplier<Item> FORGOTTEN_KATANA = registerWeapon("forgotten_katana", WeaponModelType.CUSTOM, CLAYMORE_TRANSLATION, () -> new SteelworksSwordItem(ModTiers.FORGOTTEN, 5, DEFAULT_KATANA_ATTACK_SPEED, new Item.Properties()));


    //Ranged Weapons
    public static final Supplier<Item> COPPER_BOW = registerWeapon("copper_bow", WeaponModelType.BOW, () -> new TieredBowItem(3, ModTiers.COPPER, new Item.Properties()));
    public static final Supplier<Item> IRON_BOW = registerWeapon("iron_bow", WeaponModelType.BOW, () -> new TieredBowItem(4, Tiers.IRON, new Item.Properties()));
    public static final Supplier<Item> GOLDEN_BOW = registerWeapon("golden_bow", WeaponModelType.BOW, () -> new TieredBowItem(3, Tiers.GOLD, new Item.Properties()));
    public static final Supplier<Item> DIAMOND_BOW = registerWeapon("diamond_bow", WeaponModelType.BOW, () -> new TieredBowItem(4.5, Tiers.DIAMOND, new Item.Properties()));
    public static final Supplier<Item> NETHERITE_BOW = registerWeapon("netherite_bow", WeaponModelType.BOW, () -> new TieredBowItem(5, Tiers.NETHERITE, new Item.Properties()));
    public static final Supplier<Item> FORGOTTEN_BOW = registerWeapon("forgotten_bow", WeaponModelType.BOW, () -> new TieredBowItem(5.5, ModTiers.FORGOTTEN, new Item.Properties()));

    //Unique Weapons
    public static final Supplier<Item> BLOOD_CULT_DAGGER = registerWeapon("blood_cult_dagger", WeaponModelType.BASIC_WEAPON, false,
            () -> new BloodCultDaggerItem(ModTiers.BLOOD_CULT, 2, DEFAULT_DAGGER_ATTACK_SPEED, new Item.Properties().durability(-1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.steelworks_weapons.blood_cult_dagger"));
                }
            });

    //Accessories
    //public static final RegistryObject<Item> COPPER_RING = registerItem("copper_ring", () -> new CopperRingItem(new Item.Properties()));
    public static final Supplier<Item> MOON_RING_WITH_DIAMOND = registerItem("moon_ring_with_diamond", () -> new MoonRingItem(new Item.Properties(), MoonRingItem.Type.WITH_DIAMOND));
    public static final Supplier<Item> MOON_RING_WITH_EMERALD = registerItem("moon_ring_with_emerald", () -> new MoonRingItem(new Item.Properties(), MoonRingItem.Type.WITH_EMERALD));
    public static final Supplier<Item> MOON_OCULARS = registerItem("moon_oculars", () -> new MoonOcularsItem(new Item.Properties()));
    public static final Supplier<Item> LUNAR_MAGNET_AMULET = registerItem("lunar_magnet_amulet", () -> new LunarMagnetAmuletItem(new Item.Properties()));
    public static final Supplier<Item> WHITE_MOON_AMULET = registerItem("white_moon_amulet", () -> new WhiteMoonAmuletItem(new Item.Properties()));

    //Forgotten
    public static final Supplier<Item> FORGOTTEN_INGOT = registerItem("forgotten_ingot", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> FORGOTTEN_SCRAP = registerItem("forgotten_scrap", () -> new Item(new Item.Properties()) {
        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            TooltipUtil.addHoldShiftTooltip(tooltipComponents);
            if (Screen.hasShiftDown()) {
                tooltipComponents.add(Component.translatable("tooltip.steelworks_weapons.forgotten_scrap"));
            }
        }
    });
    public static final Supplier<Item> FORGOTTEN_NUGGET = registerItem("forgotten_nugget", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}