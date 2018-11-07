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
import com.locydragon.ui.util.core.packet.FakePotionMaker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
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
			RenderPlayer.addTo(who);
			MapView map = Bukkit.getMap(who.getItemInHand().getDurability());
			mapInterface.update();
			try {
				for (MapRenderer renderer : map.getRenderers()) {
					map.removeRenderer(renderer);
				}
			} catch (NullPointerException exc) {} finally {
				map.setScale(MapView.Scale.FARTHEST);
				if (!mapInterface.empty) {
					map.addRenderer(mapInterface.render);
				}
				who.sendMap(map);
				FakePotionMaker.make(who, (byte)14, (byte)3, (32767));
				FakePotionMaker.make(who, (byte)15, (byte)3, (32767));
			}
		});
	}

	private static void faceToGround(Player who) {
		Location loc = who.getLocation().clone();
		loc.setPitch(90);
		loc.setYaw(0);
		who.teleport(loc);
	}
}
