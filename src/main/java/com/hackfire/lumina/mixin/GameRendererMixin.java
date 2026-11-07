package com.hackfire.lumina.mixin;

import com.hackfire.lumina.LuminaClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Hook for post-processing (god rays, bloom) after world render.
 * Intentionally empty for v0.1 — skeleton only.
 */
@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "render", at = @At("RETURN"))
    private void lumina$onRenderEnd(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        if (LuminaClient.getConfig() == null || !LuminaClient.getConfig().enabled) return;
        // Future: apply cheap post passes when under budget
    }
}
