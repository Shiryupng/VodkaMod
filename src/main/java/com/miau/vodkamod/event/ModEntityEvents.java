package com.miau.vodkamod.event;

import com.miau.vodkamod.VodkaMod;
import com.miau.vodkamod.entity.VagabondEntity;
import com.miau.vodkamod.registry.ModEntityTypes;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
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
}
