package com.locydragon.ui.async;

import com.locydragon.ui.UserInterface;
import com.locydragon.ui.util.core.listener.RenderPlayer;

import java.io.IOException;

@Deprecated
public class AsyncDataSaveJob extends Thread {
	@Override
	@Deprecated
	public void run() {
		while (true) {
			try {
				Thread.sleep(3000);
				UserInterface.dataFile.set("monitor", RenderPlayer.monitorOn);
				try {
					UserInterface.dataFile.save(".//plugins//UserInterface//Data.dat");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
