package com.hackfire.lumina.mixin;

import com.hackfire.lumina.LuminaClient;
import net.minecraft.client.render.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Terrain / shadow capture hook. Full Sodium shader injection comes later.
 */
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void lumina$onRenderStart(CallbackInfo ci) {
        if (LuminaClient.getConfig() == null || !LuminaClient.getConfig().enabled) return;
        // Future: update low-res shadow map when softShadows && under budget
    }
}
