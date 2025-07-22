package fr.bilscript.bilzone.manager.zone;

import org.bukkit.block.Block;

public record ZonePos(int x, int y, int z) {

	public static ZonePos from(final Block block) {
		return new ZonePos(block.getX(), block.getY(), block.getZ());
	}
}
