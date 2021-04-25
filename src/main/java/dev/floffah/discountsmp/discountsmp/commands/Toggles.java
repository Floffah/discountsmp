package dev.floffah.discountsmp.discountsmp.commands;

import dev.floffah.discountsmp.discountsmp.DiscountSMP;
import dev.floffah.util.chat.Colours;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Toggles implements CommandExecutor, TabExecutor {
    DiscountSMP main;

    public Toggles(DiscountSMP main) {
        this.main = main;
    }

    public boolean hasLPPermission(User u, String perm) {
        Set<PermissionNode> perms = u.getNodes().stream()
                .filter(NodeType.PERMISSION::matches)
                .map(NodeType.PERMISSION::cast)
                //.map(PermissionNode::getPermission)
                .collect(Collectors.toSet());

        for (PermissionNode lpp : perms) {
            if (lpp.getPermission().equalsIgnoreCase(perm) && !lpp.getValue()) return false;
            else if (lpp.getPermission().equalsIgnoreCase(perm)) return true;
        }
        return false;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Colours.def("&cOnly a player can run this command"));
            return true;
        }
        Player p = (Player) sender;
        User u = main.lp.getUserManager().getUser(p.getUniqueId());
        if (u == null) {
            sender.sendMessage(Colours.def("&cThe permission system is not aware of your existence."));
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("keepinv") || args[0].equalsIgnoreCase("keepinventory") || args[0].equalsIgnoreCase("kinv")) {
                if (!hasLPPermission(u, "essentials.keepinv")) {
                    u.data().add(Node.builder("essentials.keepinv").build());
                    u.data().add(Node.builder("essentials.keepxp").build());
                    main.lp.getUserManager().saveUser(u);
                    sender.sendMessage(Colours.def("&aEnabled keep inventory for " + sender.getName()));
                } else if (hasLPPermission(u, "essentials.keepinv")) {
                    Set<PermissionNode> perms = u.getNodes().stream()
                            .filter(NodeType.PERMISSION::matches)
                            .map(NodeType.PERMISSION::cast)
                            .collect(Collectors.toSet());

                    for (PermissionNode perm : perms) {
                        System.out.println(perm.getPermission());
                        if (perm.getPermission().equalsIgnoreCase("essentials.keepinv") || perm.getPermission().equalsIgnoreCase("essentials.keepxp")) {
                            u.data().remove(perm);
                            u.data().add(perm.toBuilder().value(false).build());
                        }
                    }

                    sender.sendMessage(Colours.def("&aDisabled keep inventory for " + sender.getName()));
                }
                return true;
            }
        }
        sender.sendMessage(Colours.def("&cIncorrect usage.\n&cCommands:\n&c/toggle keepinv"));
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("keepinv", "keepinventory", "kinv");
        }
        return new ArrayList<>();
    }
}
