package com.hackfire.lumina.mixin;

import com.hackfire.lumina.LuminaClient;
import net.minecraft.client.render.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * World render hook for future low-res shadow capture.
 * Uses setWorld as a stable, low-risk inject point for the skeleton build.
 */
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Inject(method = "setWorld" , at = @At("TAIL"))
    private void lumina$onWorldChange(CallbackInfo ci) {
        if (LuminaClient.getConfig() == null || !LuminaClient.getConfig().enabled) {
            return;
        }
        // Future: reset shadow caches on world change
    }
}
