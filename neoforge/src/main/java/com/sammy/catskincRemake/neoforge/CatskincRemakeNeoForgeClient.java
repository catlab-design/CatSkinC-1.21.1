package com.sammy.catskincRemake.neoforge;

import com.sammy.catskincRemake.client.CatskincRemakeClient;
import com.sammy.catskincRemake.client.ModSounds;

public final class CatskincRemakeNeoForgeClient {
    private CatskincRemakeNeoForgeClient() {
    }

    public static void registerClientInit() {
        ModSounds.register();
        CatskincRemakeClient.init();
    }
}
