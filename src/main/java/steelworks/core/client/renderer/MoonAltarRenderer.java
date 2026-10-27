package steelworks.core.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import steelworks.core.block.tile.MoonAltarTileEntity;
import steelworks.core.client.model.MoonAltarModel;

public class MoonAltarRenderer extends GeoBlockRenderer<MoonAltarTileEntity> {
    public MoonAltarRenderer(BlockEntityRendererProvider.Context context) {
        super(new MoonAltarModel());
    }

    @Override
    public void renderFinal(PoseStack poseStack, MoonAltarTileEntity altar, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        PedestalTileEntityRenderer.doPedestalRender(altar, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
    }
}
