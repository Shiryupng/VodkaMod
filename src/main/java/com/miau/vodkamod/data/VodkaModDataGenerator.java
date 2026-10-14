package com.miau.vodkamod.data;

import com.miau.vodkamod.VodkaMod;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Generador de datos del mod.
 * <p>
 * Genera automáticamente el NBT de la estructura
 * {@code data/vodka_mod/structure/metal_bench.nbt} (banco de metal + farola +
 * spawner del vagabundo). Se ejecuta con {@code gradlew runData}.
 */
@Mod.EventBusSubscriber(modid = VodkaMod.MODID)
public final class VodkaModDataGenerator {
    private VodkaModDataGenerator() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        if (!event.includeServer()) {
            return;
        }
        event.getGenerator().addProvider(true, new MetalBenchStructureProvider(event.getGenerator()));
    }

    private static final class MetalBenchStructureProvider implements net.minecraft.data.DataProvider {
        private final net.minecraft.data.DataGenerator generator;

        MetalBenchStructureProvider(net.minecraft.data.DataGenerator generator) {
            this.generator = generator;
        }

        @Override
        public java.util.concurrent.CompletableFuture<?> run(net.minecraft.data.CachedOutput cache) {
            Path output = Path.of("..", "src/main/resources/data/vodka_mod/structure/metal_bench.nbt");
            try {
                java.nio.file.Files.createDirectories(output.getParent());
                NbtIo.writeCompressed(buildMetalBenchStructure(), output);
                System.out.println("[VodkaMod] Estructura generada: " + output.toAbsolutePath());
            } catch (Exception e) {
                throw new RuntimeException("No se pudo guardar metal_bench.nbt", e);
            }
            return java.util.concurrent.CompletableFuture.completedFuture(null);
        }

        @Override
        public String getName() {
            return "VodkaMod Structure Generator";
        }
    }

    /**
     * Construye el NBT de la estructura:
     * <pre>
     *   (0,1,0)  [spawner]              ← encima del banco
     *   (0,0,0)  [iron_block] [iron_block] (1,0,0)  [lantern] (2,0,0)
     *             └──── banco ────┘            └─ farola ─┘
     * </pre>
     * <p>
     * Usa {@link NbtUtils#addCurrentDataVersion} para que el DataVersion sea
     * el correcto para la versión actual del juego.
     */
    private static CompoundTag buildMetalBenchStructure() {
        int sizeX = 3;
        int sizeY = 2;
        int sizeZ = 1;

        // Paleta de bloques:
        // 0 = iron_block (banco)
        // 1 = air
        // 2 = lantern (farola)
        // 3 = mob_spawner (spawner del vagabundo)
        ListTag palette = new ListTag();
        palette.add(blockState("minecraft:iron_block"));
        palette.add(blockState("minecraft:air"));
        palette.add(blockState("minecraft:lantern"));
        palette.add(blockState("minecraft:mob_spawner"));

        // NBT del spawner con SpawnPotentials
        CompoundTag spawnerNbt = buildVagabondSpawner();

        // Bloques
        ListTag blockList = new ListTag();
        blockList.add(blockTag(0, 0, 0, 0, null));           // iron_block
        blockList.add(blockTag(1, 0, 0, 0, null));           // iron_block
        blockList.add(blockTag(2, 0, 0, 2, null));           // lantern
        blockList.add(blockTag(0, 1, 0, 3, spawnerNbt));     // mob_spawner

        // Tag raíz
        CompoundTag root = new CompoundTag();

        // size [x, y, z]
        ListTag sizeTag = new ListTag();
        sizeTag.add(IntTag.valueOf(sizeX));
        sizeTag.add(IntTag.valueOf(sizeY));
        sizeTag.add(IntTag.valueOf(sizeZ));
        root.put("size", sizeTag);

        root.put("palette", palette);
        root.put("blocks", blockList);
        root.put("entities", new ListTag());

        // Añadir DataVersion correcto automáticamente
        return NbtUtils.addCurrentDataVersion(root);
    }

    private static CompoundTag blockState(String name) {
        CompoundTag tag = new CompoundTag();
        tag.putString("Name", name);
        return tag;
    }

    private static CompoundTag blockTag(int x, int y, int z, int state, CompoundTag nbt) {
        CompoundTag tag = new CompoundTag();
        tag.put("pos", intList(x, y, z));
        tag.putInt("state", state);
        if (nbt != null) {
            tag.put("nbt", nbt);
        }
        return tag;
    }

    private static ListTag intList(int x, int y, int z) {
        ListTag list = new ListTag();
        list.add(IntTag.valueOf(x));
        list.add(IntTag.valueOf(y));
        list.add(IntTag.valueOf(z));
        return list;
    }

    /**
     * Construye el NBT de un spawner de vagabundos.
     * <p>
     * 67 % → spawnea 1 vagabundo, 33 % → spawnea 2 vagabundos.
     */
    private static CompoundTag buildVagabondSpawner() {
        CompoundTag spawner = new CompoundTag();

        CompoundTag spawnData = new CompoundTag();
        spawnData.put("entity", vagabondEntity());
        spawner.put("SpawnData", spawnData);

        ListTag spawnPotentials = new ListTag();

        CompoundTag potential1 = new CompoundTag();
        potential1.putInt("weight", 67);
        CompoundTag data1 = new CompoundTag();
        data1.put("entity", vagabondEntity());
        data1.putInt("count", 1);
        potential1.put("data", data1);
        spawnPotentials.add(potential1);

        CompoundTag potential2 = new CompoundTag();
        potential2.putInt("weight", 33);
        CompoundTag data2 = new CompoundTag();
        data2.put("entity", vagabondEntity());
        data2.putInt("count", 2);
        potential2.put("data", data2);
        spawnPotentials.add(potential2);

        spawner.put("SpawnPotentials", spawnPotentials);

        spawner.putInt("SpawnCount", 1);
        spawner.putInt("MaxNearbyEntities", 4);
        spawner.putInt("RequiredPlayerRange", 16);
        spawner.putInt("Delay", 0);
        spawner.putInt("MinSpawnDelay", 200);
        spawner.putInt("MaxSpawnDelay", 400);

        return spawner;
    }

    private static CompoundTag vagabondEntity() {
        CompoundTag entity = new CompoundTag();
        entity.putString("id", "vodka_mod:vagabond");
        return entity;
    }
}
