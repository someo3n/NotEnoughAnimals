package io.github.someo3n.notenoughanimals;

import io.github.someo3n.notenoughanimals.command.GlowCommand;
import io.github.someo3n.notenoughanimals.command.ReloadCommand;
import io.github.someo3n.notenoughanimals.listener.ChunkListener;
import io.github.someo3n.notenoughanimals.listener.EntityListener;
import io.papermc.paper.entity.Leashable;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class NotEnoughAnimals extends JavaPlugin {
    private static final String MARKED_TAG = "_m";
    private static final String DOMESTIC_TAG = "_d";

    private Random random = new Random();

    private boolean entitiesSpawnedGlow;
    private float spawningChunkChance;
    private int minSpawnTries;
    private int maxSpawnTries;
    private List<SpawnRule> spawnRules = new ArrayList<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();

        getCommand("neaglow").setExecutor(new GlowCommand(this));
        getCommand("neareload").setExecutor(new ReloadCommand(this));

        registerListeners();
        initMetrics();
    }

    public void reloadConfig() {
        super.reloadConfig();
        random = new Random();

        Configuration config = getConfig();
        entitiesSpawnedGlow = false;
        spawningChunkChance = (float) config.getDouble("spawning-chunk-chance", 0.10f);
        minSpawnTries = config.getInt("spawn-tries.min", 0);
        maxSpawnTries = Math.abs(config.getInt("spawn-tries.max", 3)+1);
        spawnRules = SpawnRule.fromConfigurationSection(config);
    }

    private void initMetrics() {
        Metrics metrics = new Metrics(this, 29402);
    }

    private void registerListeners() {
        for (Listener listener : List.of(
                new ChunkListener(this),
                new EntityListener(this)
        )) getServer().getPluginManager().registerEvents(listener, this);
    }

    public Random getRandom() {
        return random;
    }

    public boolean isMarked(Entity entity) {
        return entity.getScoreboardTags().contains(MARKED_TAG);
    }

    public void setMarked(Entity entity, boolean marked) {
        if (marked) entity.addScoreboardTag(MARKED_TAG);
        else entity.getScoreboardTags().remove(MARKED_TAG);
    }

    public boolean isDomestic(Entity entity) {
        return entity.getScoreboardTags().contains(DOMESTIC_TAG);
    }

    public void setDomestic(Entity entity, boolean domestic) {
        if (domestic) entity.addScoreboardTag(DOMESTIC_TAG);
        else entity.getScoreboardTags().remove(DOMESTIC_TAG);
    }

    public boolean isEntityApplicableForDespawn(Entity entity) {
        return isMarked(entity) &&
                entity.getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL &&
                !isDomestic(entity) &&
                entity.getCustomName() == null &&
                entity.getVehicle() == null &&
                entity.getPassengers().isEmpty() &&
                !(entity instanceof Tameable tame && tame.isTamed()) &&
                !(entity instanceof Leashable leashable && leashable.isLeashed()) &&
                !Utility.hasEquipment(entity) &&
                (!(entity instanceof Ageable ageable) || ageable.isAdult());
    }

    public int getHowManyEntitiesToSpawn() {
        if (random.nextFloat() > spawningChunkChance) return 0;
        return minSpawnTries + random.nextInt(maxSpawnTries);
    }

    public List<SpawnRule> getSpawnRules() {
        return spawnRules;
    }

    public boolean isEntitiesSpawnedGlow() {
        return entitiesSpawnedGlow;
    }

    public void setEntitiesSpawnedGlow(boolean entitiesSpawnedGlow) {
        this.entitiesSpawnedGlow = entitiesSpawnedGlow;
    }
}
