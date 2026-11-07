package com.hackfire.lumina.mixin;

import com.hackfire.lumina.LuminaClient;
import com.hackfire.lumina.config.LuminaConfig;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Subtle ambient / direct light boost without heavy shaders.
 * Safe on every device.
 */
@Mixin(LightmapTextureManager.class)
public class LightTextureMixin {
    @ModifyVariable(method = "update", at = @At("STORE"), ordinal = 1)
    private float lumina$adjustSkyFactor(float sky) {
        LuminaConfig cfg = LuminaClient.getConfig();
        if (cfg == null || !cfg.enabled || !cfg.improvedAmbient) return sky;
        return Math.min(1.0f, sky * cfg.ambientStrength);
    }
}
