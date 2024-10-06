package com.cursee.pet_health_regen.mixin;

import com.cursee.pet_health_regen.Configuration;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Cat.class)
public class ForgeCatMixin extends TamableAnimal implements VariantHolder<CatVariant> {

    protected ForgeCatMixin(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void injected$onTick(CallbackInfo ci) {

        if (!Configuration.enabled) return;

        Cat cat = (Cat) (Object) this;

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
    public void setVariant(CatVariant object) {

    }

    @Override
    public CatVariant getVariant() {
        return null;
    }
}
