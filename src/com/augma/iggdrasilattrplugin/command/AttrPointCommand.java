package com.augma.iggdrasilattrplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.augma.iggdrasilattrplugin.Main;
import com.augma.iggdrasilattrplugin.listener.PlayerListener;

public class AttrPointCommand implements CommandExecutor {

	private Main main;
	
	public AttrPointCommand(Main main) {
		this.main = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0) {
				player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.attrPoint.forgetPlayer").replace("&", "§"));
			} else {
				if(args.length == 1) {
					player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.attrPoint.forgetType").replace("&", "§"));
				} else {
					if(Bukkit.getPlayer(args[0]) != null) {
						Player target = Bukkit.getPlayer(args[0]);
						if(main.getConfig().getInt(target.getUniqueId().toString() + ".attrPoint") != 0) {
							if(args[1].equalsIgnoreCase("force")) {
								main.getConfig().set(target.getUniqueId().toString() + ".attrPoint", main.getConfig().getInt(target.getUniqueId().toString() + ".attrPoint") - 1);
								main.getConfig().set(target.getUniqueId().toString() + ".force", main.getConfig().getInt(target.getUniqueId().toString() + ".force") + 1);
								target.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.force.incrementAttributeValue").replace("&", "§"));
								player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.attrPoint.success").replace("&", "§"));
							} else if(args[1].equalsIgnoreCase("vie")) {
								main.getConfig().set(target.getUniqueId().toString() + ".attrPoint", main.getConfig().getInt(target.getUniqueId().toString() + ".attrPoint") - 1);
								main.getConfig().set(target.getUniqueId().toString() + ".vie", main.getConfig().getInt(target.getUniqueId().toString() + ".vie") + 1);
								PlayerListener.upgradePlayerHealth(target, false);
								target.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.vie.incrementAttributeValue").replace("&", "§"));
								player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.attrPoint.success").replace("&", "§"));
							} else if(args[1].equalsIgnoreCase("critique")) {
								main.getConfig().set(target.getUniqueId().toString() + ".attrPoint", main.getConfig().getInt(target.getUniqueId().toString() + ".attrPoint") - 1);
								main.getConfig().set(target.getUniqueId().toString() + ".critique", main.getConfig().getInt(target.getUniqueId().toString() + ".critique") + 1);
								target.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.vie.incrementAttributeValue").replace("&", "§"));
								player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.attrPoint.success").replace("&", "§"));
							} else {
								player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.attrPoint.errorType").replace("&", "§"));
							}
						} else {
							player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.attrPoint.notEnoughAttrPoint").replace("&", "§"));
						}
					} else {
						player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.attrPoint.didntFindPlayer").replace("&", "§"));
					}
				}
			}
		}
		return false;
	}

}
