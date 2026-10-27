package steelworks.core.event;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import steelworks.SteelworksWeapons;
import steelworks.core.client.renderer.MoonAltarRenderer;
import steelworks.core.client.renderer.PedestalTileEntityRenderer;
import steelworks.core.registry.ModBlocks;
import steelworks.core.registry.ModItems;
import steelworks.core.registry.ModTileEntities;

@Mod.EventBusSubscriber(modid = SteelworksWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SteelworksClientEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ModItems.bows.forEach(bow -> {
            ItemProperties.register(bow.get(),
                    ResourceLocation.fromNamespaceAndPath("minecraft", "pulling"),
                    (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && ItemStack.isSameItemSameTags(stack, entity.getUseItem()) ? 1.0F : 0.0F);

            ItemProperties.register(bow.get(),
                    ResourceLocation.fromNamespaceAndPath("minecraft", "pull"), (stack, level, entity, seed) -> {
                if (entity == null || !entity.isUsingItem() || !ItemStack.isSameItemSameTags(stack, entity.getUseItem())) {
                    return 0.0F;
                }
                return Math.min((stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F, 1.0F);
            });
        }));
    }

    @SubscribeEvent
    public static void registerTileEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModTileEntities.PEDESTAL_TE.get(), PedestalTileEntityRenderer::new);
        //event.registerBlockEntityRenderer(ModTileEntities.MOON_ALTAR_TE.get(), PedestalTileEntityRenderer::new);
        event.registerBlockEntityRenderer(ModTileEntities.MOON_ALTAR_TE.get(), MoonAltarRenderer::new);
    }
}
