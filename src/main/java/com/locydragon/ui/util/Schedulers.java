package com.locydragon.ui.util;

import com.locydragon.ui.UserInterface;
import org.bukkit.Bukkit;

public class Schedulers {

	public static void syncSafeTask(Runnable runnable) {
		Bukkit.getScheduler().runTaskLater(UserInterface.instance, runnable, 2);
	}
}
