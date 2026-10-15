package com.miau.vodkamod.worldgen;

import com.miau.vodkamod.VodkaMod;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registro de features (generadores de estructuras) del mod.
 */
public final class ModFeatures {
    private ModFeatures() {
    }

    public static final DeferredRegister<Feature> FEATURES =
        DeferredRegister.create(Registries.FEATURE, VodkaMod.MODID);

    /** Feature del banco de metal con spawner de vagabundos. */
    public static final RegistryObject<Feature> METAL_BENCH =
        FEATURES.register("metal_bench", MetalBenchFeature::new);
}
