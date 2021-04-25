package dev.floffah.discountsmp.discountsmp.listeners;

import dev.floffah.discountsmp.discountsmp.DiscountSMP;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class Players implements Listener {
    DiscountSMP main;

    public Players(DiscountSMP main) {
        this.main = main;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (main.getPd().containsKey(e.getPlayer().getUniqueId())) {
            main.getPd().get(e.getPlayer().getUniqueId()).destroy();
            main.getPd().remove(e.getPlayer().getUniqueId());
        }
    }

    public boolean multiEqualsIgnoreCase(String str, String... comparer) {
        if (str == null) return false;
        for (String s : comparer) {
            if (s != null && s.equalsIgnoreCase(str)) return true;
        }
        return false;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() == EntityType.PLAYER) {
            Player player = (Player) e.getDamager();
            ItemStack mainhand = player.getInventory().getItemInMainHand();
            ItemStack offhand = player.getInventory().getItemInOffHand();
            if (
                    !(
                            ((!mainhand.getType().equals(Material.AIR) && mainhand.hasDisplayName() && mainhand.getDisplayName().toLowerCase().contains("mule's wrath"))
                                    || (!offhand.getType().equals(Material.AIR) && offhand.hasDisplayName() && offhand.getDisplayName().toLowerCase().contains("mule's wrath"))
                            ) && player.getName().equalsIgnoreCase("rubbishh26"))
            ) {
                if (mainhand.getType().equals(Material.STICK) && mainhand.containsEnchantment(Enchantment.KNOCKBACK))
                    reset(e, mainhand);
                else if (offhand.getType().equals(Material.STICK) && offhand.containsEnchantment(Enchantment.KNOCKBACK))
                    reset(e, offhand);
                else if (mainhand.containsEnchantment(Enchantment.DAMAGE_ALL) && mainhand.getEnchantLevel(Enchantment.DAMAGE_ALL) > 5)
                    reset(e, mainhand);
                else if (offhand.containsEnchantment(Enchantment.DAMAGE_ALL) && offhand.getEnchantLevel(Enchantment.DAMAGE_ALL) > 5)
                    reset(e, offhand);
            } else if (e.getEntity() instanceof Player) {
                for (ItemStack i : ((Player) e.getEntity()).getInventory().getArmorContents()) {
                    i.setDamage(1);
                }
            }
        }
    }

    public void reset(EntityDamageByEntityEvent e, ItemStack stack) {
        e.setCancelled(true);
        stack.setAmount(0);
    }
}
