package com.cursee.pet_health_regen.mixin;

import com.cursee.pet_health_regen.Configuration;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(AbstractHorse.class)
public class ForgeAbstractHorseMixin extends Animal implements ContainerListener, HasCustomInventoryScreen, OwnableEntity, PlayerRideableJumping, Saddleable {

    protected ForgeAbstractHorseMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void injected$onTick(CallbackInfo ci) {

        if (!Configuration.enabled) return;

        AbstractHorse cat = (AbstractHorse) (Object) this;

        if (cat.getOwner() == null) return;

        Difficulty difficulty = cat.level().getDifficulty();

        switch (difficulty) {
            case PEACEFUL -> {
                if (Configuration.max_difficulty >= 0L) {
                    cat.heal(0.1f);
                }
            }
            case EASY -> {
                if (Configuration.max_difficulty >= 1L) {
                    cat.heal(0.1f);
                }
            }
            case NORMAL -> {
                if (Configuration.max_difficulty >= 2L) {
                    cat.heal(0.1f);
                }
            }
            case HARD -> {
                if (Configuration.max_difficulty >= 3L) {
                    cat.heal(0.1f);
                }
            }
        }
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void containerChanged(Container container) {

    }

    @Override
    public void openCustomInventoryScreen(Player player) {

    }

    @Override
    public @Nullable UUID getOwnerUUID() {
        return null;
    }

    @Override
    public void onPlayerJump(int i) {

    }

    @Override
    public boolean canJump() {
        return false;
    }

    @Override
    public void handleStartJump(int i) {

    }

    @Override
    public void handleStopJump() {

    }

    @Override
    public boolean isSaddleable() {
        return false;
    }

    @Override
    public void equipSaddle(@Nullable SoundSource soundSource) {

    }

    @Override
    public boolean isSaddled() {
        return false;
    }
}
