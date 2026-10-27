package steelworks.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import steelworks.core.block.tile.PedestalTileEntity;
import steelworks.core.registry.ModItems;

@SuppressWarnings("deprecation")
public class MoonPedestalBlock extends BaseEntityBlock {

    public MoonPedestalBlock(Properties properties) {
        super(properties.noOcclusion().requiresCorrectToolForDrops());
    }

    public static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 11, 13);

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PedestalTileEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {

        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof PedestalTileEntity pedestalTileEntity) {
                pedestalTileEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {

        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() == ModItems.BLOOD_CULT_DAGGER.get()) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {

            BlockEntity tileEntity = level.getBlockEntity(pos);

            if (tileEntity instanceof PedestalTileEntity stonePedestalTile) {
                if (stonePedestalTile.inventory.getStackInSlot(stonePedestalTile.SLOT_INDEX).isEmpty() && !stack.isEmpty()) {
                    stonePedestalTile.inventory.insertItem(stonePedestalTile.SLOT_INDEX, stack.copy(), false);
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                    level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 2.0F);
                    return InteractionResult.SUCCESS;
                } else if (stack.isEmpty()) {


                    var pedestalStack = stonePedestalTile.inventory.getStackInSlot(stonePedestalTile.SLOT_INDEX);

                    boolean added = player.getInventory().add(pedestalStack);

                    if (!added) {
                        player.drop(pedestalStack, false);
                    }

                    stonePedestalTile.clearContents();
                    level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }
        } else {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
