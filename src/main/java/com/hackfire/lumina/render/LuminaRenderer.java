package com.hackfire.lumina.render;

import com.hackfire.lumina.config.LuminaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

/**
 * Central render orchestrator. Keeps work budgets tight for phone.
 */
public class LuminaRenderer {
    private final LuminaConfig config;
    private long lastFrameNs = 0;
    private double smoothedMs = 16.0;

    public LuminaRenderer(LuminaConfig config) {
        this.config = config;
    }

    public void tick(MinecraftClient client) {
        long now = System.nanoTime();
        if (lastFrameNs > 0) {
            double dtMs = (now - lastFrameNs) / 1_000_000.0;
            smoothedMs = smoothedMs * 0.9 + dtMs * 0.1;
        }
        lastFrameNs = now;
    }

    public void renderDebugHud(DrawContext graphics, float tickDelta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc == null || mc.textRenderer == null) return;

        int y = 4;
        graphics.drawTextWithShadow(mc.textRenderer, "Lumina " + config.preset, 4, y, 0xA0E0FF);
        y += 10;
        graphics.drawTextWithShadow(mc.textRenderer,
                String.format("frame %.1f ms  shadows=%s  water=%s  clouds=%s",
                        smoothedMs,
                        config.softShadows ? "on" : "off",
                        config.enhancedWater ? "on" : "off",
                        config.clouds ? "on" : "off"),
                4, y, 0xC0C0C0);
    }

    public double getSmoothedFrameMs() {
        return smoothedMs;
    }

    public boolean isUnderBudget() {
        return smoothedMs < 28.0;
    }
}
