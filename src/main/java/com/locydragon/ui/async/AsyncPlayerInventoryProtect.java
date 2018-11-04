package com.locydragon.ui.async;

import com.locydragon.ui.UserInterface;
import com.locydragon.ui.util.core.MapRenderObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AsyncPlayerInventoryProtect extends Thread {
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (Player online : Bukkit.getOnlinePlayers()) {
				if (MapRenderObject.renderingPlayer.keySet().contains(online.getName().toLowerCase())) {
					continue;
				}
				for (ItemStack item : online.getInventory()) {
					if (item != null && item.getType() == Material.MAP && item.hasItemMeta() && item.getItemMeta().hasLore()) {
						for (String lore : item.getItemMeta().getLore()) {
							if (lore.equals(ChatColor.BLUE+"UI Map")) {
								clearSync(online, item);
							}
						}
					}
				}
			}
		}
	}

	public void clearSync(Player who, ItemStack which) {
		Bukkit.getScheduler().runTask(UserInterface.instance, () -> {
			who.getInventory().remove(which);
			who.updateInventory();
		});
	}
}
