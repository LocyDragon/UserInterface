package com.locydragon.ui.util.core.listener.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KeyNoEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private KeyNo keyNo;

	public KeyNoEvent(final Player player, KeyNo keyNo) {
		this.player = player;
		this.keyNo = keyNo;
	}

	public Player getPlayer() {
		return this.player;
	}

	public KeyNo getKeyNo() {
		return this.keyNo;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
