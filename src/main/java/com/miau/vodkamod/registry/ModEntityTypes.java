package com.miau.vodkamod.registry;

import com.miau.vodkamod.VodkaMod;
import com.miau.vodkamod.entity.VagabondEntity;
import com.miau.vodkamod.projectile.ThrownBottleEntity;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registro de las entidades del mod: el proyectil (botella lanzada) y el
 * vagabundo.
 */
public final class ModEntityTypes {
    private ModEntityTypes() {
        // Solo contiene registros; no se instancia.
    }

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, VodkaMod.MODID);

    public static final RegistryObject<EntityType<ThrownBottleEntity>> THROWN_BOTTLE =
        ENTITY_TYPES.register("thrown_bottle",
            () -> EntityType.Builder.<ThrownBottleEntity>of(ThrownBottleEntity::new, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build(ResourceKey.create(
                    Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(VodkaMod.MODID, "thrown_bottle")
                ))
        );

    /** Vagabundo: mob hostil que lleva la botella rota y suelta vodka. */
    public static final RegistryObject<EntityType<VagabondEntity>> VAGABOND =
        ENTITY_TYPES.register("vagabond",
            () -> EntityType.Builder.<VagabondEntity>of(VagabondEntity::new, MobCategory.MONSTER)
                .sized(0.6F, 1.95F)
                .clientTrackingRange(8)
                .build(ResourceKey.create(
                    Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(VodkaMod.MODID, "vagabond")
                ))
        );
}
