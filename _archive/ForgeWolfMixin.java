package com.cursee.pet_health_regen.mixin;

import com.cursee.pet_health_regen.Configuration;
import com.cursee.pet_health_regen.Constants;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(Wolf.class)
public class ForgeWolfMixin extends TamableAnimal implements NeutralMob {

    static {
        System.out.println("accessed ForgeWolfMixin");
    }

    protected ForgeWolfMixin(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void injected$onTick(CallbackInfo ci) {

        Constants.LOG.info("Ticking on a Wolf, on the server.");

        if (!Configuration.enabled) return;

        Wolf cat = (Wolf) (Object) this;

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
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int i) {

    }

    @Override
    public @Nullable UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uUID) {

    }

    @Override
    public void startPersistentAngerTimer() {

    }
}
