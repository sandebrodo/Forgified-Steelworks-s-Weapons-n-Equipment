package steelworks.core.client.model;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;
import steelworks.SteelworksWeapons;
import steelworks.core.block.tile.MoonAltarTileEntity;

public class MoonAltarModel extends GeoModel<MoonAltarTileEntity> {

    @Override
    public ResourceLocation getModelResource(MoonAltarTileEntity altarTile) {
        return ResourceLocation.fromNamespaceAndPath(SteelworksWeapons.MODID, "geo/block/moon_altar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MoonAltarTileEntity altarTile) {
        return ResourceLocation.fromNamespaceAndPath(SteelworksWeapons.MODID, "textures/block/moon_altar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MoonAltarTileEntity altarTile) {
        return ResourceLocation.fromNamespaceAndPath(SteelworksWeapons.MODID, "animations/moon_altar_anim.animation.json");
    }
}
