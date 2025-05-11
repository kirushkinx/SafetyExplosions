package ru.kirushkinx.SafetyExplosions;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;

public class ReloadCommand implements CommandExecutor, TabCompleter {

    private final SafetyExplosions plugin;

    public ReloadCommand(SafetyExplosions plugin) {
        this.plugin = plugin;
        plugin.getCommand("safetyexplosions").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadPluginConfig();
            sender.sendMessage(ChatColor.GREEN + "SafetyExplosions config reloaded!");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Usage: /safetyexplosions reload");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("reload");
        }
        return Collections.emptyList();
    }
}