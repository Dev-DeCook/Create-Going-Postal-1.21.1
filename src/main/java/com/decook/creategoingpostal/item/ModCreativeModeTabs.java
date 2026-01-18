package com.decook.creategoingpostal.item;

import com.decook.creategoingpostal.CreateGoingPostal;
import com.decook.creategoingpostal.block.ModBlocks;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;

public class ModCreativeModeTabs {

    // Create a Deferred Register to hold CreativeModeTabs
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateGoingPostal.MOD_ID);

    // create a tab with the id "creategoingpostal:creategoingpostal_tab" for all items
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATEGOINGPOSTAL_TAB = CREATIVE_MODE_TABS.register("creategoingpostal_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.creategoingpostal"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.POSTALPARCEL.get().getDefaultInstance())
            .displayItems((itemDisplayPerameters, output) -> {
                output.accept(ModItems.POSTALPARCEL);
                output.accept(ModBlocks.POSTMASTERSDESK_BLOCK);
            }).build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
