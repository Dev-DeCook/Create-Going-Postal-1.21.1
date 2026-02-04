package com.decook.creategoingpostal;

import org.slf4j.Logger;

import com.decook.creategoingpostal.block.ModBlocks;
import com.decook.creategoingpostal.block.entity.ModBlockEntities;
import com.decook.creategoingpostal.item.ModCreativeModeTabs;
import com.decook.creategoingpostal.item.ModItems;
import com.decook.creategoingpostal.network.SetTabPacket;
import com.decook.creategoingpostal.screen.ModMenuTypes;
import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(CreateGoingPostal.MOD_ID)
public class CreateGoingPostal {
    public static final String MOD_ID = "creategoingpostal";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateGoingPostal(IEventBus modEventBus, ModContainer modContainer) {
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerPackets);
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    private void registerPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(SetTabPacket.TYPE, SetTabPacket.CODEC, SetTabPacket::handle);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
