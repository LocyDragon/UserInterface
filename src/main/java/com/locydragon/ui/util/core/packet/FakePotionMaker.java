package com.locydragon.ui.util.core.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class FakePotionMaker {
	public static String nmsVersion =
			org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	public static void make(Player who, byte id, byte level, int dur) {
		PacketContainer container = FakeInteractMaker.manager.createPacket(PacketType.Play.Server.ENTITY_EFFECT);
		container.getIntegers().write(0, who.getEntityId());
		container.getIntegers().write(1, dur);
		container.getBytes().write(0, id);
		container.getBytes().write(1, level);
		container.getBytes().write(2, (byte)0x02);
		try {
			FakeInteractMaker.manager.sendServerPacket(who ,container);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static void remove(Player who, int id) {
		if (nmsVersion.contains("v1_8") || nmsVersion.contains("v1_7") || nmsVersion.contains("v1_6") || nmsVersion.contains("v1_5")
				|| nmsVersion.contains("v1_4")) {
			PacketContainer container = FakeInteractMaker.manager.createPacket(PacketType.Play.Server.REMOVE_ENTITY_EFFECT);
			container.getIntegers().write(0, who.getEntityId());
			container.getIntegers().write(1, id);
			try {
				FakeInteractMaker.manager.sendServerPacket(who ,container);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			PacketContainer container = FakeInteractMaker.manager.createPacket(PacketType.Play.Server.REMOVE_ENTITY_EFFECT);
			container.getIntegers().write(0, who.getEntityId());
			try {
				Class mobClass = Class.forName("net.minecraft.server."+nmsVersion+".MobEffectList");
				try {
					Object effectInstance = mobClass.getMethod("fromId", int.class).invoke(null, id);
					container.getSpecificModifier(mobClass).write(0, effectInstance);
					try {
						FakeInteractMaker.manager.sendServerPacket(who ,container);
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				FakeInteractMaker.manager.sendServerPacket(who ,container);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
}
