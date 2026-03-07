package com.sammy.catskincRemake.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PreviewRemotePlayerTest {
    @Test
    void overridesNameRenderingForPreviewPlayers() throws Exception {
        assertEquals(
                PreviewRemotePlayer.class,
                PreviewRemotePlayer.class.getDeclaredMethod("shouldShowName").getDeclaringClass()
        );
    }
}
