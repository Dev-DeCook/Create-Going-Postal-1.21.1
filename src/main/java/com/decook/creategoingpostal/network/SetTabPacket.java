package com.decook.creategoingpostal.network;

import com.decook.creategoingpostal.screen.custom.PostmastersDeskMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetTabPacket(int tab) implements CustomPacketPayload {
    public static final Type<SetTabPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("creategoingpostal", "set_tab"));
    public static final StreamCodec<FriendlyByteBuf, SetTabPacket> CODEC = StreamCodec.composite(
        StreamCodec.of((buf, val) -> buf.writeInt(val), FriendlyByteBuf::readInt),
        SetTabPacket::tab,
        SetTabPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SetTabPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().containerMenu instanceof PostmastersDeskMenu menu) {
                menu.currentTab = packet.tab;
            }
        });
    }
}
