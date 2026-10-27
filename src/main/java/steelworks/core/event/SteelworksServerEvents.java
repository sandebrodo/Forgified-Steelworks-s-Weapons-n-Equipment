package steelworks.core.event;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import steelworks.SteelworksWeapons;
import steelworks.core.item.HammerItem;
import steelworks.core.registry.ModItems;

import java.util.*;

@Mod.EventBusSubscriber(modid = SteelworksWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SteelworksServerEvents {


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

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

        if (event.phase != TickEvent.Phase.END) return;

        var player = event.player;
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        ItemStack mainHandItemStack = serverPlayer.getMainHandItem();
        ItemStack offhandItemStack = serverPlayer.getOffhandItem();
        CompoundTag data = serverPlayer.getPersistentData();
        //SteelworksWeapons.LOGGER.debug("IS DAGGERS GIVEN: {}", data.getBoolean("isDaggersGiven"));

        boolean stillHasBothDaggers = mainHandItemStack.is(ModItems.BLOOD_CULT_DAGGER.get()) && offhandItemStack.is(ModItems.BLOOD_CULT_DAGGER.get());

        if (!stillHasBothDaggers && data.getBoolean("isDaggersGiven")) {
            data.putBoolean("isDaggersGiven", false);
            removeBloodCultDaggers(serverPlayer);
            returnEnvelope(serverPlayer);
        }
    }

    static void removeBloodCultDaggers(ServerPlayer player) {

        player.getInventory().clearOrCountMatchingItems(
                stack -> stack.is(ModItems.BLOOD_CULT_DAGGER.get()),
                -1,
                player.getInventory()
        );
    }

    static void returnEnvelope(ServerPlayer player) {
        ItemStack envelope = new ItemStack(ModItems.BLOOD_ENVELOPE.get());
        var items = player.getInventory().items;
        int selectedSlot = player.getInventory().selected;

        boolean placed = false;

        for (int i = 0; i < items.size(); i++) {
            if (i == selectedSlot) continue;
            if (items.get(i).isEmpty()) {
                items.set(i, envelope);
                placed = true;
                break;
            }
        }

        if (!placed) {
            player.drop(envelope, false);
        }
    }

    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        ItemStack stack = event.getEntity().getItem();

        if (stack.is(ModItems.BLOOD_CULT_DAGGER.get())) {
            event.getEntity().discard();
        }
    }
}
