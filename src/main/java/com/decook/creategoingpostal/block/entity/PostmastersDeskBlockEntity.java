package com.decook.creategoingpostal.block.entity;

import javax.annotation.Nullable;

import com.decook.creategoingpostal.screen.custom.PostmastersDeskPostMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class PostmastersDeskBlockEntity extends BlockEntity implements MenuProvider{

    public final ItemStackHandler post = new ItemStackHandler(28) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack){
            return super.getStackLimit(slot, stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public final ItemStackHandler profit = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack){
            return 512;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public PostmastersDeskBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.POSTMASTERSDESK_BE.get(), pos, blockState);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(post.getSlots());
        for(int i = 0; i < post.getSlots(); i++) {
            inv.setItem(i, post.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("post", post.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        post.deserializeNBT(registries, tag.getCompound("post"));
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int containerId, Inventory inv, Player player) {
        return new PostmastersDeskPostMenu(1, inv, this);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Postmaster's Desk");
    }
 
    
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
}
