package com.hackfire.lumina.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hackfire.lumina.LuminaClient;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Flat, phone-first config. Every expensive feature is off or heavily capped by default.
 * Profiles mirror Complementary's POTATO / VERYLOW spirit.
 */
public class LuminaConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("lumina.json");

    // Master
    public boolean enabled = true;

    // Lighting (cheap)
    public boolean improvedAmbient = true;
    public float ambientStrength = 0.85f;
    public float directStrength = 1.05f;

    // Soft shadows — low res, limited distance (phone safe)
    public boolean softShadows = true;
    public int shadowResolution = 512;      // 256/512/1024 — keep low on phone
    public int shadowDistance = 48;         // blocks
    public int shadowSamples = 4;           // PCF samples
    public float shadowSoftness = 1.2f;

    // Water (lightweight surface tint + simple foam)
    public boolean enhancedWater = true;
    public float waterOpacity = 0.75f;
    public boolean waterWaves = false;      // off by default on phone

    // Clouds (simple layered, low cost)
    public boolean clouds = true;
    public int cloudLayers = 1;             // 1 = phone, 2-3 desktop
    public float cloudOpacity = 0.65f;
    public int cloudRenderDistance = 96;

    // God rays / light shafts (very cheap post)
    public boolean godRays = false;         // off by default — enable on stronger devices
    public float godRayIntensity = 0.35f;
    public int godRaySamples = 8;

    // Bloom (Kawase-style, optional)
    public boolean bloom = false;
    public float bloomIntensity = 0.25f;
    public int bloomLevels = 3;

    // Plants / waving (off on phone)
    public boolean plantsWave = false;
    public float waveStrength = 0.6f;

    // Quality preset
    public QualityPreset preset = QualityPreset.PHONE;

    // Debug
    public boolean debugHud = false;

    public enum QualityPreset {
        PHONE,      // shadows 256-512, no godrays/bloom/waves
        LOW,
        MEDIUM,
        HIGH
    }

    public static LuminaConfig load() {
        if (Files.exists(PATH)) {
            try {
                String json = Files.readString(PATH);
                LuminaConfig cfg = GSON.fromJson(json, LuminaConfig.class);
                if (cfg != null) {
                    cfg.applyPreset();
                    return cfg;
                }
            } catch (Exception e) {
                LuminaClient.LOGGER.warn("Failed to load config, using defaults", e);
            }
        }
        LuminaConfig cfg = new LuminaConfig();
        cfg.applyPreset();
        cfg.save();
        return cfg;
    }

    public void save() {
        try {
            Files.createDirectories(PATH.getParent());
            Files.writeString(PATH, GSON.toJson(this));
        } catch (IOException e) {
            LuminaClient.LOGGER.error("Failed to save config", e);
        }
    }

    /** Apply hard caps based on preset so phone stays safe. */
    public void applyPreset() {
        switch (preset) {
            case PHONE -> {
                shadowResolution = Math.min(shadowResolution, 512);
                shadowDistance = Math.min(shadowDistance, 48);
                shadowSamples = Math.min(shadowSamples, 4);
                cloudLayers = 1;
                cloudRenderDistance = Math.min(cloudRenderDistance, 96);
                godRays = false;
                bloom = false;
                plantsWave = false;
                waterWaves = false;
            }
            case LOW -> {
                shadowResolution = Math.min(shadowResolution, 512);
                shadowDistance = Math.min(shadowDistance, 64);
                godRays = false;
                bloom = false;
            }
            case MEDIUM -> {
                shadowResolution = Math.min(shadowResolution, 1024);
                shadowDistance = Math.min(shadowDistance, 96);
            }
            case HIGH -> { /* user values */ }
        }
    }
}
