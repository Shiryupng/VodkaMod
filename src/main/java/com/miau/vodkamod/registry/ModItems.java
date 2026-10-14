package com.miau.vodkamod.registry;

import com.miau.vodkamod.VodkaMod;
import com.miau.vodkamod.config.ModValues;
import com.miau.vodkamod.effects.DrinkEffects;
import com.miau.vodkamod.item.VodkaBottleItem;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Weapon;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registro de los cuatro items del mod.
 * <p>
 * <b>Importante:</b> el orden importa. {@code vodka_bottle} se registra el
 * primero porque {@code vodka} y {@code premium_vodka} lo referencian al
 * construirse (devuelven la botella vacía al beber).
 */
public final class ModItems {
    private ModItems() {
        // Solo contiene registros; no se instancia.
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, VodkaMod.MODID);

    /** Botella vacía: se fabrica, sirve de recipiente de destilación y se puede lanzar. */
    public static final RegistryObject<Item> VODKA_BOTTLE = ITEMS.register("vodka_bottle",
        () -> new VodkaBottleItem(new Item.Properties()
            .setId(ITEMS.key("vodka_bottle"))
            .stacksTo(16))
    );

    /** Vodka: se obtiene destilando una patata con una botella vacía. */
    public static final RegistryObject<Item> VODKA = ITEMS.register("vodka",
        () -> new Item(new Item.Properties()
            .setId(ITEMS.key("vodka"))
            .stacksTo(1)
            .component(DataComponents.CONSUMABLE, DrinkEffects.VODKA)
            // Al beberse devuelve exactamente una botella vacía.
            .usingConvertsTo(VODKA_BOTTLE.get()))
    );

    /** Vodka premium: se obtiene destilando una patata venenosa con una botella vacía. */
    public static final RegistryObject<Item> PREMIUM_VODKA = ITEMS.register("premium_vodka",
        () -> new Item(new Item.Properties()
            .setId(ITEMS.key("premium_vodka"))
            .stacksTo(1)
            .component(DataComponents.CONSUMABLE, DrinkEffects.PREMIUM_VODKA)
            .usingConvertsTo(VODKA_BOTTLE.get()))
    );

    /** Botella rota: arma cuerpo a cuerpo improvisada con durabilidad limitada. */
    public static final RegistryObject<Item> BROKEN_BOTTLE = ITEMS.register("broken_bottle",
        () -> new Item(new Item.Properties()
            .setId(ITEMS.key("broken_bottle"))
            .durability(ModValues.BROKEN_BOTTLE_DURABILITY)
            // Hace que cada golpe consuma usos y que el item se rompa al llegar a 0.
            .component(DataComponents.WEAPON, new Weapon(ModValues.BROKEN_BOTTLE_DAMAGE_PER_ATTACK))
            .attributes(createBrokenBottleAttributes()))
    );

    /**
     * Huevo de spawn del vagabundo: permite invocarlo con clic derecho (o desde
     * la pestaña creativa) sin escribir el comando {@code /summon}.
     * <p>
     * El tipo de entidad se guarda en el componente {@code entity_data} del item;
     * los modelos/definiciones están en {@code assets/vodka_mod/items/} y
     * {@code assets/vodka_mod/models/item/}.
     */
    public static final RegistryObject<Item> VAGABOND_SPAWN_EGG = ITEMS.register("vagabond_spawn_egg",
        () -> new SpawnEggItem(new Item.Properties()
            .setId(ITEMS.key("vagabond_spawn_egg"))
            .spawnEgg(ModEntityTypes.VAGABOND.get()))
    );

    private static ItemAttributeModifiers createBrokenBottleAttributes() {
        return ItemAttributeModifiers.builder()
            .add(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(
                    Item.BASE_ATTACK_DAMAGE_ID,
                    ModValues.BROKEN_BOTTLE_ATTACK_DAMAGE,
                    AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.MAINHAND
            )
            .add(
                Attributes.ATTACK_SPEED,
                new AttributeModifier(
                    Item.BASE_ATTACK_SPEED_ID,
                    ModValues.BROKEN_BOTTLE_ATTACK_SPEED,
                    AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.MAINHAND
            )
            .build();
    }
}
