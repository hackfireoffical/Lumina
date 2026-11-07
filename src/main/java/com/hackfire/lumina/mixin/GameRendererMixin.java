package com.hackfire.lumina.mixin;

import com.hackfire.lumina.LuminaClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Post-frame hook for future god rays / bloom.
 */
@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void lumina$afterRender(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        if (LuminaClient.getConfig() == null || !LuminaClient.getConfig().enabled) {
            return;
        }
        // Future: cheap post passes when under budget
    }
}
