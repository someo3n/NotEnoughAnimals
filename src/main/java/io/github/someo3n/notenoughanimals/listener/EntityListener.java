package io.github.someo3n.notenoughanimals.listener;

import io.github.someo3n.notenoughanimals.NotEnoughAnimals;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityEnterLoveModeEvent;
import org.bukkit.event.world.EntitiesUnloadEvent;

public class EntityListener implements Listener {
    private final NotEnoughAnimals plugin;

    public EntityListener(NotEnoughAnimals plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityEnterLoveMode(EntityEnterLoveModeEvent event) {
        plugin.setDomestic(event.getEntity(), true);
    }

    @EventHandler
    public void onEntityBreed(EntityBreedEvent event) {
        plugin.setDomestic(event.getEntity(), true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntitiesUnload(EntitiesUnloadEvent event) {
        for (Entity entity : event.getEntities())
            if (plugin.isEntityApplicableForDespawn(entity))
                entity.remove();
    }
}
