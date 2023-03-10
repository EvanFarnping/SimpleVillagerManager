package me.evanf.removevillagers.manager;

import me.evanf.removevillagers.RemoveVillagers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class VillagerManager implements Listener {

    public VillagerManager(RemoveVillagers main) {
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onAnyChunkLoad(ChunkLoadEvent chunkEvent) {
        if (RemoveVillagers.config.getBoolean("on_any_chunk")) {
            onChunkLoad(chunkEvent);
        }
    }

    @EventHandler
    public void onNewVillagerChunkLoad(ChunkLoadEvent chunkEvent) {
        if (RemoveVillagers.config.getBoolean("only_on_new_chunk")) {
            onNewChunkLoad(chunkEvent);
        }
    }

    @EventHandler
    public void onVillagerBreed(EntityBreedEvent event) {
        if (RemoveVillagers.config.getBoolean("disable_breed")) {
            disableBreed(event);
        }
    }

    @EventHandler
    public void onEntityTransform(EntityTransformEvent event) {
        if (RemoveVillagers.config.getBoolean("disable_transform")) {
            disableCures(event);
        }
    }

    @EventHandler
    public void onVillagerInteract(PlayerInteractEntityEvent event) {
        if (RemoveVillagers.config.getBoolean("disable_right-click")) {
            onPlayerInteractEntity(event);
        }
    }

    private void onChunkLoad(ChunkLoadEvent chunkEvent) {
        for (Entity entity : chunkEvent.getChunk().getEntities()) {
            if (chunkEvent.getChunk().isLoaded()) {
                if (entity instanceof Villager) {
                    entity.remove();
                }
            }
        }
    }

    private void onNewChunkLoad(ChunkLoadEvent chunkEvent) {
        for (Entity entity : chunkEvent.getChunk().getEntities()) {
            if (chunkEvent.getChunk().isLoaded()
                    && chunkEvent.isNewChunk()) {
                if (entity instanceof Villager) {
                    entity.remove();
                }
            }
        }
    }

    private void disableBreed(EntityBreedEvent event) {
        if (Villager.class.isAssignableFrom(event.getEntity().getClass())) {
            event.setCancelled(true);
        }
    }

    private void disableCures(EntityTransformEvent event) {
        if (event.getEntity() instanceof ZombieVillager
                && event.getTransformReason()
                == EntityTransformEvent.TransformReason.CURED) {
            event.setCancelled(true);
        }
    }

    private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.VILLAGER) {
            event.setCancelled(true);
        }
    }
}