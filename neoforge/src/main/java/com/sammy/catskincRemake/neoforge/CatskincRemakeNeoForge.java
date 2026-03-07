package com.sammy.catskincRemake.neoforge;

import com.sammy.catskincRemake.CatskincRemake;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(CatskincRemakeNeoForge.NEOFORGE_MOD_ID)
public final class CatskincRemakeNeoForge {
    public static final String NEOFORGE_MOD_ID = "catskinc_remake";

    public CatskincRemakeNeoForge() {
        CatskincRemake.init();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            CatskincRemakeNeoForgeClient.registerClientInit();
        }
    }
}
