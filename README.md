# Vodka Mod (Forge 26.3)

Mod de destilación y consumo de vodka para **Minecraft 26.3 / Forge 26.3-66.0.9**.
Java 25, Gradle, estructura oficial del MDK de Forge 26.3.

| Contenido | Descripción |
|---|---|
| `vodka_mod:vodka_bottle` | Botella vacía: se fabrica, es el recipente de la mesa de preparación, se puede lanzar y se devuelve al beber |
| `vodka_mod:vodka` | Vodka: destilado de patata + botella |
| `vodka_mod:premium_vodka` | Vodka premium: destilado de patata venenosa + botella |
| `vodka_mod:broken_bottle` | Botella rota: arma cuerpo a cuerpo con durabilidad |
| `vodka_mod:vagabond` | Vagabundo: mob hostil que lleva la botella rota y suelta vodka (50 %) |
| `vodka_mod:vagabond_spawn_egg` | Huevo de spawn: invoca al vagabundo sin escribir comandos |
| Pestaña `Vodka Mod` | Botella, vodka, vodka premium, botella rota y huevo de vagabundo (en ese orden) |

---

## 1. Importar en IntelliJ IDEA

1. `File ▸ Open…` y selecciona la **carpeta del proyecto** (la que contiene `build.gradle`). Elige **Open as Project**.
2. IntelliJ detecta Gradle y empieza a sincronizar (descarga Forge 26.3 la primera vez: varios minutos).
3. Si pregunta por el JDK, elige **Java 25** (`C:\Users\videojuegos\.jdks\temurin-25.0.4.1`).
4. Si Gradle no encuentra Java, define la variable de entorno `JAVA_HOME`:
   `JAVA_HOME=C:\Users\videojuegos\.jdks\temurin-25.0.4.1`

## 2. Ejecutar en modo desarrollo

Desde IntelliJ (pestaña *Gradle* ▸ `Tasks ▸ forgeGradle ▸ runClient`)
o desde una terminal en la carpeta del proyecto:

```powershell
# Windows (PowerShell)
$env:JAVA_HOME = 'C:\Users\videojuegos\.jdks\temurin-25.0.4.1'
.\gradlew.bat runClient
```

Otros runs disponibles: `runServer`, `runData`.

## 3. Compilar y encontrar el .jar

```powershell
.\gradlew.bat build
```

El mod compilado queda en:

```
build\libs\vodka-mod-1.0.0.jar
```

Para instalarlo: cópia ese `.jar` en la carpeta `mods` de tu perfil de Forge 26.3.

---

## 4. Cómo se juega

1. **Fabricar la botella**: 4 bloques de `minecraft:glass` en un cuadrado 2×2 en la mesa de trabajo → 1 botella vacía.
2. **Destilar**: pon la botella vacía en el hueco de abajo de la **mesa de preparación**, una **patata** en el de arriba y blaze powder de combustible → **vodka**. Con **patata venenosa** → **vodka premium**. (La receta NO usa `minecraft:glass_bottle`.)
3. **Beber**: clic derecho mantenido. Al terminar aplicará los efectos y **devolverá exactamente una botella vacía** (la botella nunca se pierde, aunque tengas el inventario lleno).
4. **Lanzar**: clic derecho con la botella vacía en la mano. Impacta contra bloques y entidades (4 de daño = 2 corazones), se rompe y tiene un **50 % de probabilidad** de soltar una **botella rota**.
5. **Botella rota**: arma cuerpo a cuerpo improvisada (5 de daño total, velocidad 2.0), pierde durabilidad al golpear y **se rompe a los 8 golpes**.
6. **Vagabundo**: mob hostil que lleva una botella rota en la mano (con la **misma rotación que el jugador**) y ataca cuerpo a cuerpo. Al morir tiene un **50 % de probabilidad** de soltar **vodka**. Para invocarlo tienes dos opciones:

   - **Huevo de spawn**: usa el item **Huevo de vagabundo** (está en la pestaña *Vodka Mod*) con clic derecho sobre el suelo.
   - **Comando**:

     ```
     /summon vodka_mod:vagabond
     ```

---

## 5. Dónde cambiar los valores (todo centralizado)

