package com.locydragon.ui.util.core.listener;

import org.bukkit.entity.Player;
import java.util.Vector;

public class RenderPlayer {
	public static Vector<String> monitorOn = new Vector<>();

	public static boolean has(Player who) {
		return monitorOn.contains(who.getName().toLowerCase());
	}

	public static void addTo(Player who) {
		monitorOn.add(who.getName().toLowerCase());
	}

	public static void remove(Player who) {
		monitorOn.remove(who.getName().toLowerCase());
	}
}
