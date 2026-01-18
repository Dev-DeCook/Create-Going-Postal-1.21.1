package com.decook.creategoingpostal;

import org.slf4j.Logger;

import com.decook.creategoingpostal.block.ModBlocks;
import com.decook.creategoingpostal.item.ModItems;
import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CreateGoingPostal.MOD_ID)
public class CreateGoingPostal {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "creategoingpostal";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "creategoingpostal" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    // Creates a creative tab with the id "creategoingpostal:creategoingpostal_tab" for all items and place it after the combat tab.
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATEGOINGPOSTAL_TAB = CREATIVE_MODE_TABS.register("creategoingpostal_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.creategoingpostal")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.POSTALPARCEL.get().getDefaultInstance())
            // Add the mod items to its own tabs
            .displayItems((parameters, output) -> {
                output.accept(ModItems.POSTALPARCEL.get());
                output.accept(ModBlocks.POSTMASTERSDESK_BLOCK.get());
            }).build());

    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public CreateGoingPostal(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so the tab get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (CreateGoingPostal) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.POSTALPARCEL);
            event.accept(ModBlocks.POSTMASTERSDESK_BLOCK);
        }
    }
}
