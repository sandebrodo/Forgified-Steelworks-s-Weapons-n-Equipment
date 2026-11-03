package steelworks.core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import steelworks.SteelworksWeapons;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = SteelworksWeapons.MODID)
public class ModDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        var client = event.includeClient();
        var server = event.includeServer();

        generator.addProvider(client, new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(client, new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(server, new ModBlockTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(server, ModLootTableProvider.createBlockLootTables(packOutput, lookupProvider));
        generator.addProvider(server, new ModWorldGenProvider(packOutput, lookupProvider));
    }

}
