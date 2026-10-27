package steelworks.core.block;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.GeckoLib;
import steelworks.core.block.tile.MoonAltarTileEntity;
import steelworks.core.registry.ModTileEntities;
import steelworks.core.util.TooltipUtil;

import java.util.List;
import java.util.function.Consumer;

public class MoonAltarBlock extends MoonPedestalBlock {
    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);


    public MoonAltarBlock(Properties properties) {
        super(properties.lightLevel(state -> state.getValue(LIT) ? 15 : 0).noOcclusion());
        registerDefaultState(defaultBlockState().setValue(LIT, false));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MoonAltarTileEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) return null;
        return createTickerHelper(blockEntityType, ModTileEntities.MOON_ALTAR_TE.get(),
                (level1, pos, state1,  blockEntity) -> blockEntity.tick(level, pos, state));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter blockGetter, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        TooltipUtil.addHoldShiftTooltip(tooltipComponents);
        if (Screen.hasShiftDown()) {
            TooltipUtil.addIndentations(tooltipComponents, 1);
            tooltipComponents.add(Component.translatable("tooltip.steelworks_weapons.moon_altar"));
        }
    }
}
