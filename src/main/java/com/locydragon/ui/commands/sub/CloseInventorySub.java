package com.locydragon.ui.commands.sub;

import com.locydragon.ui.UserInterfaceAPI;
import com.locydragon.ui.commands.SubCmdRunner;
import com.locydragon.ui.commands.SubCommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CloseInventorySub  implements SubCmdRunner {
	/**
	 * 指令前缀
	 */
	public static final String CMD_PREFIX = "close";
	/**
	 * 期望指令长度
	 */
	public static final Integer LENGTH_EXPECT = 2;

	@Override
	public void onSubCommand(SubCommandInfo info) {
		if (info.args[0].equalsIgnoreCase(CMD_PREFIX)) {
			if (info.args.length == LENGTH_EXPECT) {
				Player target = Bukkit.getPlayer(info.args[1]);
				if (target == null) {
					info.sender.sendMessage("§3[UserInterface] §e指定玩家不存在!");
					return;
				}
				UserInterfaceAPI.closeUI(target);
			} else {
				UserInterfaceAPI.closeUI(info.sender);
			}
		}
	}
}
