package com.miau.vodkamod.event;

import com.miau.vodkamod.VodkaMod;
import com.miau.vodkamod.entity.VagabondEntity;
import com.miau.vodkamod.registry.ModEntityTypes;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Eventos de entidades del mod.
 * <p>
 * Registra los atributos y las reglas de aparición del vagabundo y ajusta su
 * relación con otras entidades (p. ej. impide que los gólems de hierro lo
 * ataquen). {@code @Mod.EventBusSubscriber} usa el bus {@code BOTH}: cada método
 * se enruta automáticamente al bus del mod o al bus de juego según el tipo de
 * evento.
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

    /**
     * Evita que el <b>gólem de hierro</b> ataque al vagabundo.
     * <p>
     * El vagabundo es un {@link net.minecraft.world.entity.monster.Monster} (ataca
     * a los jugadores), y el gólem de hierro ataca a todo {@code Enemy} cercano
     * (ver {@code IronGolem}); por eso antes acababa matándolo. El vagabundo es,
     * sin embargo, un vecino más del pueblo, así que aquí <b>cancelamos</b> el
     * cambio de objetivo cuando el que apunta es un gólem de hierro y el
     * objetivo es un vagabundo.
     * <p>
     * Devolver {@code true} cancela el evento (el gólem conserva su objetivo
     * anterior, es decir, no adquiere al vagabundo). El parche se aplica a
     * cualquier intento de fijar objetivo, tanto por la IA como al empujar
     * ({@code IronGolem#doPush}).
     */
    @SubscribeEvent
    public static boolean onLivingChangeTarget(LivingChangeTargetEvent event) {
        return event.getNewTarget() instanceof VagabondEntity
            && event.getEntity() instanceof IronGolem;
    }
}
