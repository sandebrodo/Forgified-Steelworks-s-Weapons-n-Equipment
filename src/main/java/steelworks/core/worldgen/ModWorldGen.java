package steelworks.core.worldgen;

import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import steelworks.SteelworksWeapons;
import steelworks.core.registry.ModBlocks;

import java.util.List;

public class ModWorldGen {

    public static final ResourceKey<ConfiguredFeature<?, ?>> MOON_PEBBLE_CONFIGURED =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, SteelworksWeapons.resourceLocation("moon_pebble"));

    public static final ResourceKey<PlacedFeature> MOON_PEBBLE_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, SteelworksWeapons.resourceLocation("moon_pebble"));

    public static final ResourceKey<BiomeModifier> ADD_MOON_PEBBLE =
            ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, SteelworksWeapons.resourceLocation("add_moon_pebble"));

    public static void configured(BootstapContext<ConfiguredFeature<?, ?>> context) {

        BlockState pebble = ModBlocks.MOON_PEBBLES.get().defaultBlockState();

        Holder<PlacedFeature> single = PlacementUtils.filtered(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(pebble)),
                BlockPredicate.allOf(
                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        BlockPredicate.wouldSurvive(pebble, BlockPos.ZERO),
                        BlockPredicate.solid(new Vec3i(0, -1, 0))
                )
        );

        FeatureUtils.register(context, MOON_PEBBLE_CONFIGURED, Feature.RANDOM_PATCH, new RandomPatchConfiguration(4, 3, 1, single));
    }

    public static void placed(BootstapContext<PlacedFeature> context) {

        HolderGetter<ConfiguredFeature<?, ?>> configured = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(MOON_PEBBLE_PLACED, new PlacedFeature(
                configured.getOrThrow(MOON_PEBBLE_CONFIGURED),
                List.of(
                        RarityFilter.onAverageOnceEvery(5),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));
    }

    public static void biomeModifiers(BootstapContext<BiomeModifier> context) {

        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);

        context.register(ADD_MOON_PEBBLE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_FOREST),
                HolderSet.direct(placed.getOrThrow(MOON_PEBBLE_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }
}
