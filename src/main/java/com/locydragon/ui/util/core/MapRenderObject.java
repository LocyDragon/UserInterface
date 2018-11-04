package com.locydragon.ui.util.core;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.locydragon.ui.UserInterface;
import com.locydragon.ui.util.LoreFactory;
import com.locydragon.ui.util.MapInterface;
import com.locydragon.ui.util.Schedulers;
import com.locydragon.ui.util.core.listener.RenderPlayer;
import com.locydragon.ui.util.core.packet.FakeInteractMaker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

public class MapRenderObject {
	public static ProtocolManager manager;
	public static ConcurrentHashMap<String,MapInterface> renderingPlayer = new ConcurrentHashMap<>();

	public static void render(MapInterface mapInterface, Player who) {
		if (renderingPlayer.keySet().contains(who.getName().toLowerCase())) {
			throw new IllegalArgumentException("Called render twice!");
		}
		renderingPlayer.put(who.getName().toLowerCase(), mapInterface);
		faceToGround(who);
		mapInterface.getData().inHand = who.getItemInHand();
		who.setItemInHand(new ItemStack(Material.EMPTY_MAP));
		FakeInteractMaker.createFakeInteract(who);
		Schedulers.syncSafeTask(() -> {
			MapView map = Bukkit.getMap(who.getItemInHand().getDurability());
			mapInterface.update();
			for (MapRenderer renderer : map.getRenderers()) {
				map.removeRenderer(renderer);
			}
			map.setScale(MapView.Scale.FARTHEST);
			if (!mapInterface.empty) {
				map.addRenderer(mapInterface.render);
			}
			who.sendMap(map);
			RenderPlayer.addTo(who);
			who.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 200));
		});
	}

	private static void faceToGround(Player who) {
		Location loc = who.getLocation().clone();
		loc.setPitch(90);
		loc.setYaw(0);
		who.teleport(loc);
	}
}
