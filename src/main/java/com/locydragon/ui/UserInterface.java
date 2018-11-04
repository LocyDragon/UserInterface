package com.locydragon.ui;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import com.locydragon.ui.async.AsyncPlayerInventoryProtect;
import com.locydragon.ui.commands.CommandBus;
import com.locydragon.ui.commands.SubCommandBasic;
import com.locydragon.ui.commands.sub.CloseInventorySub;
import com.locydragon.ui.commands.sub.OpenInventorySub;
import com.locydragon.ui.util.core.listener.HeldNoChangeListener;
import com.locydragon.ui.util.core.listener.RenderPlayer;
import com.locydragon.ui.util.core.packet.FakeInteractMaker;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class UserInterface extends JavaPlugin {
	public static Plugin instance;
	public static FileConfiguration dataFile = null;
	@Override
	public void onLoad() {
		File dataFile = new File(".//plugins//UserInterface//Data.dat");
		if (!dataFile.exists()) {
			try {
				dataFile.getParentFile().mkdirs();
				dataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		UserInterface.dataFile = YamlConfiguration.loadConfiguration(dataFile);
		RenderPlayer.monitorOn = toVector(UserInterface.dataFile.getStringList("monitor"));
		loadDefault();
		instance = this;
		Thread asyncAnti = new AsyncPlayerInventoryProtect();
		asyncAnti.setDaemon(true);
		asyncAnti.start();
		SubCommandBasic.addListener(new CloseInventorySub());
		SubCommandBasic.addListener(new OpenInventorySub());
	}

	@Override
	public void onEnable() {
		getLogger().info("Welcome to you User Interface!Version: "+this.getDescription().getVersion());
		Bukkit.getPluginManager().registerEvents(new HeldNoChangeListener(), this);
		Bukkit.getPluginCommand("ui").setExecutor(new CommandBus());
		FakeInteractMaker.manager = ProtocolLibrary.getProtocolManager();
	}

	@Override
	public void onDisable() {
		UserInterface.dataFile.set("monitor", RenderPlayer.monitorOn);
		try {
			UserInterface.dataFile.save(".//plugins//UserInterface//Data.dat");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Vector<String> toVector(List<String> which) {
		Vector<String> vector = new Vector<>();
		vector.addAll(which);
		return vector;
	}

	private void loadDefault() {
		File defaultFile = new File(".//plugins//UserInterface//gui//default.yml");
		if (!defaultFile.exists()) {
			defaultFile.getParentFile().mkdirs();
			try {
				defaultFile.createNewFile();
				FileConfiguration defaults = YamlConfiguration.loadConfiguration(defaultFile);
				defaults.set("background", "[empty]");
				defaults.save(defaultFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void debug() {
		Set<PacketType> types = new HashSet<>();
		types.remove(PacketType.Play.Client.FLYING);
		PacketType.values().forEach(x -> types.add(x));
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(PacketAdapter.params()
				.plugin(this)
				.clientSide()
				.listenerPriority(ListenerPriority.MONITOR)
				.gamePhase(GamePhase.PLAYING)
				.optionAsync()
				.options(ListenerOptions.SKIP_PLUGIN_VERIFIER)
				.types(PacketType.Play.Client.BLOCK_PLACE)

		) {
			@Override
			public void onPacketReceiving(PacketEvent e) {
				System.out.println(e.getPacketType().toString());
				System.out.println(e.getPacket().toString());
				System.out.println(e.getPacket().getIntegers().toString());
				System.out.println(e.getPacket().getFloat().toString());
				System.out.println(e.getPacket().getLongs().toString());
			}
		});
	}
}
