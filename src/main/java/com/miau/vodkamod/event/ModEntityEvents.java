package com.miau.vodkamod.event;

import com.miau.vodkamod.VodkaMod;
import com.miau.vodkamod.entity.VagabondEntity;
import com.miau.vodkamod.registry.ModEntityTypes;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Eventos de entidades del mod.
 * <p>
 * Registra los atributos de cada mob propio. El evento es del bus del mod y se
 * dispara tanto en cliente como en servidor.
 */
@Mod.EventBusSubscriber(modid = VodkaMod.MODID)
public final class ModEntityEvents {
    private ModEntityEvents() {
        // Solo eventos estáticos; no se instancia.
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.VAGABOND.get(), VagabondEntity.createAttributes().build());
    }

    /**
     * Registra dónde puede aparecer el vagabundo de forma natural (en el suelo,
     * sobre el mapa de altura {@code MOTION_BLOCKING_NO_LEAVES}).
     * <p>
     * La regla de si puede aparecer de día la decide
     * {@link VagabondEntity#checkSpawnRules}; aquí solo se le dice al juego
     * "dónde" es válido un spawn. Los biomas en los que aparece están en el
     * biome modifier {@code data/vodka_mod/forge/biome_modifier/vagabond_spawns.json}.
     */
    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(
            ModEntityTypes.VAGABOND.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            VagabondEntity::checkSpawnRules,
            SpawnPlacementRegisterEvent.Operation.REPLACE
        );
    }
}
