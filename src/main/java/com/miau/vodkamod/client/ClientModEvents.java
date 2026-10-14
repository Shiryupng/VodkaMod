package com.miau.vodkamod.client;

import com.miau.vodkamod.VodkaMod;
import com.miau.vodkamod.registry.ModEntityTypes;

import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Registros de cliente del mod: modelos y renderers de las entidades.
 * Solo se carga en el lado cliente ({@code value = Dist.CLIENT}).
 */
@Mod.EventBusSubscriber(modid = VodkaMod.MODID, value = Dist.CLIENT)
public final class ClientModEvents {
    private ClientModEvents() {
        // Solo eventos estáticos; no se instancia.
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // El vagabundo reaprovecha la malla de jugador (skin 64x64) para poder
        // usar una skin normal como textura.
        event.registerLayerDefinition(
            VagabondRenderer.LAYER,
            () -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE, false), 64, 64)
        );
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.THROWN_BOTTLE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.VAGABOND.get(), VagabondRenderer::new);
    }
}
