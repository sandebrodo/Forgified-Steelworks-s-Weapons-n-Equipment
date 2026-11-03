package steelworks.core.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import steelworks.core.block.tile.MoonAltarTileEntity;
import steelworks.core.client.model.MoonAltarModel;

public class MoonAltarTileEntityRenderer extends GeoBlockRenderer<MoonAltarTileEntity> {

    public MoonAltarTileEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new MoonAltarModel());
    }

    @Override
    public void renderFinal(PoseStack poseStack, MoonAltarTileEntity altarTile, BakedGeoModel model, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, int colour) {
        PedestalTileEntityRenderer.doPedestalRender(altarTile, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
    }
}
