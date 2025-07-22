package fr.bilscript.bilzone;

import fr.bilscript.bilzone.commands.ZoneCommand;
import fr.bilscript.bilzone.database.Database;
import fr.bilscript.bilzone.manager.ZoneManager;
import fr.bilscript.bilzone.manager.wand.WandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BilZone extends JavaPlugin {

	private static BilZone instance;
	private Database database;
	private WandManager wandManager;
	private ZoneManager zoneManager;

	@Override
	public void onEnable() {
		instance = this;
		this.saveDefaultConfig();
		this.reloadConfig();
		wandManager = new WandManager(this);
		database = new Database();
		zoneManager = new ZoneManager(this);

		getCommand("zone").setExecutor(new ZoneCommand());
	}

	public static BilZone getInstance() {
		return instance;
	}

	public WandManager getWandManager() {
		return this.wandManager;
	}

	public ZoneManager getZoneManager() {
		return this.zoneManager;
	}

	public Database getDatabase() {
		return this.database;
	}
}
