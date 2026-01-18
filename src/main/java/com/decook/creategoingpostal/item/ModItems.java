package com.decook.creategoingpostal.item;

import com.decook.creategoingpostal.CreateGoingPostal;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("null")
public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateGoingPostal.MOD_ID);

    public static final DeferredItem<Item> POSTALPARCEL = ITEMS.register("postalparcel",
            () -> new Item(new Item.Properties()));

            
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
