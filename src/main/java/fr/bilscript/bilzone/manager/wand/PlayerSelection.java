package fr.bilscript.bilzone.manager.wand;

import org.bukkit.Location;

public class PlayerSelection {
	private Location first;
	private Location second;

	public boolean isValid() {
		return (first != null && second != null);
	}

	//Left Click
	public void setFirst(final Location first) {
		this.first = first;
	}

	//Right Click
	public void setSecond(final Location second) {
		this.second = second;
	}

	public Location getFirst() {
		return this.first;
	}

	public Location getSecond() {
		return this.second;
	}
}
