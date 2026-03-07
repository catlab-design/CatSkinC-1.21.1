package com.sammy.catskincRemake1_21_1.fabric;

import com.sammy.catskincRemake1_21_1.CatskincRemake1_21_1;
import net.fabricmc.api.ModInitializer;

public final class CatskincRemake1_21_1Fabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        CatskincRemake1_21_1.init();
    }
}
