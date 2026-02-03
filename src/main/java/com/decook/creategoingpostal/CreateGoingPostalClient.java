package com.decook.creategoingpostal;

import com.decook.creategoingpostal.screen.ModMenuTypes;
import com.decook.creategoingpostal.screen.custom.PostmastersDeskScreen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = CreateGoingPostal.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = CreateGoingPostal.MOD_ID, value = Dist.CLIENT)
public class CreateGoingPostalClient {
    public CreateGoingPostalClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.POSTMASTERSDESK_MENU.get(), PostmastersDeskScreen::new);
    }
}
