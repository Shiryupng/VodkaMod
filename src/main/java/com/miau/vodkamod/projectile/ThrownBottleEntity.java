package com.miau.vodkamod.projectile;

import com.miau.vodkamod.config.ModValues;
import com.miau.vodkamod.registry.ModEntityTypes;
import com.miau.vodkamod.registry.ModItems;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * Botella lanzada: daña a la entidad que impacta, se rompe contra cualquier
 * cosa (bloque o entidad) y, con la probabilidad de
 * {@link ModValues#BROKEN_BOTTLE_DROP_CHANCE}, suelta una botella rota.
 */
public class ThrownBottleEntity extends ThrowableItemProjectile {

    /** Identificador del evento de "la botella se rompió" (partículas en cliente). */
    private static final byte BREAK_EVENT = 3;

    public ThrownBottleEntity(EntityType<? extends ThrownBottleEntity> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownBottleEntity(Level level, LivingEntity shooter, ItemStack itemStack) {
        super(ModEntityTypes.THROWN_BOTTLE.get(), shooter, level, itemStack);
    }

    public ThrownBottleEntity(Level level, double x, double y, double z, ItemStack itemStack) {
        super(ModEntityTypes.THROWN_BOTTLE.get(), x, y, z, level, itemStack);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.VODKA_BOTTLE.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        Entity target = hitResult.getEntity();
        target.hurt(
            this.damageSources().thrown(this, this.getOwner()),
            ModValues.BOTTLE_PROJECTILE_DAMAGE
        );
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level().isClientSide()) {
            return;
        }

        this.level().broadcastEntityEvent(this, BREAK_EVENT);
        this.level().playSound(
            null,
            this.getX(),
            this.getY(),
            this.getZ(),
            SoundEvents.GLASS_BREAK,
            SoundSource.PLAYERS,
            1.0F,
            1.0F
        );
        this.dropBrokenBottle();
        this.discard();
    }

    /** Partículas de vidrio rotas al recibir el evento de rotura. */
    @Override
    public void handleEntityEvent(byte id) {
        if (id != BREAK_EVENT) {
            super.handleEntityEvent(id);
            return;
        }

        ItemStack itemStack = this.getItem();
        ParticleOptions particle = itemStack.isEmpty()
            ? ParticleTypes.ITEM_SNOWBALL
            : new ItemParticleOption(ParticleTypes.ITEM, ItemStackTemplate.fromNonEmptyStack(itemStack));

        for (int i = 0; i < 8; i++) {
            this.level().addParticle(particle, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    /** Suelta la botella rota (si pasa la probabilidad configurada). */
    private void dropBrokenBottle() {
        if (this.level().getRandom().nextFloat() < ModValues.BROKEN_BOTTLE_DROP_CHANCE) {
            Containers.dropItemStack(
                this.level(),
                this.getX(),
                this.getY(),
                this.getZ(),
                new ItemStack(ModItems.BROKEN_BOTTLE.get())
            );
        }
    }
}
