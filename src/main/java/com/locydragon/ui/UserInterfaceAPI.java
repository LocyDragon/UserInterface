package com.locydragon.ui;

import com.locydragon.ui.util.MapInterface;
import com.locydragon.ui.util.core.MapRenderObject;
import com.locydragon.ui.util.core.listener.RenderPlayer;
import com.locydragon.ui.util.core.packet.FakePotionMaker;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.io.File;

public class UserInterfaceAPI {
	public static void openEmtptyUI(Player who) {
		new MapInterface(who).render();
	}

	public static void closeUI(Player who) {
		MapInterface mapInterface = MapRenderObject.renderingPlayer.get(who.getName().toLowerCase());
		if (mapInterface == null) {
			return;
		}
		who.setItemInHand(mapInterface.getData().inHand == null ? new ItemStack(Material.AIR) : mapInterface.getData().inHand);
		MapRenderObject.renderingPlayer.remove(who.getName().toLowerCase());
		RenderPlayer.remove(who);
		who.updateInventory();
		FakePotionMaker.remove(who, (byte)14);
		FakePotionMaker.remove(who, (byte)15);
	}

	public static boolean openUI(Player who, String uiFileName) {
		File yamlGUI = new File(".//plugins//UserInterface//gui//"+uiFileName+".yml");
		if (!yamlGUI.exists()) {
			return false;
		}
		if (getInventoryOpening(who) != null) {
			return false;
		}
		YamlConfiguration yamlUI = YamlConfiguration.loadConfiguration(yamlGUI);
		if (yamlUI.getString("background", "[empty]").equalsIgnoreCase("[empty]")) {
			openEmtptyUI(who);
			return true;
		}
		MapInterface mapInterface = new MapInterface(who);
		mapInterface.setBackGround(new File(".//plugins//UserInterface//"+yamlUI.getString("background")));
		mapInterface.update();
		mapInterface.render();
		return true;
	}

	public static MapInterface getInventoryOpening(Player who) {
		return MapRenderObject.renderingPlayer.get(who.getName().toLowerCase());
	}

	public static boolean uiExists(String uiFileName) {
		return new File(".//plugins//UserInterface//gui//"+uiFileName+".yml").exists();
	}
}
