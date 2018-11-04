package com.locydragon.ui.util.core.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class FakeInteractMaker {
	public static ProtocolManager manager;
	public static String nmsVersion =
			org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	public static void createFakeInteract(Player who) {
		if (nmsVersion.contains("v1_8") || nmsVersion.contains("v1_7") || nmsVersion.contains("v1_6") || nmsVersion.contains("v1_5")
				|| nmsVersion.contains("v1_4")) {
			PacketContainer container = manager.createPacket(PacketType.Play.Client.BLOCK_PLACE);
			container.getBlockPositionModifier().write(0, new BlockPosition(-1, -1, -1));
			container.getIntegers().write(0, 255);
			container.getItemModifier().write(0, who.getItemInHand());
			container.getFloat().write(0, 0.0F);
			container.getFloat().write(1, 0.0F);
			container.getFloat().write(2, 0.0F);
			container.getLongs().write(0, System.currentTimeMillis());
			try {
				manager.recieveClientPacket(who, container);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			PacketContainer container = manager.createPacket(PacketType.Play.Client.USE_ITEM);
			container.getBlockPositionModifier().write(0, new BlockPosition(-1, -1, -1));
			container.getDirections().write(0, EnumWrappers.Direction.DOWN);
			container.getHands().write(0, EnumWrappers.Hand.MAIN_HAND);
			container.getFloat().write(0, 0.0F);
			container.getFloat().write(1, 0.0F);
			container.getFloat().write(2, 0.0F);
			container.getLongs().write(0, System.currentTimeMillis());
			try {
				manager.recieveClientPacket(who, container);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
}
