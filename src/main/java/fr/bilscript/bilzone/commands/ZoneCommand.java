package fr.bilscript.bilzone.commands;

import fr.bilscript.bilzone.BilZone;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ZoneCommand implements CommandExecutor, TabCompleter {

	private static final List<String> PARAMETRES = List.of("wand", "create", "remove", "info", "list");

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args) {
		if (!(sender instanceof Player player))
			return true;

		if (args.length == 0) {
			player.sendMessage("§cUsage: /zone <wand | create | list | remove | info>");
			return true;
		}

		BilZone instance = BilZone.getInstance();
		switch (args[0].toLowerCase()) {
			case "wand":
				instance.getWandManager().giveWand(player);
				break;
			case "create":
				if (args.length != 2) {
					player.sendMessage("§cSyntax Error\n§aUsage: /zone create <zone id>");
					return true;
				}
				instance.getZoneManager().createZone(player, args[1]);
				break;
			case "list":
				instance.getZoneManager().printZoneList(player);
				break;
			case "remove":
				if (args.length != 2) {
					player.sendMessage("§cSyntax Error\n§aUsage: /zone remove <zone id>");
					return true;
				}
				instance.getZoneManager().removeZone(args[1], player);
				break;
			case "info":
				if (args.length != 2) {
					player.sendMessage("§cSyntax Error\n§aUsage: /zone info <zone id>");
					return true;
				}
				instance.getZoneManager().zoneInfo(player, args[1]);
				break;
		}
		return false;
	}

	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
		if (args.length == 1)
			return PARAMETRES.stream().filter(string -> string.toLowerCase().startsWith(args[0].toLowerCase())).toList();
		if (args.length == 2 && List.of("create", "remove", "info").contains(args[0])) {
			List<String> ids = new ArrayList<>(BilZone.getInstance().getZoneManager().getZones().keySet());
			String input = args[1].toLowerCase();
			if (ids.isEmpty())
				return List.of("<Zone ID>").stream().filter(s -> s.startsWith(input)).toList();
			else {
				return ids.stream().filter(s -> s.startsWith(input)).toList();
			}
		}
		return List.of();
	}

}
