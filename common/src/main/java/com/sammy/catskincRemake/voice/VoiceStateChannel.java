package com.sammy.catskincRemake.voice;

import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public final class VoiceStateChannel {
    public static final ResourceLocation VOICE_STATE_ID = createChannelId();

    private static final AtomicInteger SEQUENCE = new AtomicInteger();
    private static volatile MinecraftServer boundServer;

    private VoiceStateChannel() {
    }

    private static ResourceLocation createChannelId() {
        try {
            Method of = ResourceLocation.class.getMethod("of", String.class, String.class);
            return (ResourceLocation) of.invoke(null, "catskinc_remake", "voice_state");
        } catch (Exception ignored) {
        }
        try {
            Method tryParse = ResourceLocation.class.getMethod("tryParse", String.class);
            ResourceLocation parsed = (ResourceLocation) tryParse.invoke(null, "catskinc_remake:voice_state");
            if (parsed != null) {
                return parsed;
            }
        } catch (Exception ignored) {
        }
        throw new IllegalStateException("Failed to create voice_state channel identifier");
    }

    public static void bindServer(MinecraftServer server) {
        boundServer = server;
    }

    public static void unbindServer(MinecraftServer server) {
        MinecraftServer current = boundServer;
        if (current == null) {
            return;
        }
        if (server == null || current == server) {
            boundServer = null;
        }
    }

    public static void broadcast(UUID uuid, boolean speaking) {
        if (uuid == null) {
            return;
        }
        MinecraftServer server = boundServer;
        if (server == null) {
            return;
        }
        int sequence = nextSequence();
        long sentAtMs = System.currentTimeMillis();
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            RegistryFriendlyByteBuf payload = new RegistryFriendlyByteBuf(Unpooled.buffer(32), player.registryAccess());
            payload.writeUUID(uuid);
            payload.writeBoolean(speaking);
            payload.writeVarInt(sequence);
            payload.writeLong(sentAtMs);
            NetworkManager.sendToPlayer(player, VOICE_STATE_ID, payload);
        }
    }

    private static int nextSequence() {
        return SEQUENCE.updateAndGet(value -> value == Integer.MAX_VALUE ? 0 : value + 1);
    }
}
