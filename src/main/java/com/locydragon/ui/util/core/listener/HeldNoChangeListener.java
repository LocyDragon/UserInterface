package com.locydragon.ui.util.core.listener;

import com.locydragon.ui.UserInterface;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class HeldNoChangeListener implements Listener {
	@EventHandler(priority = EventPriority.MONITOR)
	public void onHeld(PlayerItemHeldEvent e) {
		if (RenderPlayer.has(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryOpen(InventoryClickEvent e) {
		if (RenderPlayer.has((Player)e.getWhoClicked())) {
			e.setCancelled(true);
			((Player) e.getWhoClicked()).updateInventory();
			Bukkit.getScheduler().runTaskLater(UserInterface.instance, () -> {
				e.getWhoClicked().closeInventory();
			}, 3);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDropItem(PlayerDropItemEvent e) {
		if (RenderPlayer.has(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInteraceAtBlock(PlayerInteractEvent e) {
		if (RenderPlayer.has(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onMove(PlayerMoveEvent e) {
		if (RenderPlayer.has(e.getPlayer())) {
			if (e.getTo().getPitch() != e.getFrom().getPitch() || e.getTo().getYaw() != e.getFrom().getYaw()) {
				faceToGround(e.getPlayer());
			}
		}
	}

	private static void faceToGround(Player who) {
		Location loc = who.getLocation().clone();
		loc.setPitch(90);
		loc.setYaw(0);
		who.teleport(loc);
	}
}
