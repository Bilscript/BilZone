package fr.bilscript.bilzone.database;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.bilscript.bilzone.BilZone;
import fr.bilscript.bilzone.manager.zone.Zone;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Database {

	private static final Gson GSON = new GsonBuilder().serializeNulls().create();

	public Map<String, Zone> load() {
		try (final Reader reader = new FileReader(this.getFile())) {
			final Type type = new TypeToken<Map<String, Zone>>() {
			}.getType();
			return GSON.fromJson(reader, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new HashMap<>();
	}

	public void save() {
		try (final FileWriter writer = new FileWriter(this.getFile())) {
			GSON.toJson(BilZone.getInstance().getZoneManager().getZones(), writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File getFile() {
		return Path.of(BilZone.getInstance().getDataFolder().getPath(), "data.json").toFile();
	}
}
