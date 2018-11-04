package com.locydragon.ui.util;

import com.locydragon.ui.util.core.MapRenderObject;
import com.locydragon.ui.util.core.PlayerData;
import com.locydragon.ui.util.renderer.PictureRender;
import org.bukkit.entity.Player;

import java.io.File;

public class MapInterface {
	public boolean empty = true;
	public PictureRender render;
	private Player player;
	private PlayerData yawAndPitch = new PlayerData();
	public MapInterface(Player target) {
		this.player = target;
		empty = true;
	}

	public void render() {
		yawAndPitch.pitch = this.player.getLocation().getPitch();
		yawAndPitch.yaw = this.player.getLocation().getYaw();
		MapRenderObject.render(this, this.player);
	}

	public void update() {
		if (this.render == null) {
			empty = true;
		} else {
			empty = false;
		}
	}

	public void setBackGround(File image) {
		if (!image.exists()) {
			return;
		}
		if (!(image.getName().endsWith(".png") || image.getName().endsWith(".jpg") || image.getName().endsWith(".bmp"))) {
			return;
		}
		this.render = new PictureRender(image);
		empty = false;
	}

	public PlayerData getData() {
		return this.yawAndPitch;
	}
}
