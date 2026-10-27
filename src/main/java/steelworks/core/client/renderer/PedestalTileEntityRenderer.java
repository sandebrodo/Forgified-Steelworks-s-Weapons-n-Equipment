package steelworks.core.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import steelworks.core.block.tile.PedestalTileEntity;

public class PedestalTileEntityRenderer implements BlockEntityRenderer<PedestalTileEntity> {

    public PedestalTileEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(PedestalTileEntity tileEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        doPedestalRender(tileEntity, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
    }

    public static void doPedestalRender(PedestalTileEntity tileEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = tileEntity.inventory.getStackInSlot(0);

        float offset = tileEntity.getItemRendererOffset();

        poseStack.pushPose();
        poseStack.translate(0.5F, offset, 0.5F);
        poseStack.scale(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(tileEntity.getRenderingRotation()));

        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(tileEntity.getLevel(),
                tileEntity.getBlockPos().above()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, tileEntity.getLevel(), 1);
        poseStack.popPose();
    }

    private static int getLightLevel(Level level, BlockPos pos) {
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(blockLight, skyLight);
    }
}
