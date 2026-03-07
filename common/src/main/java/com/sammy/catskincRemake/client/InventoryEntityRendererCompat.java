package com.sammy.catskincRemake.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.LivingEntity;

public final class InventoryEntityRendererCompat {
    private InventoryEntityRendererCompat() {
    }

    public static void drawEntity(GuiGraphics context, int x1, int y1, int x2, int y2, int mouseX, int mouseY, LivingEntity entity) {
        if (entity == null) {
            return;
        }

        int width = Math.max(1, x2 - x1);
        int height = Math.max(1, y2 - y1);
        int previewHeight = Math.max(1, y2 - y1);
        int verticalOffset = Math.max(4, previewHeight / 14);
        int appliedOffset = Math.min(verticalOffset, Math.max(0, y1));
        int y1Adjusted = y1 - appliedOffset;
        int y2Adjusted = y2 - appliedOffset;
        int size = Math.max(20, Math.round(Math.min(width, height) * 0.35F));

        try {
            // Use the native 1.21 renderer path that applies yaw/pitch from mouse position.
            InventoryScreen.renderEntityInInventoryFollowsMouse(
                    context,
                    x1,
                    y1Adjusted,
                    x2,
                    y2Adjusted,
                    size,
                    0.0F,
                    mouseX,
                    mouseY,
                    entity
            );
        } catch (Exception ignored) {
            // Keep UI responsive even if preview rendering fails in some environments.
        }
    }
}

