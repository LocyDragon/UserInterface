package com.locydragon.ui.util;

import java.util.ArrayList;
import java.util.List;

public class LoreFactory {
	List<String> lore = new ArrayList<>();
	public LoreFactory append(String obj) {
		lore.add(obj);
		return this;
	}

	public List<String> get() {
		return this.lore;
	}
}
