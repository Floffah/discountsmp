package dev.floffah.discountsmp.discountsmp.commands;

import dev.floffah.discountsmp.discountsmp.DiscountSMP;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Disc implements CommandExecutor, TabCompleter {
    DiscountSMP main;

    public Disc(DiscountSMP main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("disc.cmd.disc")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo permission LOL"));
            return true;
        }
        if (args.length == 1) {
            if (args[0].equals("summon")) {

            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("summon", "edit");
        } else if (args.length == 2) {
            if (args[0].equals("summon")) {
                return Arrays.asList("particle-buildup", "uground-buildup");
            }
        }
        return new ArrayList<>();
    }
}
