package com.miau.vodkamod.worldgen;

import com.miau.vodkamod.VodkaMod;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registro de los <b>tipos</b> de feature del mod.
 * <p>
 * En esta versión de Minecraft los features son datos: el "tipo" (el
 * {@link MapCodec}) se registra en Java dentro del registro
 * {@code minecraft:worldgen/feature_type}, y la instancia concreta se describe
 * en un JSON de {@code data/&lt;mod&gt;/worldgen/feature/}. Ver
 * {@code data/vodka_mod/worldgen/feature/metal_bench.json}.
 */
public final class ModFeatures {
    private ModFeatures() {
        // Solo contiene registros; no se instancia.
    }

    /** Registro de tipos de feature (codecs) del mod. */
    public static final DeferredRegister<MapCodec<? extends Feature>> FEATURES =
        DeferredRegister.create(Registries.FEATURE_TYPE, VodkaMod.MODID);

    /** Tipo de feature del banco de metal (banco + farola + spawner). */
    public static final RegistryObject<MapCodec<? extends Feature>> METAL_BENCH =
        FEATURES.register("metal_bench", () -> MetalBenchFeature.CODEC);
}
