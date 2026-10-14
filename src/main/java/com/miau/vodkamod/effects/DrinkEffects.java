package com.miau.vodkamod.effects;

import java.util.List;

import com.miau.vodkamod.config.ModValues;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

/**
 * Efectos que aplican las bebidas del mod al terminar de beberse.
 * <p>
 * Todos los segundos y niveles vienende {@link ModValues}, así que para
 * equilibrar el mod basta con editar esa clase.
 */
public final class DrinkEffects {
    private DrinkEffects() {
        // Solo contiene constantes; no se instancia.
    }

    /** Efectos de la vodka normal: Fuerza I, Nauseas, Debilidad. */
    public static final Consumable VODKA = Consumables.defaultDrink()
        .consumeSeconds(ModValues.DRINK_CONSUME_SECONDS)
        .onConsume(new ApplyStatusEffectsConsumeEffect(List.of(
            effect(MobEffects.STRENGTH, ModValues.VODKA_STRENGTH_SECONDS, ModValues.VODKA_STRENGTH_LEVEL),
            effect(MobEffects.NAUSEA, ModValues.VODKA_NAUSEA_SECONDS, ModValues.VODKA_NAUSEA_LEVEL),
            effect(MobEffects.WEAKNESS, ModValues.VODKA_WEAKNESS_SECONDS, ModValues.VODKA_WEAKNESS_LEVEL)
        )))
        .build();

    /** Efectos de la vodka premium: Fuerza II, Resistencia, Nauseas, Debilidad. */
    public static final Consumable PREMIUM_VODKA = Consumables.defaultDrink()
        .consumeSeconds(ModValues.DRINK_CONSUME_SECONDS)
        .onConsume(new ApplyStatusEffectsConsumeEffect(List.of(
            effect(MobEffects.STRENGTH, ModValues.PREMIUM_VODKA_STRENGTH_SECONDS, ModValues.PREMIUM_VODKA_STRENGTH_LEVEL),
            effect(MobEffects.RESISTANCE, ModValues.PREMIUM_VODKA_RESISTANCE_SECONDS, ModValues.PREMIUM_VODKA_RESISTANCE_LEVEL),
            effect(MobEffects.NAUSEA, ModValues.PREMIUM_VODKA_NAUSEA_SECONDS, ModValues.PREMIUM_VODKA_NAUSEA_LEVEL),
            effect(MobEffects.WEAKNESS, ModValues.PREMIUM_VODKA_WEAKNESS_SECONDS, ModValues.PREMIUM_VODKA_WEAKNESS_LEVEL)
        )))
        .build();

    /** Crea un efecto a partir de segundos y nivel (nivel 0 = efecto I). */
    private static MobEffectInstance effect(Holder<MobEffect> mobEffect, int seconds, int level) {
        return new MobEffectInstance(mobEffect, ModValues.ticks(seconds), level);
    }
}