Archivo: **`src/main/java/com/miau/vodkamod/config/ModValues.java`**

| Constante | Valor por defecto | Qué controla |
|---|---|---|
| `BOTTLE_PROJECTILE_DAMAGE` | `4.0F` | Daño de la botella lanzada |
| `BOTTLE_SHOOT_POWER` | `1.5F` | Fuerza del lanzamiento |
| `BROKEN_BOTTLE_DROP_CHANCE` | `0.5F` | Probabilidad de soltar la botella rota (0.0–1.0) |
| `BROKEN_BOTTLE_DURABILITY` | `8` | Usos de la botella rota |
| `BROKEN_BOTTLE_DAMAGE_PER_ATTACK` | `1` | Usos que resta cada golpe |
| `BROKEN_BOTTLE_ATTACK_DAMAGE` | `4.0` | Daño añadido del arma (total 5.0) |
| `BROKEN_BOTTLE_ATTACK_SPEED` | `-2.0` | Velocidad de ataque (total 2.0) |
| `VODKA_*_SECONDS` / `VODKA_*_LEVEL` | 30/10/20 s, nivel 0 | Efectos del vodka |
| `PREMIUM_VODKA_*_SECONDS` / `PREMIUM_VODKA_*_LEVEL` | 45/30/30/30 s, nivel 1/0/0/0 | Efectos del vodka premium |
| `DRINK_CONSUME_SECONDS` | `1.6F` | Tiempo de beber |
| `VAGABOND_MAX_HEALTH` | `20.0` | Vida del vagabundo (10 corazones) |
| `VAGABOND_MOVEMENT_SPEED` | `0.25` | Velocidad del vagabundo |
| `VAGABOND_ATTACK_DAMAGE` | `2.0` | Daño base (la botella rota suma 4.0) |
| `VAGABOND_FOLLOW_RANGE` | `24.0` | Distancia a la que persigue al jugador |
| `VAGABOND_HELD_ITEM_DROP_CHANCE` | `0.0F` | Si el vagabundo suelta su botella al morir |
| `VAGABOND_VODKA_DROP_CHANCE` | `0.5` | Probabilidad de soltar vodka (ver loot table) |

> El botín del vagabundo está en
> **`src/main/resources/data/vodka_mod/loot_table/entities/vagabond.json`**
> (probabilidad `0.5`). Si cambias `VAGABOND_VODKA_DROP_CHANCE`, cambia también
> el `chance` del JSON.

Los efectos se aplican en **`effects/DrinkEffects.java`** (si quieres añadir o quitar efectos) y las recetas de destilación en los JSON de **`data/vodka_mod/recipe/`** (formato datapack de 26.3).

Efectos actuales:

* **Vodka**: Fuerza I 30 s, Nauseas 10 s, Debilidad I 20 s.
* **Vodka premium**: Fuerza II 45 s, Resistencia I 30 s, Nauseas 30 s, Debilidad I 30 s.

## 6. Cambiar las texturas (placeholders)

Las texturas actuales son de relleno (16×16, generadas). Sustituye estos archivos
manteniendo el nombre y el tamaño:

```
src/main/resources/assets/vodka_mod/textures/item/vodka_bottle.png
src/main/resources/assets/vodka_mod/textures/item/vodka.png
src/main/resources/assets/vodka_mod/textures/item/premium_vodka.png
src/main/resources/assets/vodka_mod/textures/item/broken_bottle.png
```

Tras copiar la nueva imagen, reinicia el cliente de desarrollo (o usa F3+T).

### Opción rápida con script (recomendada)

Si tu imagen no es de 16×16, usa el script incluido; la redimensiona y la coloca
en su sitio automáticamente:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\tools\update_texture.ps1 `
  -Source "C:\ruta\a\tu\imagen.png" -Target vodka_bottle
```

`-Target` admite: `vodka_bottle`, `vodka`, `premium_vodka`, `broken_bottle`.
Luego reinicia `runClient` (o pulsa **F3+T** en el juego) para ver el cambio.

### Textura del vagabundo

El vagabundo no usa una textura de item de 16×16, sino una **skin de jugador de
64×64** (formato de skin de Minecraft). El renderer ya apunta a una textura
propia del mod:

