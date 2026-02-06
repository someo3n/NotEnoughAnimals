<img src="/img/icon.png" align="left" width="100" style="margin-right: 10px;" alt="NotEnoughAnimals logo">

## NotEnoughAnimals

<div style="clear: both;"></div>

> Animals never stop spawning, spawn anytime you load fresh or existing chunks and now despawn when you unload entities

### Introduction

Before beta 1.8, animals would spawn constantly like hostile mobs
but would despawn like hostile mobs. But Minecraft, after its beta 1.8 Adventure Update, changed how animals
spawning mechanics dramatically. The changes were that animals did not
ever despawn, but at the cost of **each chunk having a permanent animal mob cap that tracks all animals ever spawned
in that chunk and so, once that cap was reached no more animals spawned in that specific chunk and the chunk was basically
"extinct" of animals.**  This change was made so that farmers did not have their mobs
despawn after a long trip away from their farm. But this change made the hunting for
food kind of people not get as many mobs as farmers. **Also, this sucked for multiplayer
servers since everyone quickly reached that mob cap when they first joined the world
and were without gear and killed everything in their vicinity to quickly get some food.**

### The features of this plugin
This plugin, as an improved recreation of an old plugin called [BetterPassives](https://dev.bukkit.org/projects/betterpassives), tries to
fix this issue and find a balance both for hunters and farmers. The vanilla spawning mechanics
stay intact, but we also add our own spawning mechanics on top of vanilla. When you load a
chunk, fresh or already generated, there is a chance -determined by the configuration file-
(defaults to 65%) that that chunk which you loaded is a spawning chunk, where animals spawn.
Then an amount of spawning tries is determined randomly with the minimum and maximum values
for the RNG being also determined by the configuration file.

But when the server unloads entities, if those entities were spawned by this plugin and don't fit
the criteria for not despawning they will be despawned.

How will you keep your entities? Well you can make them fit the vanilla criteria that determine
if they should despawn or not, like name tagging them for example, but you can also feed them 
or breed them to make them domestic mobs and essentially they and their all their generations
will not despawn.

**This plugin does not affect how hostile entities, aquatic entities, etc. spawn! It is recommended
to use it only for controlling how land animals spawn!**

### In action

![Example](/img/example2.png "Example")
_Example (don't mind the sheep being attacked by the wolf)_

![Example at spawn with /neaglow](/img/example.png "Example at spawn with /neaglow")
_Example at a server's spawn with all the NotEnoughAnimals spawned entities glowing_

### Quick start

First, make sure you fit these **requirements**:
* PaperMC 1.21+
* Java 21+

Then follow these steps to install the plugin:

1. Download the latest release from the Releases page or from whatever website your reading this on
2. Place `NotEnoughAnimals.jar` into your server's `plugins/` folder
3. Restart the server or just load this plugin if you have a plugin manager
4. Adjust the configuration in `plugins/NotEnoughAnimals/config.yml` if needed


### For server admins

#### Performance

Spawning checks only occur when chunks are loaded.
Despawning checks only occur when entities are unloaded.
The plugin does not run constant ticking logic.

#### Management

You can see which mobs were spawned by this plugin by seeing if they have
the `_m` scoreboard tag. You can also see which mobs are domestic by seeing if they have the
`_d` scoreboard tag.

#### Commands
* `/neareload` - Reload the configuration file of this plugin
* `/neaglow` - Debugging feature to make entities spawned by this plugin glow

#### Configuration

Here is the default configuration file, with some comments to help you understand what is
going on.

```yaml
# NotEnoughAnimals configuration file

# The chance for a chunk that you load to be a chunk where entities will spawn
# 1.0 = 100%, 0.0 = 0%
spawning-chunk-chance: 0.65

# How much times to try to spawn entities in a spawning chunk
spawn-tries:
  min: 1
  max: 5

# Spawning requirements for each entity
spawn-rules:
    # Allowed environments. Each one can be NORMAL, NETHER or THE_END
  - environments: [NORMAL]
    # Allowed biomes
    # Refer to https://jd.papermc.io/paper/1.21.11/org/bukkit/block/Biome.html
    # for a biome name list
    biomes: [PLAINS, SUNFLOWER_PLAINS]
    # Allowed blocks to stand on, "#" means insert the contents of a block tag
    # Refer to https://jd.papermc.io/paper/1.21.11/org/bukkit/Material.html
    # for a material name list
    # And refer to https://jd.papermc.io/paper/1.21.11/org/bukkit/Tag.html
    # for a tags name list
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    # Entity types that can spawn under these conditions
    # Refer to https://jd.papermc.io/paper/1.21.11/org/bukkit/entity/EntityType.html
    # for an entity type names list
    types: [COW, SHEEP, PIG, CHICKEN, HORSE, DONKEY]
  - environments: [NORMAL]
    biomes: [FOREST, FLOWER_FOREST, BIRCH_FOREST, OLD_GROWTH_BIRCH_FOREST]
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    types: [SHEEP, PIG, CHICKEN, COW, WOLF]
  - environments: [NORMAL]
    biomes: [DARK_FOREST]
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    types: [SHEEP, PIG, CHICKEN, COW]
  - environments: [NORMAL]
    biomes: [TAIGA, OLD_GROWTH_PINE_TAIGA, OLD_GROWTH_SPRUCE_TAIGA]
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    types: [WOLF, FOX, RABBIT, SHEEP, PIG, CHICKEN, COW]
  - environments: [NORMAL]
    biomes: [SNOWY_TAIGA]
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    types: [WOLF, FOX, RABBIT, SHEEP, PIG, CHICKEN, COW]
  - environments: [NORMAL]
    biomes: [SNOWY_PLAINS, SNOWY_SLOPES, ICE_SPIKES]
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    types: [POLAR_BEAR, RABBIT, FOX]
  - environments: [NORMAL]
    biomes: [GROVE, FROZEN_PEAKS, JAGGED_PEAKS]
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    types: [RABBIT, FOX, GOAT]
  - environments: [NORMAL]
    biomes: [STONY_PEAKS, WINDSWEPT_HILLS, WINDSWEPT_GRAVELLY_HILLS]
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    types: [GOAT, SHEEP, LLAMA]
  - environments: [NORMAL]
    biomes: [WINDSWEPT_FOREST]
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    types: [SHEEP, PIG, CHICKEN, COW, LLAMA]
  - environments: [NORMAL]
    biomes: [MEADOW]
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    types: [DONKEY, SHEEP, RABBIT]
  - environments: [NORMAL]
    biomes: [CHERRY_GROVE]
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    types: [SHEEP, PIG, CHICKEN, BEE]
  - environments: [NORMAL]
    biomes: [JUNGLE, BAMBOO_JUNGLE, SPARSE_JUNGLE]
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    types: [CHICKEN, OCELOT, PARROT, PANDA]
  - environments: [NORMAL]
    biomes: [SAVANNA, SAVANNA_PLATEAU, WINDSWEPT_SAVANNA]
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    types: [HORSE, DONKEY, SHEEP, COW, PIG, CHICKEN]
  - environments: [NORMAL]
    biomes: [DESERT]
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    types: [RABBIT]
  - environments: [NORMAL]
    biomes: [SWAMP, MANGROVE_SWAMP]
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    types: [FROG]
  - environments: [NORMAL]
    biomes: [MUSHROOM_FIELDS]
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    types: [MOOSHROOM]
  - environments: [NORMAL]
    biomes: [BEACH, SNOWY_BEACH, STONY_SHORE]
    on-top-of: ["#ANIMALS_SPAWNABLE_ON"]
    types: [TURTLE]
```

### License

This project is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

See the [LICENSE](LICENSE) file for the full text.
