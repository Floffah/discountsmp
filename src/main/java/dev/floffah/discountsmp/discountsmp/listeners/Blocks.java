package dev.floffah.discountsmp.discountsmp.listeners;

import dev.floffah.discountsmp.discountsmp.DiscountSMP;
import dev.floffah.discountsmp.discountsmp.store.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Lectern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;

public class Blocks implements Listener {
    DiscountSMP main;

    public Blocks(DiscountSMP main) {
        this.main = main;
    }

    @EventHandler
    @Deprecated
    public void onPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType().equals(Material.PISTON)) {
            Player p = e.getPlayer();
            PlayerData pd = main.getPlayer(p.getUniqueId());
            if (!pd.isMechanic) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not in mechanic mode"));
            }
        } else if (e.getBlock().getType().equals(Material.SKELETON_SKULL)) {
            Block block = e.getBlock().getRelative(BlockFace.DOWN);
            if (block.getType().equals(Material.CHEST)) {
                Chest chest = (Chest) block.getState();
                if (chest.getInventory().getItem(0) != null && chest.getInventory().getItem(0).getType().equals(Material.NETHERITE_INGOT)) {
                    World world = block.getWorld();
                    world.getBlockAt(e.getBlock().getLocation()).setType(Material.AIR);
                    world.getBlockAt(block.getLocation()).setType(Material.LECTERN);
                    Lectern lectern = (Lectern) world.getBlockAt(block.getLocation()).getState();
                    ItemStack bok = new ItemStack(Material.WRITTEN_BOOK);
                    BookMeta bm = (BookMeta) bok.getItemMeta();
                    bm.setAuthor("Ancestors");
                    bm.setTitle("The token");
                    ArrayList<String> pages = new ArrayList<>();
                    pages.add("You did it.\nGive this to a god by the name of Floffah.\n\n- Your Ancestors");
                    bm.setPages(pages);
                    bok.setItemMeta(bm);
                    lectern.getInventory().setItem(0, bok);
                }
            }
        }
    }
}
