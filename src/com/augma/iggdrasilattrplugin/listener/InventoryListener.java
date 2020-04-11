package com.augma.iggdrasilattrplugin.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.augma.iggdrasilattrplugin.Main;
import com.augma.iggdrasilattrplugin.command.SkillCommand;

public class InventoryListener implements Listener {
	
	private Main main;

	public InventoryListener(Main main) {
		this.main = main;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInventoryInteract(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
		Player player = (Player) event.getWhoClicked();
		if(inv.getName().equalsIgnoreCase(SkillCommand.inventoryName + player.getName())) {
			event.setCancelled(true);
			ItemStack current = event.getCurrentItem();
			if(current.getType().equals(Material.AIR) || current.getType().equals(Material.BOOK) || current.getType().equals(Material.SKULL_ITEM)) return;
			else if(!(main.getConfig().getInt(player.getUniqueId().toString() + ".attrPoint") <= 0) || current.getType().equals(Material.FLINT_AND_STEEL)) {
				if(current.getType().equals(Material.IRON_SWORD)) {
					main.getConfig().set(player.getUniqueId().toString() + ".attrPoint", main.getConfig().getInt(player.getUniqueId().toString() + ".attrPoint") - 1);
					main.getConfig().set(player.getUniqueId().toString() + ".force", main.getConfig().getInt(player.getUniqueId().toString() + ".force") + 1);
					player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.force.incrementAttributeValue").replace("&", "§"));
					player.closeInventory();
					player.performCommand("skill");
				} else if(current.getType().equals(Material.IRON_CHESTPLATE)) {
					main.getConfig().set(player.getUniqueId().toString() + ".attrPoint", main.getConfig().getInt(player.getUniqueId().toString() + ".attrPoint") - 1);
					main.getConfig().set(player.getUniqueId().toString() + ".vie", main.getConfig().getInt(player.getUniqueId().toString() + ".vie") + 1);
					PlayerListener.upgradePlayerHealth(player, false);
					player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.vie.incrementAttributeValue").replace("&", "§"));
					player.closeInventory();
					player.performCommand("skill");
				} else if(current.getType().equals(Material.POTION)) {
					main.getConfig().set(player.getUniqueId().toString() + ".attrPoint", main.getConfig().getInt(player.getUniqueId().toString() + ".attrPoint") - 1);
					main.getConfig().set(player.getUniqueId().toString() + ".critique", main.getConfig().getInt(player.getUniqueId().toString() + ".critique") + 1);
					player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.vie.incrementAttributeValue").replace("&", "§"));
					player.closeInventory();
					player.performCommand("skill");
				} else if(current.getType().equals(Material.FLINT_AND_STEEL)) {
					if(!(main.getConfig().getInt(player.getUniqueId().toString() + ".force") == 0 && main.getConfig().getInt(player.getUniqueId().toString() + ".vie") == 0 && main.getConfig().getInt(player.getUniqueId().toString() + ".critique") == 0)) {
						int lastHealth = main.getConfig().getInt(player.getUniqueId().toString() + ".vie");
						System.out.println(lastHealth);
						int totalAttrPoint = main.getConfig().getInt(player.getUniqueId().toString() + ".attrPoint") + main.getConfig().getInt(player.getUniqueId().toString() + ".force") + main.getConfig().getInt(player.getUniqueId().toString() + ".vie") + main.getConfig().getInt(player.getUniqueId().toString() + ".critique");
						main.getConfig().set(player.getUniqueId().toString() + ".attrPoint", totalAttrPoint);
						main.getConfig().set(player.getUniqueId().toString() + ".force", 0);
						main.getConfig().set(player.getUniqueId().toString() + ".vie", 0);
						main.getConfig().set(player.getUniqueId().toString() + ".critique", 0);
						player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.flint.message").replace("&", "§"));
						player.closeInventory();
						player.performCommand("skill");
						PlayerListener.resetPlayerHealth(player, lastHealth);
					} else {
						player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.flint.alreadyReset").replace("&", "§"));
						player.closeInventory();
					}
				}
			} else {
				player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.notEnoughAttrPoint").replace("&", "§"));
				player.closeInventory();
			}
		} else if(inv.getName().equalsIgnoreCase(main.getConfig().getString("messages.skillinfo.interfaceName").replace("&", "§"))) {
			event.setCancelled(true);
		}
	}
}
