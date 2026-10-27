package steelworks.core.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import steelworks.core.api.IAbilityItem;
import steelworks.core.registry.ModParticles;
import steelworks.core.util.TooltipUtil;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class LunarMagnetAmuletItem extends CurioBaseItem implements IAbilityItem {

    private PullItemsWhen pullItems;
    public LunarMagnetAmuletItem(Properties properties) {
        super(properties);
        this.pullItems = PullItemsWhen.ON_PRESS;
    }

    private static final double PULL_RADIUS = 7.0D;
    private static final double PULL_SPEED = 1.8;

    public enum PullItemsWhen {
        EVERY_TICK("every_tick"),
        ON_PRESS("on_press");

        final String name;

        PullItemsWhen(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Component getLocalizedName() {
            return Component.translatable("component.steelworks_weapons.lunar_magnet_amulet_mode_" + name);
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        var entity = slotContext.entity();
        if (entity instanceof Player player) {
            if (this.pullItems == PullItemsWhen.EVERY_TICK) {
                pullItems(player, PULL_RADIUS , PULL_SPEED);
            }
        }
        super.curioTick(slotContext, stack);
    }

    @Override
    public void onKeyPressed(Player player, ItemStack stack) {
        if (player.isCrouching()) {
            switch (pullItems) {
                case EVERY_TICK -> {
                    this.pullItems = PullItemsWhen.ON_PRESS;
                    player.displayClientMessage(Component.translatable("message.steelworks_weapons.lunar_magnet_amulet_switch", this.pullItems.getLocalizedName()), true);
                    return;
                }
                case ON_PRESS -> {
                    this.pullItems = PullItemsWhen.EVERY_TICK;
                    player.displayClientMessage(Component.translatable("message.steelworks_weapons.lunar_magnet_amulet_switch", this.pullItems.getLocalizedName()), true);
                    return;
                }
            }
        }

        if (this.pullItems == PullItemsWhen.ON_PRESS) {
            pullItems(player, PULL_RADIUS, PULL_SPEED);
            createParticlesAroundPlayer(player, 8, 1);
        }
    }

    @Override
    public int getAbilityCooldown() {
        return 20;
    }

    public static void pullItems(Player player, double radius, double speed) {
        Level level = player.level();
        if (level.isClientSide) return;

        AABB area = player.getBoundingBox().inflate(radius);
        Vec3 target = player.position().add(0, player.getBbHeight() / 2.0, 0);

        for (ItemEntity item : level.getEntitiesOfClass(ItemEntity.class, area, entity -> entity.isAlive() && !entity.hasPickUpDelay())) {
            Vec3 toPlayer = target.subtract(item.position());
            double distance = toPlayer.length();

            if (distance < 0.5) continue;

            Vec3 pull = toPlayer.normalize().scale(speed);
            item.setDeltaMovement(item.getDeltaMovement().scale(0.5).add(pull));
            item.hurtMarked = true;
        }
    }

    public static void createParticlesAroundPlayer(Player player, int count, double radius) {

        Level level = player.level();
        if (!(level instanceof ServerLevel serverLevel)) return;
        Vec3 center = player.getBoundingBox().getCenter();

        for (int i = 0; i < count; i++) {

            double angle = (2 * Math.PI / count) * i;
            double x = center.x + radius * Math.cos(angle);
            double z = center.z + radius * Math.sin(angle);

            serverLevel.sendParticles(ModParticles.MOON_ALTAR_PARTICLE_2.get(),
                    x, center.y, z,
                    1,
                    0.0, 0.0, 0.0,
                    0.0);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Component radiusComponent = Component.literal(String.valueOf(PULL_RADIUS)).withStyle(ChatFormatting.LIGHT_PURPLE);
        TooltipUtil.addHoldShiftTooltip(tooltipComponents);
        if (Screen.hasShiftDown()) {
            TooltipUtil.addIndentations(tooltipComponents, 1);
            TooltipUtil.addAbilityTooltip(tooltipComponents);
            TooltipUtil.addAbilityKeybindTooltip(tooltipComponents);
            TooltipUtil.addIndentations(tooltipComponents, 1);
            tooltipComponents.add(Component.translatable("tooltip.steelworks_weapons.lunar_magnet_amulet", radiusComponent, this.pullItems.getLocalizedName()));
        }
    }
}
