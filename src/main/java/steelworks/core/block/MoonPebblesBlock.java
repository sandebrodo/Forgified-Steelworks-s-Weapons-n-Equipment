package steelworks.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import steelworks.core.registry.ModItems;

@SuppressWarnings("deprecation")
public class MoonPebblesBlock extends Block {

    private static final VoxelShape SHAPE = box(3, 0, 3, 14, 4, 14);

    public MoonPebblesBlock(Properties properties) {
        super(properties.sound(SoundType.STONE).noOcclusion().instabreak());
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if (player.getAbilities().instabuild || !player.getMainHandItem().isEmpty()) return InteractionResult.PASS;
        RandomSource random = player.getRandom();
        level.removeBlock(pos, true);

        int count = random.nextInt(1, 3);

        ItemStack stack = new ItemStack(ModItems.MOON_PEBBLE.get(), count);
        ItemEntity entity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
        level.addFreshEntity(entity);

        level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1.0F);
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return Block.canSupportCenter(level, pos.below(), Direction.UP);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState neighbor, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, dir, neighbor, level, pos, neighborPos);
    }
}
