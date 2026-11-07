package com.hackfire.lumina;

import com.hackfire.lumina.config.LuminaConfig;
import com.hackfire.lumina.render.LuminaRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lumina — performance-first visual mod for Fabric 1.20.1 + Sodium.
 * Inspired by Complementary Unbound, built for phones and low-end devices.
 */
public class LuminaClient implements ClientModInitializer {
    public static final String MOD_ID = "lumina";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static LuminaConfig config;
    private static LuminaRenderer renderer;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Lumina initializing (phone-optimized profile)");

        config = LuminaConfig.load();
        renderer = new LuminaRenderer(config);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (config.enabled) {
                renderer.tick(client);
            }
        });

        HudRenderCallback.EVENT.register((graphics, tickDelta) -> {
            if (config.debugHud) {
                renderer.renderDebugHud(graphics, tickDelta);
            }
        });

        LOGGER.info("Lumina ready — features: soft shadows={}, water={}, clouds={}, godrays={}",
                config.softShadows, config.enhancedWater, config.clouds, config.godRays);
    }

    public static LuminaConfig getConfig() {
        return config;
    }

    public static LuminaRenderer getRenderer() {
        return renderer;
    }
}
