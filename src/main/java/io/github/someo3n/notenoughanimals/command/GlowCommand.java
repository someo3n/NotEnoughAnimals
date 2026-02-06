package io.github.someo3n.notenoughanimals.command;

import io.github.someo3n.notenoughanimals.NotEnoughAnimals;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

public class GlowCommand implements CommandExecutor {
    private final NotEnoughAnimals plugin;

    public GlowCommand(NotEnoughAnimals plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean newValue = !plugin.isEntitiesSpawnedGlow();
        plugin.setEntitiesSpawnedGlow(newValue);
        String suffix = newValue ? "glow" : "not glow";
        sender.sendMessage("Entities spawned by Not Enough Animals will now " + suffix);
        return true;
    }
}
