package steelworks.core.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import steelworks.core.registry.ModTileEntities;

public class PedestalTileEntity extends BlockEntity {

    public final int SLOT_INDEX = 0;
    private float itemRendererOffset = 1.0F;

    public final ItemStackHandler inventory = new ItemStackHandler(1) {

        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {

            if (level == null) return;

            setChanged();
            if (!level.isClientSide) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    protected PedestalTileEntity(BlockEntityType tile, BlockPos pos, BlockState state) {
        super(tile, pos, state);
    }

    public PedestalTileEntity(BlockPos pos, BlockState state) {
       this(ModTileEntities.PEDESTAL_TE.get(), pos, state);
    }

    private float renderingRotation;

    public float getRenderingRotation() {
        renderingRotation += 0.25F;

        if (renderingRotation >= 360) {
            renderingRotation = 0;
        }
        return renderingRotation;
    }

    public void clearContents() {
        inventory.setStackInSlot(SLOT_INDEX, ItemStack.EMPTY);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }
        if (this.level == null) return;

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", inventory.serializeNBT());
        tag.putFloat("translation", this.itemRendererOffset);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        inventory.deserializeNBT(tag.getCompound("inventory"));
        itemRendererOffset = tag.getFloat("translation");
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void setItemRendererOffset(float offset) {
        this.itemRendererOffset = offset;
    }

    public float getItemRendererOffset() {
        return this.itemRendererOffset;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    public void sync() {
        if (level != null && !level.isClientSide) {
            this.setChanged();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }
}
