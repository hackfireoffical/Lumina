package com.hackfire.lumina.mixin;

import com.hackfire.lumina.LuminaClient;
import com.hackfire.lumina.config.LuminaConfig;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Skeleton hook on lightmap update. Full ambient curve comes later.
 */
@Mixin(LightmapTextureManager.class)
public class LightTextureMixin {
    @Inject(method = "update", at = @At("HEAD"))
    private void lumina$onLightmapUpdate(float delta, CallbackInfo ci) {
        LuminaConfig cfg = LuminaClient.getConfig();
        if (cfg == null || !cfg.enabled || !cfg.improvedAmbient) {
            return;
        }
        // Future: adjust lightmap curve
    }
}