**`src/main/resources/assets/vodka_mod/textures/entity/vagabond.png`**

Ahora mismo ese archivo contiene una **skin propia del vagabundo** (64×64). Para
cambiarla, **sustituye ese PNG** por otra skin 64×64 y recarga (**F3+T**) o
recompila:

```
src/main/resources/assets/vodka_mod/textures/entity/vagabond.png   (64×64)
```

No hace falta tocar código: `VagabondRenderer.TEXTURE` ya apunta a
`vodka_mod:textures/entity/vagabond.png`. Si quieres usar otro nombre de archivo
o ruta, cambia esa constante.

> El comportamiento del mob está en `VagabondEntity` (IA, sonidos) y sus números
> en `ModValues`; el botín, en la loot table.

---

## 7. Recetas (datapack 26.3)

Todas las recetas viven en `src/main/resources/data/vodka_mod/recipe/`.
En 26.3 el formato cambió respecto a versiones antiguas.

**Botella vacía (2×2 de cristal → 1 botella)** — `vodka_bottle.json`:

```json
{
  "type": "minecraft:crafting_shaped",
  "category": "misc",
  "key": {
    "G": "minecraft:glass"
  },
  "pattern": [
    "GG",
    "GG"
  ],
  "result": {
    "id": "vodka_mod:vodka_bottle",
    "count": 1
  }
}
```

> Importante: la clave (`"G"`) es un **string directo** (`"minecraft:glass"` o
> un tag `"#minecraft:..."`), NO un objeto `{"item": "..."}`. El resultado usa
> `"id"` (no `"item"`).

**Destilación** — `brewing_vodka.json` y `brewing_premium_vodka.json`:

```json
{
  "type": "minecraft:brewing",
  "input": {
    "item": "vodka_mod:vodka_bottle"
  },
  "reagent": {
    "item": "minecraft:potato"
  },
  "output": {
    "id": "vodka_mod:vodka",
    "count": 1
  }
}
```

- `input` = el recipiente que va en el hueco inferior de la mesa de preparación.
- `reagent` = el ingrediente del hueco superior (`minecraft:potato` o `minecraft:poisonous_potato`).
- `output` = el item resultante.

Como estas recetas son datapacks, al añadir una nueva basta con crear otro JSON
en esa carpeta; el juego registra automáticamente el recipiente y el reactivo
como válidos en la mesa de preparación.

---

## 8. Estructura de paquetes

```
src/main/java/com/miau/vodkamod/
├── VodkaMod.java                 Punto de entrada (@Mod)
├── config/ModValues.java         Todos los valores ajustables
├── registry/ModItems.java        Registro de los 4 items
├── registry/ModEntityTypes.java  Registro del proyectil y del vagabundo
├── registry/ModCreativeTabs.java Pestaña creativa "Vodka Mod"
├── item/VodkaBottleItem.java     Botella vacía: lanzamiento + proyectil
├── projectile/ThrownBottleEntity.java  Proyectil: daño, rotura, drop
├── entity/VagabondEntity.java    Vagabundo: mob con botella rota
├── event/ModEntityEvents.java    Atributos de los mobs (evento Forge)
├── effects/DrinkEffects.java     Efectos al beber
└── client/
    ├── ClientModEvents.java      Modelos y renderers (cliente)
    └── VagabondRenderer.java     Renderer del vagabundo
```

> Las recetas de destilación y la de la botella vacía ya **no** son una clase Java:
> en 26.3 se declaran como datapacks JSON en `data/vodka_mod/recipe/`.

Recursos:

```
src/main/resources/
├── META-INF/mods.toml
├── pack.mcmeta
├── assets/vodka_mod/items/       Definiciones de item (26.x)
├── assets/vodka_mod/models/item/ Modelos
├── assets/vodka_mod/textures/item/ Texturas de items (placeholders, incl. vagabond_spawn_egg.png)
├── assets/vodka_mod/textures/entity/vagabond.png  Skin del vagabundo (64×64)
├── assets/vodka_mod/lang/        en_us.json y es_es.json
├── data/vodka_mod/recipe/        Recetas: botella (crafting) y destilación (brewing)
└── data/vodka_mod/loot_table/entities/vagabond.json  Botín del vagabundo
```
