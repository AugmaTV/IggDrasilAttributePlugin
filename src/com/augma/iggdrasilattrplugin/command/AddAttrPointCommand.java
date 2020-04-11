package com.augma.iggdrasilattrplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.augma.iggdrasilattrplugin.Main;

public class AddAttrPointCommand implements CommandExecutor {

	private Main main;
	
	public AddAttrPointCommand(Main main) {
		this.main = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0) {
				player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.addattrpoint.forgetPlayer").replace("&", "§"));
			} else {
				if(args.length == 1) {
					player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.addattrpoint.forgetNumber").replace("&", "§"));
				} else {
					if(Bukkit.getPlayer(args[0]) != null) {
						Player target = Bukkit.getPlayer(args[0]);
						int pointToAdd;
						try {
							pointToAdd = Integer.parseInt(args[1]);
						}
						catch (NumberFormatException e)
						{
							player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.addattrpoint.errorNumber").replace("&", "§"));
							return false;
						}
						main.getConfig().set(target.getUniqueId().toString() + ".attrPoint", main.getConfig().getInt(target.getUniqueId().toString() + ".attrPoint") + pointToAdd);
						player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.addattrpoint.success").replace("&", "§"));
					} else {
						player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.addattrpoint.didntFindPlayer").replace("&", "§"));
					}
				}
			}
		}
		return false;
	}
}
