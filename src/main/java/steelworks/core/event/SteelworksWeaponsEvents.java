package steelworks.core.event;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import steelworks.SteelworksWeapons;
import steelworks.core.item.HammerItem;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = SteelworksWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SteelworksWeaponsEvents {


    public static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if(mainHandItem.getItem() instanceof HammerItem hammerItem && player instanceof ServerPlayer serverPlayer) {
            BlockPos blockPos = event.getPos();

            if(HARVESTED_BLOCKS.contains(blockPos)) {
                return;
            }

            if (!player.isCreative()) {
                mainHandItem.hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }

            for (BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, blockPos, serverPlayer)) {
                if(pos == blockPos || !hammerItem.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }

                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }

}
