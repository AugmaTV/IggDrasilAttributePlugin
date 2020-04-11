package com.augma.iggdrasilattrplugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import com.augma.iggdrasilattrplugin.command.AddAttrPointCommand;
import com.augma.iggdrasilattrplugin.command.AttrPointCommand;
import com.augma.iggdrasilattrplugin.command.ResetSkillCommand;
import com.augma.iggdrasilattrplugin.command.SkillCommand;
import com.augma.iggdrasilattrplugin.command.SkillinfoCommand;
import com.augma.iggdrasilattrplugin.listener.InventoryListener;
import com.augma.iggdrasilattrplugin.listener.PlayerListener;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		System.out.println("IggDrasil Attribute Plugin is loaded.");
		saveDefaultConfig();
		getCommand("skill").setExecutor(new SkillCommand(this));
		getCommand("skillinfo").setExecutor(new SkillinfoCommand(this));
		getCommand("addattrpoint").setExecutor(new AddAttrPointCommand(this));
		getCommand("resetskill").setExecutor(new ResetSkillCommand(this));
		getCommand("attrpoint").setExecutor(new AttrPointCommand(this));
		getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
	}
	
	@Override
	public void onDisable() {
		System.out.println("Iggdrasil Attribute Plugin have been stopped.");
		try {
			getConfig().save(getDataFolder() + File.separator + "config.yml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
