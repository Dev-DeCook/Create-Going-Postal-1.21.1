package com.decook.creategoingpostal.screen.custom;

import com.decook.creategoingpostal.CreateGoingPostal;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PostmastersDeskPostScreen extends AbstractContainerScreen<PostmastersDeskPostMenu> {
    private final static ResourceLocation GUI_TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(CreateGoingPostal.MOD_ID, "textures/gui/postmastersdesk/postmastersdesk_post.png");

    public PostmastersDeskPostScreen(PostmastersDeskPostMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

}
