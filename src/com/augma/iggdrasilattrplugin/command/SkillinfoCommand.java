package com.augma.iggdrasilattrplugin.command;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.augma.iggdrasilattrplugin.Main;

public class SkillinfoCommand implements CommandExecutor {

	private Main main;
	
	public SkillinfoCommand(Main main) {
		this.main = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0) {
				player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.skillinfo.forgetPlayer").replace("&", "§"));
			} else {
				if(Bukkit.getPlayer(args[0]) != null) {
					Player target  = Bukkit.getPlayer(args[0]);
					Inventory inv = Bukkit.createInventory(null, 18, main.getConfig().getString("messages.skillinfo.interfaceName").replace("&", "§"));
					ItemStack book = new ItemStack(Material.BOOK);
					ItemMeta bookM = book.getItemMeta();
					
					ItemStack skull = new ItemStack(Material.SKULL_ITEM);
					skull.setDurability((short) 3);
					SkullMeta skullM = (SkullMeta) skull.getItemMeta();
					skullM.setDisplayName(target.getName());
					skullM.setDisplayName(main.getConfig().getString("messages.head.titleHead").replace("&", "§"));
					skullM.setLore(Arrays.asList(main.getConfig().getString("messages.head.beforePlayerName").replace("&", "§") + player.getName(), main.getConfig().getString("messages.skillinfo.beforeNumber").replace("&", "§") + SkillCommand.getAttrPointColored(main.getConfig().getInt(target.getUniqueId() + ".attrPoint")) + main.getConfig().getString("messages.skillinfo.afterNumber").replace("&", "§")));
					skullM.setOwner(target.getName());
					skull.setItemMeta(skullM);
					for(int i = 0; i < inv.getSize(); i++) {
						if(i == 0) inv.setItem(i, skull);
						if(i == 11) {
							bookM.setDisplayName(main.getConfig().getString("messages.force.bookAttributePoint").replace("&","§"));
							bookM.setLore(Arrays.asList(main.getConfig().getString("messages.actualPoint").replace("&","§") + " " + main.getConfig().getInt(target.getUniqueId().toString() + ".force")));
							book.setItemMeta(bookM);
							inv.setItem(i, book);
						}
						if(i == 13) {
							bookM.setDisplayName(main.getConfig().getString("messages.vie.bookAttributePoint").replace("&","§"));
							bookM.setLore(Arrays.asList(main.getConfig().getString("messages.actualPoint").replace("&","§") + " " + main.getConfig().getInt(target.getUniqueId().toString() + ".vie")));
							book.setItemMeta(bookM);
							inv.setItem(i, book);
						}
						if(i == 15) {
							bookM.setDisplayName(main.getConfig().getString("messages.critique.bookAttributePoint").replace("&","§"));
							bookM.setLore(Arrays.asList(main.getConfig().getString("messages.actualPoint").replace("&","§") + " " + main.getConfig().getInt(target.getUniqueId().toString() + ".critique")));
							book.setItemMeta(bookM);
							inv.setItem(i, book);
						}
					}
					player.openInventory(inv);
				} else {
					player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.skillinfo.didntFindPlayer").replace("&", "§"));
				}
			}
		}
		return false;
	}

}
