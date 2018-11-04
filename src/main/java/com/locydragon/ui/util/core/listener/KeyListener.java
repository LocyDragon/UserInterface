package com.locydragon.ui.util.core.listener;

import com.locydragon.ui.util.core.listener.events.KeyNo;
import com.locydragon.ui.util.core.listener.events.KeyNoEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import java.util.concurrent.ConcurrentHashMap;


public class KeyListener implements Listener {
	public static Double ERGONOMICS = Math.pow(Math.E / 9, Math.sqrt(3));
	private static ConcurrentHashMap<String,Long> timeStamp = new ConcurrentHashMap<>();
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (RenderPlayer.has(e.getPlayer())) {
			if (e.getTo().getZ() - e.getFrom().getZ() > ERGONOMICS) {
				callEventWithTimeStamp(new KeyNoEvent(e.getPlayer(), KeyNo.W), System.currentTimeMillis());
			} else if (e.getFrom().getZ() - e.getTo().getZ() > ERGONOMICS) {
				callEventWithTimeStamp(new KeyNoEvent(e.getPlayer(), KeyNo.S), System.currentTimeMillis());
			} else if (e.getTo().getX() - e.getFrom().getX() > ERGONOMICS) {
				callEventWithTimeStamp(new KeyNoEvent(e.getPlayer(), KeyNo.A), System.currentTimeMillis());
			} else if (e.getFrom().getX() - e.getTo().getX() > ERGONOMICS) {
				callEventWithTimeStamp(new KeyNoEvent(e.getPlayer(), KeyNo.D), System.currentTimeMillis());
			}
			e.getPlayer().teleport(e.getFrom());
		}
	}

	private static void callEventWithTimeStamp(KeyNoEvent e, long timeStamp) {
		if (KeyListener.timeStamp.keySet().contains(e.getPlayer().getName().toLowerCase())) {
			if (timeStamp - KeyListener.timeStamp.get(e.getPlayer().getName().toLowerCase()) < 350) {
				return;
			}
		}
		Bukkit.getPluginManager().callEvent(e);
		KeyListener.timeStamp.put(e.getPlayer().getName().toLowerCase(), timeStamp);
	}
}
