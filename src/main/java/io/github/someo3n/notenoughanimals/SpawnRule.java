package io.github.someo3n.notenoughanimals;

import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.*;

public record SpawnRule(
        List<World.Environment> environments,
        List<Biome> biomes,
        List<Material> spawnsOnTopOf,
        List<EntityType> entityTypes
) {
    public static List<SpawnRule> fromConfigurationSection(ConfigurationSection section) {
        List<SpawnRule> rules = new ArrayList<>();

        if (!section.isList("spawn-rules")) return rules;

        for (Map<?, ?> map : section.getMapList("spawn-rules")) {
            // Environments
            List<String> envStrings = (List<String>) map.getOrDefault("environments", null);
            List<World.Environment> environments = envStrings.stream()
                    .map(s -> World.Environment.valueOf(s.toUpperCase()))
                    .toList();

            // Biomes
            List<String> biomeStrings = (List<String>) map.getOrDefault("biomes", null);
            List<Biome> biomes = biomeStrings.stream()
                    .map(name -> {
                        try {
                            return (Biome) Biome.class.getField(name.toUpperCase()).get(null);
                        } catch (ReflectiveOperationException e) {
                            throw new IllegalArgumentException("Unknown biome: " + name, e);
                        }
                    })
                    .toList();

            // Entity types
            List<String> typeStrings = (List<String>) map.getOrDefault("types", null);
            List<EntityType> entityTypes = typeStrings.stream()
                    .map(s -> EntityType.valueOf(s.toUpperCase()))
                    .toList();

            // On-top-of blocks
            List<String> topStrings = (List<String>) map.getOrDefault("on-top-of", null);
            if (topStrings == null) topStrings = List.of("#ANIMALS_SPAWNABLE_ON");
            List<Material> spawnsOnTopOf = new ArrayList<>();
            for (String entry : topStrings) {
                if (!entry.startsWith("#")) {
                    spawnsOnTopOf.add(Material.valueOf(entry.toUpperCase()));
                } else {
                    String tagName = entry.substring(1).toUpperCase();
                    try {
                        var field = Tag.class.getField(tagName);
                        Object value = field.get(null);
                        if (value instanceof Tag<?> tag)
                            spawnsOnTopOf.addAll(((Tag<Material>) tag).getValues());
                    } catch (ReflectiveOperationException e) {
                        throw new IllegalArgumentException("Unknown block tag: " + entry, e);
                    }
                }
            }

            rules.add(new SpawnRule(environments, biomes, spawnsOnTopOf, entityTypes));
        }

        return rules;
    }

    public boolean trySpawnEntity(NotEnoughAnimals plugin, Chunk chunk) {
        World world = chunk.getWorld();
        if (!environments.contains(world.getEnvironment())) return false;

        Random random = plugin.getRandom();
        int x = (chunk.getX() << 4) + random.nextInt(16);
        int z = (chunk.getZ() << 4) + random.nextInt(16);

        Block block = Utility.resolveGround(world.getHighestBlockAt(x, z));
        if (block == null) return false;
        if (!biomes.contains(block.getBiome())) return false;
        if (!spawnsOnTopOf.contains(block.getType())) return false;
        Block upperBlock = block.getRelative(0, 1, 0);
        if (upperBlock.getLightFromSky() < 9) return false;
        if (!Tag.REPLACEABLE.isTagged(upperBlock.getType())) return false;

        EntityType entityType = Utility.randomChoice(entityTypes, random);
        Location spawnLocation = block.getLocation().add(0.5, 1, 0.5);

        Entity entity = world.spawnEntity(spawnLocation, entityType, CreatureSpawnEvent.SpawnReason.CUSTOM);
        plugin.setMarked(entity, true);
        entity.setPersistent(false);
        if (plugin.isEntitiesSpawnedGlow()) entity.setGlowing(true);

        return true;
    }
}
