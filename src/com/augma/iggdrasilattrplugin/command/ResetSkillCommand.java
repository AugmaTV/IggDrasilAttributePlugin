package com.augma.iggdrasilattrplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.augma.iggdrasilattrplugin.Main;
import com.augma.iggdrasilattrplugin.listener.PlayerListener;

public class ResetSkillCommand implements CommandExecutor {

	private Main main;
	
	public ResetSkillCommand(Main main) {
		this.main = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0) {
				player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.resetskill.forgetPlayer").replace("&", "§"));
			} else {
				if(Bukkit.getPlayer(args[0]) != null) {
					Player target = Bukkit.getPlayer(args[0]);
					if(!(main.getConfig().getInt(player.getUniqueId().toString() + ".force") == 0 && main.getConfig().getInt(player.getUniqueId().toString() + ".vie") == 0 && main.getConfig().getInt(player.getUniqueId().toString() + ".critique") == 0)) {
						int lastHealth = main.getConfig().getInt(player.getUniqueId().toString() + ".vie");
						System.out.println(lastHealth);
						int totalAttrPoint = main.getConfig().getInt(player.getUniqueId().toString() + ".attrPoint") + main.getConfig().getInt(player.getUniqueId().toString() + ".force") + main.getConfig().getInt(player.getUniqueId().toString() + ".vie") + main.getConfig().getInt(player.getUniqueId().toString() + ".critique");
						main.getConfig().set(player.getUniqueId().toString() + ".attrPoint", totalAttrPoint);
						main.getConfig().set(player.getUniqueId().toString() + ".force", 0);
						main.getConfig().set(player.getUniqueId().toString() + ".vie", 0);
						main.getConfig().set(player.getUniqueId().toString() + ".critique", 0);
						player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.resetskill.message").replace("&", "§"));
						target.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.resetskill.messageByAdmin").replace("&", "§"));
						PlayerListener.resetPlayerHealth(target, lastHealth);
					} else {
						player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.resetskill.alreadyReset").replace("&", "§"));
					}
				} else {
					player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.resetskill.didntFindPlayer").replace("&", "§"));
				}
			}
		}
		return false;
	}
}
