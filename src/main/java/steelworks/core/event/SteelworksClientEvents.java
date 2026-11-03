package steelworks.core.event;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import steelworks.SteelworksWeapons;
import steelworks.core.client.renderer.MoonAltarTileEntityRenderer;
import steelworks.core.client.renderer.PedestalTileEntityRenderer;
import steelworks.core.registry.ModItems;
import steelworks.core.registry.ModTileEntities;

@EventBusSubscriber(modid = SteelworksWeapons.MODID)
public class SteelworksClientEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ModItems.bows.forEach(bow -> {

            ItemProperties.register(bow.get(), ResourceLocation.fromNamespaceAndPath("minecraft", "pulling"),
                    (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && ItemStack.isSameItemSameComponents(stack, entity.getUseItem()) ? 1.0F : 0.0F);

            ItemProperties.register(bow.get(),
                    ResourceLocation.fromNamespaceAndPath("minecraft", "pull"), (stack, level, entity, seed) -> {
                        if (entity == null || !entity.isUsingItem() || !ItemStack.isSameItemSameComponents(stack, entity.getUseItem())) {
                            return 0.0F;
                        }
                        return Math.min((stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F, 1.0F);
                    });
        }));
    }


    @SubscribeEvent
    public static void registerTileEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModTileEntities.PEDESTAL_TE.get(), PedestalTileEntityRenderer::new);
        event.registerBlockEntityRenderer(ModTileEntities.MOON_ALTAR_TE.get(), MoonAltarTileEntityRenderer::new);
    }

}
