package com.sammy.catskincRemake.client;

import com.sammy.catskincRemake.CatskincRemake;
import net.minecraft.resources.ResourceLocation;

public final class Identifiers {
    private Identifiers() {
    }

    public static ResourceLocation mod(String path) {
        return of(CatskincRemake.MOD_ID, path);
    }

    public static ResourceLocation of(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    public static ResourceLocation parse(String value) {
        return ResourceLocation.parse(value);
    }
}

