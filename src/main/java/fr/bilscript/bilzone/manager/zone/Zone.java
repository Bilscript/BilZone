package fr.bilscript.bilzone.manager.zone;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Zone {
	private final String id;
	private final ZonePos pos1, pos2;
	private final String world;

	public Zone(final String id, final Block block1, final Block block2) {
		this.id = id;
		this.pos1 = ZonePos.from(block1);
		this.pos2 = ZonePos.from(block2);
		this.world = block1.getWorld().getName();
	}

	public boolean isInside(final Location loc) {
		final double minX = Math.min(this.pos1.x(), this.pos2.x());
		final double maxX = Math.max(this.pos1.x(), this.pos2.x());
		final double minY = Math.min(this.pos1.y(), this.pos2.y());
		final double maxY = Math.max(this.pos1.y(), this.pos2.y());
		final double minZ = Math.min(this.pos1.z(), this.pos2.z());
		final double maxZ = Math.max(this.pos1.z(), this.pos2.z());

		return (loc.x() >= minX && loc.x() <= maxX) &&
				(loc.y() >= minY && loc.y() <= maxY) &&
				(loc.z() >= minZ && loc.z() <= maxZ);
	}

	public World getWorld() {
		return Bukkit.getWorld(this.world);
	}

	public ZonePos getPos1() {
		return this.pos1;
	}

	public ZonePos getPos2() {
		return this.pos2;
	}

	public String getId() {
		return this.id;
	}
}
