package com.decook.creategoingpostal.block.entity;

import com.decook.creategoingpostal.CreateGoingPostal;
import com.decook.creategoingpostal.block.ModBlocks;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CreateGoingPostal.MOD_ID);

    public static final Supplier<BlockEntityType<PostmastersDeskBlockEntity>> POSTMASTERSDESK_BE =
        BLOCK_ENTITIES.register("postmastersdesk_be", () -> BlockEntityType.Builder.of(
            PostmastersDeskBlockEntity::new, ModBlocks.POSTMASTERSDESK_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
