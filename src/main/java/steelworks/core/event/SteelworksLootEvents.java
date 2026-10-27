package steelworks.core.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import steelworks.SteelworksWeapons;
import steelworks.core.registry.ModItems;

@Mod.EventBusSubscriber(modid = SteelworksWeapons.MODID)
public class SteelworksLootEvents {

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {

        ResourceLocation id = event.getName();

        if (id.equals(BuiltInLootTables.NETHER_BRIDGE)) {
            event.getTable().addPool(buildPool(ModItems.FORGOTTEN_SCRAP.get(), 2, 0.01f));
        }

        if (id.equals(BuiltInLootTables.ANCIENT_CITY)) {
            event.getTable().addPool(buildPool(ModItems.FORGOTTEN_SCRAP.get(), 3, 0.0035F));
        }

    }

    static LootPool buildPool(Item item, int maxCount, float chance) {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(chance))
                .add(LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, maxCount))))
                .build();
    }
}
