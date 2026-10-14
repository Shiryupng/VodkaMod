package com.miau.vodkamod.registry;

import com.miau.vodkamod.VodkaMod;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Pestaña creativa "Vodka Mod".
 * <p>
 * Orden de los items: botella vacía, vodka, vodka premium, botella rota.
 */
public final class ModCreativeTabs {
    private ModCreativeTabs() {
        // Solo contiene registros; no se instancia.
    }

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VodkaMod.MODID);

    public static final RegistryObject<CreativeModeTab> VODKA_TAB = CREATIVE_MODE_TABS.register("vodka_tab",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.vodka_tab"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.VODKA_BOTTLE.get().getDefaultInstance())
            .displayItems((_, output) -> {
                output.accept(ModItems.VODKA_BOTTLE.get());
                output.accept(ModItems.VODKA.get());
                output.accept(ModItems.PREMIUM_VODKA.get());
                output.accept(ModItems.BROKEN_BOTTLE.get());
                output.accept(ModItems.VAGABOND_SPAWN_EGG.get());
            })
            .build()
    );
}
