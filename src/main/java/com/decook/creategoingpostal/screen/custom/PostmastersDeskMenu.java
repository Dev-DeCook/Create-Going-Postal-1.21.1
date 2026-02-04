package com.decook.creategoingpostal.screen.custom;

import com.decook.creategoingpostal.block.ModBlocks;
import com.decook.creategoingpostal.block.entity.PostmastersDeskBlockEntity;
import com.decook.creategoingpostal.screen.ModMenuTypes;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class PostmastersDeskMenu extends AbstractContainerMenu {
    public final PostmastersDeskBlockEntity blockEntity;
    private final Level level;
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    public int currentTab = 0;

    public PostmastersDeskMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public PostmastersDeskMenu(int containerId, Inventory inv, BlockEntity blockEntity) {
        super(ModMenuTypes.POSTMASTERSDESK_MENU.get(), containerId);
        this.blockEntity = ((PostmastersDeskBlockEntity) blockEntity);
        this.level = inv.player.level();
        addPlayerInventory(inv);
        addPlayerHotbar(inv);
        addProfitInventory(this.blockEntity.profit);
        addPostInventory(this.blockEntity.post);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (currentTab == 0) {
                if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX + 1, TE_INVENTORY_FIRST_SLOT_INDEX + 25, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (currentTab == 1) {
                if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }
        } else if (pIndex >= TE_INVENTORY_FIRST_SLOT_INDEX) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
            player, ModBlocks.POSTMASTERSDESK_BLOCK.get());
    }

    private void addPostInventory(ItemStackHandler inv) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 8; ++l) {
                this.addSlot(new ConditionalSlot(inv, l + i * 8, 8 + l * 18, 26 + i * 18, 0));
            }
        }
    }

    private void addProfitInventory(ItemStackHandler inv) {
        this.addSlot(new ConditionalSlot(inv, 0, 80, 35, 1));
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    private class ConditionalSlot extends SlotItemHandler {
        private final int requiredTab;

        public ConditionalSlot(ItemStackHandler handler, int index, int x, int y, int requiredTab) {
            super(handler, index, x, y);
            this.requiredTab = requiredTab;
        }

        @Override
        public boolean isActive() {
            return currentTab == requiredTab;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return currentTab == requiredTab && super.mayPlace(stack);
        }

        @Override
        public boolean mayPickup(Player player) {
            return currentTab == requiredTab && super.mayPickup(player);
        }
    }
}
