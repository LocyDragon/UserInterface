package com.locydragon.ui.util.core.listener.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class KeyNoListener implements Listener {
	@EventHandler
	public void onKeyNo(KeyNoEvent e) {
		e.getPlayer().sendMessage(e.getKeyNo().toString());
	}
}
