package steelworks.core.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import steelworks.core.block.tile.MoonAltarTileEntity;
import steelworks.core.block.tile.PedestalTileEntity;

import java.util.List;

public class MoonAltarContainer extends SimpleContainer {

    private final Level level;
    private final BlockEntity catalystSource;
    private final List<BlockPos> pedestalPositions;
    private final int SLOT_INDEX = 0;


    public MoonAltarContainer(Level level, BlockEntity catalystSource, List<BlockPos> pedestalPositions, ItemStack[] items) {
        super(items);
        this.level = level;
        this.catalystSource = catalystSource;
        this.pedestalPositions = pedestalPositions;
    }

    @Override
    public ItemStack removeItem(int index, int amount) {

        ItemStack removed = super.removeItem(index, amount);

        BlockPos pos = pedestalPositions.get(index);
        if (level.getBlockEntity(pos) instanceof PedestalTileEntity pedestal) {
            pedestal.inventory.extractItem(SLOT_INDEX, amount, false);
            pedestal.sync();
        }
        return removed;
    }

    public void removeCatalystItem(int amount, boolean simulate) {
        if (catalystSource instanceof MoonAltarTileEntity altarTile) {
            altarTile.inventory.extractItem(SLOT_INDEX, amount, simulate);
        }
    }

    public BlockPos getPedestalPosition(int index) {
        return pedestalPositions.get(index);
    }

    public BlockEntity getCatalystSource() {
        return catalystSource;
    }
}
