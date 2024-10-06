package com.cursee.pet_health_regen;

import com.cursee.monolib.core.MonoLibConfiguration;
import com.cursee.monolib.core.sailing.Sailing;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class PetHealthRegenForge {
    
    public PetHealthRegenForge() {
    
        PetHealthRegen.init();
        Sailing.register(Constants.MOD_NAME, Constants.MOD_ID, Constants.MOD_VERSION, Constants.MC_VERSION_RAW, Constants.PUBLISHER_AUTHOR, Constants.PRIMARY_CURSEFORGE_MODRINTH);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {

        if (!Configuration.enabled) return;

        if (event.phase != TickEvent.Phase.START) return;

        final MinecraftServer server = event.getServer();

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
    }

}