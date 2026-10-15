package com.miau.vodkamod.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

/**
 * Feature que coloca el "banco de metal" con un spawner de vagabundos.
 * <pre>
 *   (0,1,0)  [spawner]
 *   (0,0,0)  [iron_block] [iron_block] (1,0,0)  [lantern] (2,0,0)
 *             └──── banco ────┘            └─ farola ─┘
 * </pre>
 * <p>
 * El spawner usa {@code setEntityId()} para que spawnee vagabundos.
 */
public record MetalBenchFeature() implements Feature {

    public static final MapCodec<MetalBenchFeature> CODEC = MapCodec.unit(MetalBenchFeature::new);

    @Override
    public MapCodec<? extends Feature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        // Banco de metal: 2 bloques de hierro x 1 de alto
        level.setBlock(origin, Blocks.IRON_BLOCK.defaultBlockState(), 2);
        level.setBlock(origin.east(), Blocks.IRON_BLOCK.defaultBlockState(), 2);

        // Farola: linterna a 1 bloque de distancia
        level.setBlock(origin.east(2), Blocks.LANTERN.defaultBlockState(), 2);

        // Spawner encima del banco
        var spawnerPos = origin.above();
        level.setBlock(spawnerPos, Blocks.SPAWNER.defaultBlockState(), 2);

        // Configurar el spawner para que spawnee vagabundos
        if (level.getBlockEntity(spawnerPos) instanceof SpawnerBlockEntity spawner) {
            spawner.setEntityId(
                BuiltInRegistries.ENTITY_TYPE.byNameCodec().parse(
                    net.minecraft.nbt.NbtOps.INSTANCE,
                    net.minecraft.nbt.StringTag.valueOf("vodka_mod:vagabond")
                ).result().orElse(null),
                random
            );
        }

        return true;
    }
}
