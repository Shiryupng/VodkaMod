package com.miau.vodkamod.item;

import com.miau.vodkamod.config.ModValues;
import com.miau.vodkamod.projectile.ThrownBottleEntity;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

/**
 * Botella vacía: se tira con clic derecho, impacta contra bloques y entidades
 * y se rompe al chocar (con una probabilidad de soltar la botella rota).
 * <p>
 * También es el recipiente que se usa en la mesa de preparación para destilar
 * vodka y la botella que devuelve al beberse cualquier bebida del mod.
 */
public class VodkaBottleItem extends Item implements ProjectileItem {

    public VodkaBottleItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        level.playSound(
            null,
            player.getX(),
            player.getY(),
            player.getZ(),
            SoundEvents.SNOWBALL_THROW,
            SoundSource.NEUTRAL,
            ModValues.BOTTLE_THROW_VOLUME,
            0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
        );

        if (level instanceof ServerLevel serverLevel) {
            Projectile.spawnProjectileFromRotation(
                ThrownBottleEntity::new,
                serverLevel,
                stack,
                player,
                0.0F,
                ModValues.BOTTLE_SHOOT_POWER,
                1.0F
            );
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        stack.consume(1, player);
        return InteractionResult.SUCCESS;
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack stack, Direction direction) {
        return new ThrownBottleEntity(level, position.x(), position.y(), position.z(), stack);
    }
}
