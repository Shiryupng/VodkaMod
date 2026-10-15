package com.miau.vodkamod.worldgen;

import com.miau.vodkamod.VodkaMod;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registro de features (generadores de estructuras) del mod.
 * <p>
 * En Forge 26.3, los features se registran como {@code MapCodec<? extends Feature>}
 * en el registro {@code FEATURE_TYPE}, no como objetos {@code Feature} directamente.
 */
public final class ModFeatures {
    private ModFeatures() {
    }

    public static final DeferredRegister<MapCodec<? extends Feature>> FEATURES =
        DeferredRegister.create(Registries.FEATURE_TYPE, VodkaMod.MODID);

    /** Feature del banco de metal con spawner de vagabundos. */
    public static final RegistryObject<MapCodec<? extends Feature>> METAL_BENCH =
        FEATURES.register("metal_bench", () -> MetalBenchFeature.CODEC);
}
