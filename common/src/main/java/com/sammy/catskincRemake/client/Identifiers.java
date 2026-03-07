package com.sammy.catskincRemake.client;

import com.sammy.catskincRemake.CatskincRemake;
import java.lang.reflect.Method;
import net.minecraft.resources.ResourceLocation;

public final class Identifiers {
    private static final Method IDENTIFIER_OF = findMethod("of", String.class, String.class);
    private static final Method IDENTIFIER_TRY_PARSE = findMethod("tryParse", String.class);

    private Identifiers() {
    }

    public static ResourceLocation mod(String path) {
        return of(CatskincRemake.MOD_ID, path);
    }

    public static ResourceLocation of(String namespace, String path) {
        if (IDENTIFIER_OF != null) {
            try {
                return (ResourceLocation) IDENTIFIER_OF.invoke(null, namespace, path);
            } catch (Exception ignored) {
            }
        }
        return parse(namespace + ":" + path);
    }

    public static ResourceLocation parse(String value) {
        if (IDENTIFIER_TRY_PARSE != null) {
            try {
                ResourceLocation identifier = (ResourceLocation) IDENTIFIER_TRY_PARSE.invoke(null, value);
                if (identifier != null) {
                    return identifier;
                }
            } catch (Exception ignored) {
            }
        }
        throw new IllegalArgumentException("Invalid identifier: " + value);
    }

    private static Method findMethod(String name, Class<?>... args) {
        try {
            return ResourceLocation.class.getMethod(name, args);
        } catch (Exception ignored) {
            return null;
        }
    }
}

