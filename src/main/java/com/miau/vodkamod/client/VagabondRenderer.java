package com.miau.vodkamod.client;

import com.miau.vodkamod.VodkaMod;
import com.miau.vodkamod.entity.VagabondEntity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;

/**
 * Renderer del vagabundo.
 * <p>
 * Usa un modelo humanoide normal, al que {@link HumanoidMobRenderer} le añade
 * automáticamente la capa {@code ItemInHandLayer}. Esa capa dibuja el item de
 * la mano con el contexto {@code THIRD_PERSON_RIGHT_HAND}, exactamente igual que
 * la mano de un jugador: por eso el vagabundo lleva la botella rota con la
 * misma rotación que el jugador.
 * <p>
 * La capa del modelo ({@link #LAYER}) se registra en {@code ClientModEvents}.
 */
public class VagabondRenderer
    extends HumanoidMobRenderer<VagabondEntity, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {

    /** Capa del modelo del vagabundo (se registra en {@link ClientModEvents}). */
    public static final ModelLayerLocation LAYER =
        new ModelLayerLocation(Identifier.fromNamespaceAndPath(VodkaMod.MODID, "vagabond"), "main");

    /**
     * Skin del vagabundo: una imagen PNG 64×64 en formato de skin de jugador.
     * <p>
     * Para personalizarlo, sustituye el archivo
     * {@code src/main/resources/assets/vodka_mod/textures/entity/vagabond.png}
     * por tu propia skin 64×64 (el formato del skin de un jugador).
     */
    private static final Identifier TEXTURE =
        Identifier.fromNamespaceAndPath(VodkaMod.MODID, "textures/entity/vagabond.png");

    public VagabondRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(LAYER)), 0.5F);
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public Identifier getTextureLocation(HumanoidRenderState state) {
        return TEXTURE;
    }

    /**
     * Pone el brazo en pose {@code ITEM} (la misma que usa el jugador) cuando
     * lleva algo en la mano, en vez de dejarlo colgando.
     */
    @Override
    protected HumanoidModel.ArmPose getArmPose(VagabondEntity mob, HumanoidArm arm) {
        ItemStack held = mob.getItemHeldByArm(arm);
        return held.isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
    }
}
