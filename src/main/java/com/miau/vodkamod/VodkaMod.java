package com.miau.vodkamod;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import com.miau.vodkamod.registry.ModCreativeTabs;
import com.miau.vodkamod.registry.ModEntityTypes;
import com.miau.vodkamod.registry.ModItems;
import com.miau.vodkamod.worldgen.ModFeatures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Punto de entrada del mod Vodka.
 * <p>
 * Registra los items, la entidad del proyectil y la pestaña creativa, y
 * conecta las recetas de destilación. El resto de la lógica vive en los
 * paquetes {@code registry}, {@code item}, {@code projectile},
 * {@code brewing}, {@code effects} y {@code config}.
 */
@Mod(VodkaMod.MODID)
public final class VodkaMod {
    public static final String MODID = "vodka_mod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public VodkaMod(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        ModItems.ITEMS.register(modBusGroup);
        ModEntityTypes.ENTITY_TYPES.register(modBusGroup);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modBusGroup);
        ModFeatures.FEATURES.register(modBusGroup);

        LOGGER.info("Vodka Mod cargado");
    }
}
