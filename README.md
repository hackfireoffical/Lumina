# Lumina

**Performance-first visual overhaul for Fabric 1.20.1 + Sodium.**

Inspired by [Complementary Unbound](https://www.complementary.dev/), rebuilt as a lightweight *code mod* (no Iris required) so it stays playable on phones and low-end devices.

## Goals

- Soft directional shadows (low-res, distance-capped)
- Improved ambient / sky lighting
- Lightweight enhanced water
- Simple layered clouds
- Optional god rays & bloom (off by default on phone preset)
- Everything behind feature flags + frame-time budgets
- Sodium-native pipeline (same philosophy as Luxium)

## Requirements

- Minecraft **1.20.1**
- Fabric Loader ≥ 0.16
- Fabric API
- **Sodium** 0.5.8+

## Quality presets

| Preset  | Shadow res | Distance | God rays | Bloom | Target        |
|---------|------------|----------|----------|-------|---------------|
| PHONE   | ≤ 512      | ≤ 48     | off      | off   | Android / weak|
| LOW     | ≤ 512      | ≤ 64     | off      | off   | integrated GPU|
| MEDIUM  | ≤ 1024     | ≤ 96     | optional | optional | mid-range   |
| HIGH    | user       | user     | on       | on    | desktop       |

Default is **PHONE**.

## Building

```bash
./gradlew build
```

JAR appears in `build/libs/`.

## CI

GitHub Actions builds on every push/PR and attaches the JAR as an artifact. Releases also upload the mod JAR to the release page.

## Status

**0.1.0-alpha** — project scaffold, config, frame-budget system, lightmap tweak, mixin hooks.  
Full shadow map, water, clouds and post effects are next.

## License

MIT
