package com.augma.iggdrasilattrplugin.listener;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.augma.iggdrasilattrplugin.Main;

public class PlayerListener implements Listener {

	private static Main main;
	
	@SuppressWarnings("static-access")
	public PlayerListener(Main main) {
		this.main = main;
	}
	
	public static void upgradePlayerHealth(Player player, boolean bool) {
		int maxHealthAttr = main.getConfig().getInt(player.getUniqueId().toString() + ".vie");
		Double maxHealth = (player.getMaxHealth() - (maxHealthAttr - 1) * main.getConfig().getDouble("modifiers.health"))  + maxHealthAttr * main.getConfig().getDouble("modifiers.health");
		player.setMaxHealth(maxHealth);
		if(bool) {
			player.setHealth(maxHealth);
		}
	}
	
	public static void setMaxHealthOnJoin(Player player) {
		Double maxHealth = player.getMaxHealth() + (main.getConfig().getInt(player.getUniqueId().toString() + ".vie") * main.getConfig().getDouble("modifiers.health"));
		player.setMaxHealth(maxHealth);
		player.setHealth(maxHealth);
	}
	
	public static void resetPlayerHealth(Player player, int lastHealthAttr) {
		player.setMaxHealth(player.getMaxHealth() - (lastHealthAttr * main.getConfig().getDouble("modifiers.health")));
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent event) {
		if(!main.getConfig().isSet(event.getPlayer().getUniqueId().toString())) {
			main.getConfig().createSection(event.getPlayer().getUniqueId().toString());
			main.getConfig().set(event.getPlayer().getUniqueId().toString() + ".attrPoint", 0);
			main.getConfig().set(event.getPlayer().getUniqueId().toString() + ".force", 0);
			main.getConfig().set(event.getPlayer().getUniqueId().toString() + ".vie", 0);
			main.getConfig().set(event.getPlayer().getUniqueId().toString() + ".critique", 0);
			main.getConfig().set(event.getPlayer().getUniqueId().toString() + ".playerName", event.getPlayer().getName());
		}
		// TODO: Le joueur gagne de la vie en plus 11 > 12 alors qu'il n'as qu'un point en vie
		upgradePlayerHealth(event.getPlayer(), false);
	}
	
	@EventHandler
	public void onLevelUp(PlayerLevelChangeEvent event) {
		if(event.getOldLevel() < event.getNewLevel()) {
			if(!main.getConfig().isSet(event.getPlayer().getUniqueId().toString())) {
				main.getConfig().createSection(event.getPlayer().getUniqueId().toString());
				main.getConfig().set(event.getPlayer().getUniqueId().toString() + ".attrPoint", 0);
				main.getConfig().set(event.getPlayer().getUniqueId().toString() + ".force", 0);
				main.getConfig().set(event.getPlayer().getUniqueId().toString() + ".vie", 0);
				main.getConfig().set(event.getPlayer().getUniqueId().toString() + ".critique", 0);
				main.getConfig().set(event.getPlayer().getUniqueId().toString() + ".playerName", event.getPlayer().getName());
			}
			main.getConfig().set(event.getPlayer().getUniqueId().toString() + ".attrPoint", main.getConfig().getInt(event.getPlayer().getUniqueId().toString() + ".attrPoint") + 1);
			event.getPlayer().sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.incrementPointAttr").replace("&", "§"));
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onHitEvent(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			Random r = new Random();
			Double multiplier = 1D;
			if(r.nextDouble() * 100D <= (main.getConfig().getInt(player.getUniqueId() + ".critique".toString()) * main.getConfig().getDouble("modifiers.critique"))) {
				multiplier = main.getConfig().getDouble("modifiers.critiqueDamageModifier");
				player.sendMessage(main.getConfig().getString("messages.prefixMessage").replace("&", "§") + main.getConfig().getString("messages.critMessage").replace("&", "§"));
			}
			event.setDamage((event.getDamage() + (main.getConfig().getInt(player.getUniqueId().toString() + ".force") * main.getConfig().getDouble("modifiers.damage"))) * multiplier);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		setMaxHealthOnJoin(event.getPlayer());
	}
}
