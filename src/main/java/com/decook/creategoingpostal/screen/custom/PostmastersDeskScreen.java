package com.decook.creategoingpostal.screen.custom;

import com.decook.creategoingpostal.CreateGoingPostal;
import com.decook.creategoingpostal.network.SetTabPacket;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.network.PacketDistributor;

public class PostmastersDeskScreen extends AbstractContainerScreen<PostmastersDeskMenu> {
    private static final ResourceLocation POST_TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(CreateGoingPostal.MOD_ID, "textures/gui/postmastersdesk/postmastersdesk_post.png");
    private static final ResourceLocation PAYMENT_TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(CreateGoingPostal.MOD_ID, "textures/gui/postmastersdesk/postmastersdesk_payment.png");
    private static final ResourceLocation NETWORK_TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(CreateGoingPostal.MOD_ID, "textures/gui/postmastersdesk/postmastersdesk_network.png");

    private int currentTab = 0;

    public PostmastersDeskScreen(PostmastersDeskMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        currentTab = menu.currentTab;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        ResourceLocation texture = switch (currentTab) {
            case 1 -> PAYMENT_TEXTURE;
            case 2 -> NETWORK_TEXTURE;
            default -> POST_TEXTURE;
        };

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(texture, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
        int index = menu.slots.indexOf(slot);
        if (index == 36 && menu.currentTab != 1) return;
        if (index >= 37 && index <= 60 && menu.currentTab != 0) return;
        super.renderSlot(guiGraphics, slot);
    }

//    @Override
//    protected void slotClicked(Slot slot, int slotId, int mouseButton, net.minecraft.world.inventory.ClickType type) {
//        if (slot != null) {
//            int index = menu.slots.indexOf(slot);
//            if (index == 36 && menu.currentTab != 1) return;
//            if (index >= 37 && index <= 60 && menu.currentTab != 0) return;
//        }
//        super.slotClicked(slot, slotId, mouseButton, type);
//    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        if (this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
            int index = menu.slots.indexOf(this.hoveredSlot);
            if (index == 36 && menu.currentTab != 1) return;
            if (index >= 37 && index <= 60 && menu.currentTab != 0) return;
        }
        super.renderTooltip(guiGraphics, x, y);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        if (this.hoveredSlot != null) {
            int index = menu.slots.indexOf(this.hoveredSlot);
            if ((index == 36 && menu.currentTab != 1) || (index >= 37 && index <= 60 && menu.currentTab != 0)) {
                this.hoveredSlot = null;
            }
        }
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        MutableComponent text;
        text = Component.translatable("creategoingpostal.postmastersDeskPostScreen.tab1");
        guiGraphics.drawString(this.font, text, 29 - (this.font.width(text) / 2), 5, 4210752, false);
        text = Component.translatable("creategoingpostal.postmastersDeskPostScreen.tab2");
        guiGraphics.drawString(this.font, text, 88 - (this.font.width(text) / 2), 5, 4210752, false);
        text = Component.translatable("creategoingpostal.postmastersDeskPostScreen.tab3");
        guiGraphics.drawString(this.font, text, 147 - (this.font.width(text) / 2), 5, 4210752, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            double relX = mouseX - leftPos;
            double relY = mouseY - topPos;
            
            if (relY >= 0 && relY <= 15) {
                int newTab = -1;
                if (relX >= 5 && relX <= 53) newTab = 0;
                else if (relX >= 64 && relX <= 112) newTab = 1;
                else if (relX >= 123 && relX <= 171) newTab = 2;
                
                if (newTab != -1 && newTab != currentTab) {
                    currentTab = newTab;
                    menu.currentTab = newTab;
                    PacketDistributor.sendToServer(new SetTabPacket(newTab));
                    this.hoveredSlot = null;
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
