package com.miau.vodkamod.config;

/**
 * Todos los valores ajustables del mod en un solo archivo.
 * <p>
 * Cambia cualquier constante de aquí y recompila: no hace falta tocar nada más.
 * Los valores de efectos se expresan en <b>segundos</b> y en <b>nivel</b>
 * (0 = nivel I, 1 = nivel II, 2 = nivel III...), igual que en vanilla.
 */
public final class ModValues {
    private ModValues() {
        // Clase de constantes; no se instancia.
    }

    // ---------------------------------------------------------------------
    // Botella lanzada (proyectil)
    // ---------------------------------------------------------------------

    /** Daño del impacto de la botella lanzada (4.0 = 2 corazones). */
    public static final float BOTTLE_PROJECTILE_DAMAGE = 4.0F;

    /** Potencia de lanzamiento (1.5 = misma fuerza que una bola de nieve). */
    public static final float BOTTLE_SHOOT_POWER = 1.5F;

    /**
     * Probabilidad de que la botella rota caiga al romperse al impactar.
     * 0.0 = nunca suelta, 1.0 = siempre suelta.
     */
    public static final float BROKEN_BOTTLE_DROP_CHANCE = 0.5F;

    /** Sonido/volumen del lanzamiento. */
    public static final float BOTTLE_THROW_VOLUME = 0.5F;

    // ---------------------------------------------------------------------
    // Botella rota (arma cuerpo a cuerpo)
    // ---------------------------------------------------------------------

    /** Durabilidad de la botella rota: se rompe al llegar a 0 usos. */
    public static final int BROKEN_BOTTLE_DURABILITY = 8;

    /** Usos que resta cada golpe con la botella rota. */
    public static final int BROKEN_BOTTLE_DAMAGE_PER_ATTACK = 1;

    /**
     * Daño añadido del ataque cuerpo a cuerpo (se suma al 1.0 base del jugador).
     * 4.0 -> 5.0 de daño total = 2.5 corazones.
     */
    public static final double BROKEN_BOTTLE_ATTACK_DAMAGE = 4.0D;

    /**
     * Velocidad de ataque añadida (se suma a la 4.0 base del jugador).
     * -2.0 -> 2.0 de velocidad = ágil, tipo "arma improvisada".
     */
    public static final double BROKEN_BOTTLE_ATTACK_SPEED = -2.0D;

    // ---------------------------------------------------------------------
    // Vodka: efectos al beber  (segundos + nivel del efecto)
    // ---------------------------------------------------------------------

    public static final int VODKA_STRENGTH_SECONDS = 30;
    public static final int VODKA_STRENGTH_LEVEL = 0;

    public static final int VODKA_NAUSEA_SECONDS = 10;
    public static final int VODKA_NAUSEA_LEVEL = 0;

    public static final int VODKA_WEAKNESS_SECONDS = 20;
    public static final int VODKA_WEAKNESS_LEVEL = 0;

    // ---------------------------------------------------------------------
    // Vodka premium: efectos al beber  (segundos + nivel del efecto)
    // ---------------------------------------------------------------------

    public static final int PREMIUM_VODKA_STRENGTH_SECONDS = 45;
    public static final int PREMIUM_VODKA_STRENGTH_LEVEL = 1;

    public static final int PREMIUM_VODKA_RESISTANCE_SECONDS = 30;
    public static final int PREMIUM_VODKA_RESISTANCE_LEVEL = 0;

    public static final int PREMIUM_VODKA_NAUSEA_SECONDS = 30;
    public static final int PREMIUM_VODKA_NAUSEA_LEVEL = 0;

    public static final int PREMIUM_VODKA_WEAKNESS_SECONDS = 30;
    public static final int PREMIUM_VODKA_WEAKNESS_LEVEL = 0;

    // ---------------------------------------------------------------------
    // Consumo de las bebidas
    // ---------------------------------------------------------------------

    /** Tiempo que tarda en beberse una botella (1.6 = igual que una poción). */
    public static final float DRINK_CONSUME_SECONDS = 1.6F;

    // ---------------------------------------------------------------------
    // Vagabundo (mob con botella rota)
    // ---------------------------------------------------------------------

    /** Vida máxima del vagabundo (20.0 = 10 corazones). */
    public static final double VAGABOND_MAX_HEALTH = 20.0D;

    /** Velocidad de movimiento (0.25 = como un zombi). */
    public static final double VAGABOND_MOVEMENT_SPEED = 0.25D;

    /**
     * Daño base del vagabundo. La botella rota que lleva añade
     * {@link #BROKEN_BOTTLE_ATTACK_DAMAGE} extra (4.0), así que el golpe real
     * es {@code VAGABOND_ATTACK_DAMAGE + 4.0}.
     */
    public static final double VAGABOND_ATTACK_DAMAGE = 2.0D;

    /** Distancia a la que el vagabundo detecta y persigue a un jugador. */
    public static final double VAGABOND_FOLLOW_RANGE = 24.0D;

    /** Probabilidad de que suelte la botella de la mano al morir (0.0–1.0). */
    public static final float VAGABOND_HELD_ITEM_DROP_CHANCE = 0.0F;

    /**
     * Probabilidad de soltar vodka al morir. Se define aquí por comodidad, pero
     * el valor real está en la loot table
     * {@code data/vodka_mod/loot_table/entities/vagabond.json} ("chance").
     * Mantén ambos valores sincronizados.
     */
    public static final double VAGABOND_VODKA_DROP_CHANCE = 0.5D;

    // ---------------------------------------------------------------------
    // Spawn natural del vagabundo
    // ---------------------------------------------------------------------

    /**
     * Si es {@code true}, el vagabundo puede aparecer también a plena luz del
     * día (al aire libre). Si es {@code false}, solo aparece en la oscuridad,
     * como el resto de monstruos.
     * <p>
     * Esto controla la "regla de spawn" (clase {@code VagabondEntity}); los
     * sitios donde aparece se definen en el biome modifier
     * {@code data/vodka_mod/forge/biome_modifier/vagabond_spawns.json}.
     */
    public static final boolean VAGABOND_SPAWN_IN_DAYLIGHT = true;

    /**
     * Peso del spawn natural (cuanto más alto, más frecuente respecto al resto
     * de monstruos del grupo).
     * <p>
     * Referencia vanilla: zombi {@code 90}, esqueleto/araña/creper {@code 100}.
     * El valor real está en el biome modifier
     * {@code data/vodka_mod/forge/biome_modifier/vagabond_spawns.json} ("weight").
     * Mantén ambos sincronizados.
     */
    public static final int VAGABOND_SPAWN_WEIGHT = 30;

    /**
     * Tamaño mínimo del grupo al aparecer. El valor real está en el biome
     * modifier ("count"), campo {@code min_inclusive}.
     */
    public static final int VAGABOND_SPAWN_MIN = 1;

    /**
     * Tamaño máximo del grupo al aparecer. El valor real está en el biome
     * modifier ("count"), campo {@code max_inclusive}.
     */
    public static final int VAGABOND_SPAWN_MAX = 2;

    /** Convierte segundos a ticks de Minecraft. */

    public static int ticks(int seconds) {
        return seconds * 20;
    }
}
