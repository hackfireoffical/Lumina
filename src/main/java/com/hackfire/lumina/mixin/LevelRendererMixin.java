package com.hackfire.lumina.mixin;

import com.hackfire.lumina.LuminaClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * World render hook for future low-res shadow capture.
 * Yarn 1.20.1 name is WorldRenderer (Mojmap LevelRenderer).
 */
@Mixin(WorldRenderer.class)
public class LevelRendererMixin {
    @Inject(method = "setWorld", at = @At("TAIL"))
    private void lumina$onWorldChange(ClientWorld world, CallbackInfo ci) {
        if (LuminaClient.getConfig() == null || !LuminaClient.getConfig().enabled) {
            return;
        }
        // Future: reset shadow caches on world change
    }
}
