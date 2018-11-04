package com.locydragon.ui.commands.sub;

import com.locydragon.ui.UserInterfaceAPI;
import com.locydragon.ui.commands.SubCmdRunner;
import com.locydragon.ui.commands.SubCommandInfo;

public class OpenInventorySub implements SubCmdRunner {
	/**
	 * 指令前缀
	 */
	public static final String CMD_PREFIX = "open";
	/**
	 * 期望指令长度
	 */
	public static final Integer LENGTH_EXPECT = 2;

	@Override
	public void onSubCommand(SubCommandInfo info) {
		if (info.args[0].equalsIgnoreCase(CMD_PREFIX)) {
			if (info.args.length == LENGTH_EXPECT) {
				if (!UserInterfaceAPI.uiExists(info.args[1])) {
					info.sender.sendMessage("§3[UserInterface] §eUI界面不存在.");
					return;
				}
				UserInterfaceAPI.openUI(info.getSender(), info.args[1]);
			} else {
				info.sender.sendMessage("§3[UserInterface] §e请使用/ui open [GUI界面名称]");
			}
		}
	}
}
