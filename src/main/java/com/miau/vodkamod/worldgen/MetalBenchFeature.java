package com.miau.vodkamod.worldgen;

import com.miau.vodkamod.entity.VagabondEntity;
import com.miau.vodkamod.registry.ModEntityTypes;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

/**
 * Genera el <b>banco de metal</b>: un banco de dos bloques de hierro con una
 * farola (valla + linterna) al lado, acompañado por un <b>vagabundo</b>.
 *
 * <pre>
 *   y+1:   [vagabundo]          [linterna]
 *   y+0:   [hierro][hierro]     [valla]
 * </pre>
 *
 * <p>
 * El vagabundo es una <b>entidad</b> que se crea al generarse la estructura (no
 * un spawner): así aparece siempre exactamente un vagabundo por banco. Se añade
 * al chunk con {@link WorldGenLevel#addFreshEntity}, la misma vía que usan las
 * estructuras vanilla (p. ej. la bruja de la cabaña del pantano), y se marca con
 * {@link net.minecraft.world.entity.Mob#setPersistenceRequired()} para que no
 * desaparezca.
 *
 * <p>
 * Esta feature <b>no</b> se coloca sola en el mundo: se usa como decoración de
 * aldea. El pool {@code minecraft:village/&lt;tipo&gt;/decor} la referencia con un
 * {@code minecraft:feature_pool_element}, así que aparece dentro de las aldeas.
 * La frecuencia se controla con el {@code weight} de esos pools.
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

        // El vagabundo acompaña al banco: se genera la entidad sobre la estructura.
        spawnVagabond(level, origin.above());

        return true;
    }

    /**
     * Crea y añade un vagabundo de pie sobre el banco.
     * <p>
     * Se usa {@link net.minecraft.world.entity.EntityType#create(ServerLevel,
     * net.minecraft.world.entity.EntityType.PostSpawnProcessor, BlockPos,
     * EntitySpawnReason, boolean, boolean)}, que coloca la entidad y ejecuta su
     * {@code finalizeSpawn} igual que el spawn vanilla, pero sin añadirla (eso lo
     * hacemos con {@link WorldGenLevel#addFreshEntity} para que sea seguro
     * durante la generación del mundo).
     */
    private static void spawnVagabond(WorldGenLevel level, BlockPos spawnPos) {
        ServerLevel serverLevel = level.getLevel();
        VagabondEntity vagabond = ModEntityTypes.VAGABOND.get().create(
            serverLevel,
            null,
            spawnPos,
            EntitySpawnReason.STRUCTURE,
            false,
            false
        );

        if (vagabond == null) {
            // P. ej. dificultad pacífico: la entidad no puede crearse.
            return;
        }

        // Que no desaparezca con la distancia aunque el jugador se aleje.
        vagabond.setPersistenceRequired();
        level.addFreshEntity(vagabond);
    }
}
