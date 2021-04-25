package dev.floffah.discountsmp.discountsmp.commands;

import dev.floffah.discountsmp.discountsmp.DiscountSMP;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;

public class Tellall implements CommandExecutor, TabCompleter {
    DiscountSMP main;

    public Tellall(DiscountSMP main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("disc.tellall") || sender.isOp()) {
            main.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', String.join(" ", args)));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
