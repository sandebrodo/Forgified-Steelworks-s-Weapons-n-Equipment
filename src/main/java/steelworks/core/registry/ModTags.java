package steelworks.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import steelworks.SteelworksWeapons;

public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> MINEABLE_WITH_HAMMER = createBlockTag("mineable_with_hammer");
    }

    public static TagKey<Block> createBlockTag(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(SteelworksWeapons.MODID, name));
    }
}
