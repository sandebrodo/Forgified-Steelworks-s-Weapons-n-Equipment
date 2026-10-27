package steelworks.core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import steelworks.SteelworksWeapons;
import steelworks.core.worldgen.ModWorldGen;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModWorldGen::configured)
            .add(Registries.PLACED_FEATURE, ModWorldGen::placed)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModWorldGen::biomeModifiers);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(SteelworksWeapons.MODID));
    }

}
