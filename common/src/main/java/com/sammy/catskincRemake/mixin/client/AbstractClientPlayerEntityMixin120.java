package com.sammy.catskincRemake.mixin.client;

import com.sammy.catskincRemake.client.SkinManagerClient;
import com.sammy.catskincRemake.client.SkinOverrideStore;
import com.sammy.catskincRemake.client.SkinTextureFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerEntityMixin120 {
    @Inject(
            method = "getSkin()Lnet/minecraft/client/resources/PlayerSkin;",
            at = @At("RETURN"),
            cancellable = true,
            require = 0
    )
    private void catskincRemake$overrideSkin(CallbackInfoReturnable<PlayerSkin> cir) {
        AbstractClientPlayer self = (AbstractClientPlayer) (Object) this;
        UUID uuid = self.getUUID();

        SkinOverrideStore.Entry entry = SkinOverrideStore.get(uuid);
        ResourceLocation overrideTexture = null;
        Boolean slimOverride = null;
        if (entry != null && entry.texture != null) {
            overrideTexture = entry.texture;
            slimOverride = entry.slim;
        } else {
            ResourceLocation cached = SkinManagerClient.getCached(uuid);
            if (cached != null) {
                overrideTexture = cached;
                slimOverride = SkinManagerClient.isSlimOrNull(uuid);
            } else {
                SkinManagerClient.ensureFetch(uuid);
            }
        }

        if (overrideTexture == null) {
            return;
        }

        PlayerSkin base = cir.getReturnValue();
        if (base == null) {
            base = DefaultPlayerSkin.get(uuid);
        }
        Object patched = SkinTextureFactory.withTextureAndModel(base, overrideTexture, slimOverride);
        if (patched instanceof PlayerSkin patchedSkin) {
            cir.setReturnValue(patchedSkin);
        }
    }
}

