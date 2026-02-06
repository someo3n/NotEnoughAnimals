package io.github.someo3n.notenoughanimals.listener;

import io.github.someo3n.notenoughanimals.NotEnoughAnimals;
import io.github.someo3n.notenoughanimals.SpawnRule;
import io.github.someo3n.notenoughanimals.Utility;
import org.bukkit.Chunk;
import org.bukkit.GameRules;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.List;
import java.util.Random;

public class ChunkListener implements Listener {
    private final NotEnoughAnimals plugin;

    public ChunkListener(NotEnoughAnimals plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChunkLoad(ChunkLoadEvent event) {
        int entityCount = plugin.getHowManyEntitiesToSpawn();
        if (entityCount < 1) return;

        Chunk chunk = event.getChunk();
        if (Boolean.FALSE.equals(chunk.getWorld().getGameRuleValue(GameRules.SPAWN_MOBS)))
            return;

        Random random = plugin.getRandom();
        List<SpawnRule> rules = plugin.getSpawnRules();

        for (int i = 0; i < entityCount; i++)
            Utility.randomChoice(rules, random).trySpawnEntity(plugin, chunk);
    }
}
