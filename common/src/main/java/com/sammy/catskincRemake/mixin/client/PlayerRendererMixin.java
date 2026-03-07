package com.sammy.catskincRemake.mixin.client;

import com.sammy.catskincRemake.client.SkinManagerClient;
import com.sammy.catskincRemake.client.SkinOverrideStore;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PlayerRenderer.class, priority = 2_000)
public abstract class PlayerRendererMixin {
    @Inject(
            method = "getTextureLocation(Lnet/minecraft/client/player/AbstractClientPlayer;)Lnet/minecraft/resources/ResourceLocation;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void catskincRemake$overrideTexture(AbstractClientPlayer player, CallbackInfoReturnable<ResourceLocation> cir) {
        if (player == null) {
            return;
        }

        SkinOverrideStore.Entry entry = SkinOverrideStore.get(player.getUUID());
        if (entry != null && entry.texture != null) {
            cir.setReturnValue(entry.texture);
            return;
        }

        ResourceLocation id = SkinManagerClient.getOrFetch(player);
        if (id != null) {
            cir.setReturnValue(id);
        }
    }
}

