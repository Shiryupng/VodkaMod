package com.miau.vodkamod.entity;

import com.miau.vodkamod.config.ModValues;
import com.miau.vodkamod.registry.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

/**
 * Vagabundo: mob hostil que va con una botella rota en la mano.
 * <p>
 * La botella se lleva en la ranura de la mano principal y se dibuja con el
 * sistema normal de items en la mano ({@code ItemInHandLayer}), por lo que sale
 * con la <b>misma rotación que en la mano de un jugador</b>.
 * <p>
 * Los atributos se registran en {@code event/ModEntityEvents.java} y el botín
 * (vodka al 50 %) está en
 * {@code data/vodka_mod/loot_table/entities/vagabond.json}.
 * <p>
 * Aparece de forma natural por el mundo (biome modifier
 * {@code data/vodka_mod/forge/biome_modifier/vagabond_spawns.json}), incluso de
 * día. Mira {@link #checkSpawnRules} y
 * {@link ModValues#VAGABOND_SPAWN_IN_DAYLIGHT}.
 */
public class VagabondEntity extends Monster {
    public VagabondEntity(EntityType<? extends VagabondEntity> type, Level level) {
        super(type, level);
        // Lleva la botella rota en la mano principal (misma pose que el jugador).
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.BROKEN_BOTTLE.get()));
        // La botella de la mano no cuenta como botín: el botín lo define la loot table.
        this.setDropChance(EquipmentSlot.MAINHAND, ModValues.VAGABOND_HELD_ITEM_DROP_CHANCE);
    }

    /**
     * Atributos del vagabundo. Se llama desde el evento de Forge
     * {@code EntityAttributeCreationEvent}.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, ModValues.VAGABOND_MAX_HEALTH)
            .add(Attributes.MOVEMENT_SPEED, ModValues.VAGABOND_MOVEMENT_SPEED)
            .add(Attributes.ATTACK_DAMAGE, ModValues.VAGABOND_ATTACK_DAMAGE)
            .add(Attributes.FOLLOW_RANGE, ModValues.VAGABOND_FOLLOW_RANGE);
    }

    /**
     * Regla de spawn natural del vagabundo. Se registra desde el evento de Forge
     * {@code SpawnPlacementRegisterEvent} (ver {@code event/ModEntityEvents.java}).
     * <p>
     * Si {@link ModValues#VAGABOND_SPAWN_IN_DAYLIGHT} es {@code true} se usa
     * {@link Monster#checkAnyLightMonsterSpawnRules}, que <b>no</b> exige
     * oscuridad y por tanto permite aparecer de día. Si es {@code false} se usa
     * {@link Monster#checkMonsterSpawnRules}, el comportamiento normal de los
     * monstruos (solo en la oscuridad).
     */
    public static boolean checkSpawnRules(
        final EntityType<VagabondEntity> type,
        final ServerLevelAccessor level,
        final EntitySpawnReason spawnReason,
        final BlockPos pos,
        final RandomSource random
    ) {
        if (ModValues.VAGABOND_SPAWN_IN_DAYLIGHT) {
            return Monster.checkAnyLightMonsterSpawnRules(type, level, spawnReason, pos, random);
        }

        return Monster.checkMonsterSpawnRules(type, level, spawnReason, pos, random);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }
}
