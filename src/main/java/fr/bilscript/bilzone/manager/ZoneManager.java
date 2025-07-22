package fr.bilscript.bilzone.manager;

import fr.bilscript.bilzone.BilZone;
import fr.bilscript.bilzone.manager.wand.PlayerSelection;
import fr.bilscript.bilzone.manager.zone.Zone;
import fr.bilscript.bilzone.manager.zone.ZonePos;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ZoneManager implements Listener {
	private final Map<String, Zone> zones;

	public ZoneManager(BilZone main) {
		Bukkit.getPluginManager().registerEvents(this, main);
		this.zones = main.getDatabase().load();
	}

	@EventHandler(ignoreCancelled = true)
	private void onMove(PlayerMoveEvent e) {
		final List<Zone> from = this.zones.values().stream().filter(zone -> zone.isInside(e.getFrom().getBlock().getLocation()))
				.collect(Collectors.toCollection(ArrayList::new));
		final List<Zone> to = this.zones.values().stream().filter(zone -> zone.isInside(e.getTo().getBlock().getLocation()))
				.collect(Collectors.toCollection(ArrayList::new));

		for (Zone zone : new ArrayList<>(from)) {
			if (to.contains(zone)) {
				from.remove(zone);
				to.remove(zone);
			}
		}

		for (Zone zone : from) {
			Bukkit.broadcastMessage(getMessage("message.leave-message", zone.getId()));
		}
		for (Zone zone : to) {
			Bukkit.broadcastMessage(getMessage("message.enter-message", zone.getId()));
		}
	}

	public void createZone(Player player, String id) {

		PlayerSelection selection = BilZone.getInstance().getWandManager().getSelection(player);

		if (selection == null || !selection.isValid()) {
			player.sendMessage("§9You must select two positions with the wand!");
			return;
		}
		if (getZone(id) != null) {
			player.sendMessage("§cAn zone with this id already exists");
			return;
		}
		Zone newZone = new Zone(id, selection.getFirst().getBlock(), (Block) selection.getSecond().getBlock());
		addZone(newZone);
		player.sendMessage("§aYou create a new zone named " + id + "!");
	}

	public boolean addZone(Zone zone) {
		if (this.zones.containsKey(zone.getId()))
			return false;
		this.zones.put(zone.getId(), zone);
		BilZone.getInstance().getDatabase().save();
		return true;
	}

	public void zoneInfo(Player player, String id) {
		Zone zone = getZone(id);
		if (zone == null) {
			player.sendMessage("§cNo zone exists with that name!");
			return;
		}
		player.sendMessage("§6Zone Info §7» §a" + zone.getId());
		player.sendMessage("§e- §6World: §f" + zone.getWorld().getName());
		player.sendMessage("§e- §6First position: §f" + getFormatPos(zone.getPos1()));
		player.sendMessage("§e- §6Second position: §f" + getFormatPos(zone.getPos2()));
	}

	public String getFormatPos(ZonePos pose) {
		return ("x: " + pose.x() + ", y: " + pose.y() + ", z: " + pose.z() + ".");
	}

	public void printZoneList(Player player) {
		if (zones.isEmpty()) {
			player.sendMessage("§cThere are no registered zones at the moment.");
			return;
		}

		player.sendMessage("§6List of all existing zones:");
		for (Zone zone : zones.values()) {
			player.sendMessage("§e- §a" + zone.getId());
		}

	}

	public Zone getZone(String id) {
		return zones.get(id);
	}

	public boolean removeZone(String id, Player player) {
		Zone removed = zones.remove(id);
		BilZone.getInstance().getDatabase().save();
		if (removed != null) {
			player.sendMessage("§aZone '" + id + "' has been removed successfully.");
			return true;
		} else {
			player.sendMessage("§cNo zone found with the name '" + id + "'.");
			return false;
		}
	}

	private String getMessage(String path, String replace) {
		return BilZone.getInstance().getConfig().getString(path).replace("%zone%", replace);
	}

	public Map<String, Zone> getZones() {
		return zones;
	}

	public void clearZones() {
		zones.clear();
		BilZone.getInstance().getDatabase().save();
	}
}
