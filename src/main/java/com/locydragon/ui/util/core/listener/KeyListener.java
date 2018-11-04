package com.locydragon.ui.util.core.listener;

import com.locydragon.ui.util.core.listener.events.KeyNo;
import com.locydragon.ui.util.core.listener.events.KeyNoEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class KeyListener implements Listener {
	public static final Double ERGONOMICS = Math.pow(Math.E / 10, Math.sqrt(2));
	public static ConcurrentLinkedQueue<String> doubleClickAnti = new ConcurrentLinkedQueue<>();
	public static Executor pool = Executors.newCachedThreadPool();
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (RenderPlayer.has(e.getPlayer()) && !doubleClickAnti.contains(e.getPlayer().getName().toLowerCase())) {
			if (e.getTo().getZ() - e.getFrom().getZ() > ERGONOMICS) {
				Bukkit.getPluginManager().callEvent(new KeyNoEvent(e.getPlayer(), KeyNo.W));
			} else if (e.getFrom().getZ() - e.getTo().getZ() > ERGONOMICS) {
				Bukkit.getPluginManager().callEvent(new KeyNoEvent(e.getPlayer(), KeyNo.S));
			} else if (e.getTo().getX() - e.getFrom().getX() > ERGONOMICS) {
				Bukkit.getPluginManager().callEvent(new KeyNoEvent(e.getPlayer(), KeyNo.A));
			} else if (e.getFrom().getX() - e.getTo().getX() > ERGONOMICS) {
				Bukkit.getPluginManager().callEvent(new KeyNoEvent(e.getPlayer(), KeyNo.D));
			}
			e.getPlayer().teleport(e.getFrom());
			doubleClickAnti.add(e.getPlayer().getName().toLowerCase());
			pool.execute(() -> {
				try {
					Thread.sleep((long) (ERGONOMICS * 2000));
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				doubleClickAnti.remove(e.getPlayer().getName().toLowerCase());
			});
		}
	}
}
