package io.github.someo3n.notenoughanimals.command;

import io.github.someo3n.notenoughanimals.NotEnoughAnimals;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private final NotEnoughAnimals plugin;

    public ReloadCommand(NotEnoughAnimals plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.reloadConfig();
        sender.sendMessage("The Not Enough Animals configuration was reloaded");
        return true;
    }
}
