package com.miau.vodkamod.worldgen;

import com.miau.vodkamod.registry.ModEntityTypes;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

/**
 * Genera el <b>banco de metal</b>: un banco de dos bloques de hierro con una
 * farola (valla + linterna) al lado y un spawner de vagabundos encima.
 *
 * <pre>
 *   y+1:   [spawner]          [linterna]
 *   y+0:   [hierro][hierro]   [valla]
 * </pre>
 *
 * <p>
 * Esta feature <b>no</b> se coloca sola en el mundo: se usa como decoración de
 * aldea. El pool {@code minecraft:village/&lt;tipo&gt;/decor} la referencia con un
 * {@code minecraft:feature_pool_element}, así que aparece dentro de las aldeas.
 *
 * <p>
 * El spawner se configura igual que en las mazmorras vanilla
 * ({@code MonsterRoomFeature}): se coloca el bloque y luego se llama a
 * {@link SpawnerBlockEntity#setEntityId}. El archivo de datos
 * {@code data/vodka_mod/worldgen/feature/metal_bench.json} describe esta feature.
 */
public record MetalBenchFeature() implements Feature {

    /** Codec sin configuración: la feature no tiene parámetros. */
    public static final MapCodec<MetalBenchFeature> CODEC = MapCodec.unit(MetalBenchFeature::new);

    @Override
    public MapCodec<MetalBenchFeature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        // Solo se coloca sobre suelo firme y dejando el bloque de origen libre,
        // para no incrustarse en el terreno ni flotar.
        if (!level.isEmptyBlock(origin) || !level.getBlockState(origin.below()).isSolid()) {
            return false;
        }

        // Banco de metal (dos bloques de hierro).
        level.setBlock(origin, Blocks.IRON_BLOCK.defaultBlockState(), 2);
        level.setBlock(origin.east(), Blocks.IRON_BLOCK.defaultBlockState(), 2);

        // Farola al lado: una valla con una linterna encima.
        BlockPos lampPost = origin.east(2);
        level.setBlock(lampPost, Blocks.OAK_FENCE.defaultBlockState(), 2);
        level.setBlock(lampPost.above(), Blocks.LANTERN.defaultBlockState(), 2);

        // Spawner apoyado sobre el banco, configurado con el vagabundo.
        BlockPos spawnerPos = origin.above();
        level.setBlock(spawnerPos, Blocks.SPAWNER.defaultBlockState(), 2);
        if (level.getBlockEntity(spawnerPos) instanceof SpawnerBlockEntity spawner) {
            spawner.setEntityId(ModEntityTypes.VAGABOND.get(), random);
        }

        return true;
    }
}
