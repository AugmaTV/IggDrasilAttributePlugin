package com.augma.iggdrasilattrplugin.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

public class SkillCommand implements CommandExecutor {
	
	private static Main main;
	public static String inventoryName;
	
	
	
	@SuppressWarnings("static-access")
	public SkillCommand(Main main) {
		this.main = main;
		this.inventoryName = main.getConfig().getString("messages.interfaceName").replace("&", "§");
	}
	
	public static String getAttrPointColored(int attrPoint) {
		String result = "";
		if(attrPoint > 0) result = "§2" + attrPoint;
		else result = "§4" + attrPoint;
		return result;
	}
	public ItemStack getItem(Material item, String customname) {
		ItemStack it = new ItemStack(item);
		ItemMeta itM = it.getItemMeta();
		itM.setDisplayName(customname);
		it.setItemMeta(itM);
		return it;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			ItemStack skull = new ItemStack(Material.SKULL_ITEM);
			skull.setDurability((short) 3);
			SkullMeta skullM = (SkullMeta) skull.getItemMeta();
			skullM.setDisplayName(player.getName());
			skullM.setDisplayName(main.getConfig().getString("messages.head.titleHead").replace("&", "§"));
			skullM.setLore(Arrays.asList(main.getConfig().getString("messages.head.beforePlayerName").replace("&", "§") + player.getName(), main.getConfig().getString("messages.head.beforeNumber").replace("&", "§") + getAttrPointColored(main.getConfig().getInt(player.getUniqueId() + ".attrPoint")) + main.getConfig().getString("messages.head.afterNumber").replace("&", "§")));
			skullM.setOwner(player.getDisplayName());
			skull.setItemMeta(skullM);
			
			ItemStack flint_and_steel = new ItemStack(Material.FLINT_AND_STEEL);
			ItemMeta flint_and_steelM = flint_and_steel.getItemMeta();
			flint_and_steelM.setDisplayName(main.getConfig().getString("messages.flint.title").replace("&", "§"));
			List<String> loreflint = new ArrayList<String>();
			for(String str : main.getConfig().getStringList("messages.flint.lore")) {
				loreflint.add(str.replace("&", "§"));
			}
			flint_and_steelM.setLore(loreflint);
			flint_and_steel.setItemMeta(flint_and_steelM);
			
			ItemStack iron_sword = new ItemStack(Material.IRON_SWORD);
			ItemMeta iron_swordM = iron_sword.getItemMeta();
			iron_swordM.setDisplayName(main.getConfig().getString("messages.force.attributeOf").replace("&","§"));
			iron_swordM.setLore(Arrays.asList(main.getConfig().getString("messages.force.itemAtrributeTo").replace("&","§")));
			iron_sword.setItemMeta(iron_swordM);
			
			ItemStack iron_chestplate = new ItemStack(Material.IRON_CHESTPLATE);
			ItemMeta iron_chestplateM = iron_chestplate.getItemMeta();
			iron_chestplateM.setDisplayName(main.getConfig().getString("messages.vie.attributeOf").replace("&","§"));
			iron_chestplateM.setLore(Arrays.asList(main.getConfig().getString("messages.vie.itemAtrributeTo").replace("&","§")));
			iron_chestplate.setItemMeta(iron_chestplateM);
			
			ItemStack potion_of_healing = new ItemStack(Material.POTION);
			ItemMeta potion_of_healingM = potion_of_healing.getItemMeta();
			potion_of_healingM.setDisplayName(main.getConfig().getString("messages.critique.attributeOf").replace("&","§"));
			potion_of_healingM.setLore(Arrays.asList(main.getConfig().getString("messages.critique.itemAtrributeTo").replace("&","§")));
			potion_of_healing.setItemMeta(potion_of_healingM);
			potion_of_healing.setDurability((short) 8261);
			
			ItemStack book = new ItemStack(Material.BOOK);
			ItemMeta bookM = book.getItemMeta();
			Inventory skill = Bukkit.createInventory(null, 27, inventoryName + player.getName());
			
			for(int i = 0; i < skill.getSize(); i++) {
				if(i == 0) skill.setItem(i, skull);
				if(i == 8) skill.setItem(i, flint_and_steel);
				if(i == 11) skill.setItem(i, iron_sword);
				if(i == 13) skill.setItem(i, iron_chestplate);
				if(i == 15) skill.setItem(i, potion_of_healing);
				if(i == 20) {
					bookM.setDisplayName(main.getConfig().getString("messages.force.bookAttributePoint").replace("&","§"));
					bookM.setLore(Arrays.asList(main.getConfig().getString("messages.actualPoint").replace("&","§") + " " + main.getConfig().getInt(player.getUniqueId().toString() + ".force")));
					book.setItemMeta(bookM);
					skill.setItem(i, book);
				}
				if(i == 22) {
					bookM.setDisplayName(main.getConfig().getString("messages.vie.bookAttributePoint").replace("&","§"));
					bookM.setLore(Arrays.asList(main.getConfig().getString("messages.actualPoint").replace("&","§") + " " + main.getConfig().getInt(player.getUniqueId().toString() + ".vie")));
					book.setItemMeta(bookM);
					skill.setItem(i, book);
				}
				if(i == 24) {
					bookM.setDisplayName(main.getConfig().getString("messages.critique.bookAttributePoint").replace("&","§"));
					bookM.setLore(Arrays.asList(main.getConfig().getString("messages.actualPoint").replace("&","§") + " " + main.getConfig().getInt(player.getUniqueId().toString() + ".critique")));
					book.setItemMeta(bookM);
					skill.setItem(i, book);
				}
			}
			
			player.openInventory(skill);
		}
		return false;
	}

}
