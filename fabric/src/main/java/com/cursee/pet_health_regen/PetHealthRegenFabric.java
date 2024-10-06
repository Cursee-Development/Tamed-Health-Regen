package com.cursee.pet_health_regen;

import com.cursee.monolib.core.MonoLibConfiguration;
import com.cursee.monolib.core.sailing.Sailing;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;

public class PetHealthRegenFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {

        PetHealthRegen.init();
        Sailing.register(Constants.MOD_NAME, Constants.MOD_ID, Constants.MOD_VERSION, Constants.MC_VERSION_RAW, Constants.PUBLISHER_AUTHOR, Constants.PRIMARY_CURSEFORGE_MODRINTH);

        ServerTickEvents.START_SERVER_TICK.register((server -> {

            if (!Configuration.enabled) return;

            if (server.getTickCount() % 20 != 0) return;

            server.getAllLevels().forEach(level -> {

                boolean shouldHeal = switch (level.getDifficulty()) {
                    case PEACEFUL -> Configuration.max_difficulty >= 0L;
                    case EASY -> Configuration.max_difficulty >= 1L;
                    case NORMAL -> Configuration.max_difficulty >= 2L;
                    case HARD -> Configuration.max_difficulty >= 3L;
                };

                if (!shouldHeal) return;

                level.getAllEntities().forEach(entity -> {

                    if (!(entity instanceof LivingEntity livingEntity)) return;
                    if (!(entity instanceof OwnableEntity ownableEntity) || ownableEntity.getOwner() == null) return;

                    livingEntity.heal(0.5f);
                });
            });
        }));
    }
}
