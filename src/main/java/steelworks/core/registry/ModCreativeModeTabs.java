package steelworks.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import steelworks.SteelworksWeapons;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SteelworksWeapons.MODID);

    public static List<Supplier<? extends Item>> items = new ArrayList<>();
    public static List<Supplier<? extends Block>> blocks = new ArrayList<>();
    public static List<Supplier<? extends Item>> weapons = new ArrayList<>();

    public static final Supplier<CreativeModeTab> STEELWORKS_WEAPONS_TAB = CREATIVE_MODE_TABS.register("steelworks_weapons_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.COPPER_SCYTHE.get()))
                    .title(Component.translatable("creative_tab.steelworks_weapons.weapons_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        items.forEach(item -> output.accept(item.get()));
                        blocks.forEach(block -> output.accept(block.get()));
                        weapons.forEach(weapon -> output.accept(weapon.get()));
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
