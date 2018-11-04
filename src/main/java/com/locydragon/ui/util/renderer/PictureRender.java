package com.locydragon.ui.util.renderer;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class PictureRender extends MapRenderer {
	private boolean rendered = false;
	private Image picture = null;

	public PictureRender(File input) {
		//super(false);
		try {
			InputStream stream = new FileInputStream(input);
			this.picture = MapPalette.resizeImage(ImageIO.read(stream));
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
		//if (rendered) {
			//player.sendMap(mapView);
			//return;
		//}
		for (int i = 0;i < mapCanvas.getCursors().size();i++) {
			mapCanvas.getCursors().removeCursor(mapCanvas.getCursors().getCursor(i));
		}
		mapCanvas.drawImage(0, 0, this.picture);
		rendered = true;
	}
}
