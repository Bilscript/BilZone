package fr.bilscript.bilzone.manager.wand;

import fr.bilscript.bilzone.BilZone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class WandManager implements Listener {

	private static final NamespacedKey KEY_WAND = new NamespacedKey(BilZone.getInstance(), "key_wand");
	private final Map<UUID, PlayerSelection> selection = new HashMap<>();

	public WandManager(BilZone main) {
		Bukkit.getPluginManager().registerEvents(this, main);
	}

	public ItemStack createWand() {
		ItemStack wand = new ItemStack(Material.WOODEN_AXE);
		wand.editMeta(meta -> {
			meta.setDisplayName("§6Zone creator");
			List<String> lore = new ArrayList<>();
			lore.add("§bLeft click for position 1");
			lore.add("§dRight click for position 2");
			meta.addEnchant(Enchantment.LURE, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
			meta.setLore(lore);
			meta.getPersistentDataContainer().set(KEY_WAND, PersistentDataType.BOOLEAN, true);
		});
		return wand;
	}

	public void giveWand(Player player) {
		if (!player.getInventory().addItem(createWand()).isEmpty())
			player.sendMessage("§cYour inventory is full, impossible to give you the wand!");
		else
			player.sendMessage("§aYou received the Wand!");
	}

	@EventHandler
	private void onClickEvent(PlayerInteractEvent event) {

		if (event.getHand() != EquipmentSlot.HAND) return;

		Action action = event.getAction();
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();

		ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
		if (itemMeta == null)
			return;
		PersistentDataContainer container = itemMeta.getPersistentDataContainer();
		final PlayerSelection selection = this.selection.computeIfAbsent(uuid, u -> new PlayerSelection());
		final Block block = event.getClickedBlock();
		if (block == null)
			return;
		if (!container.has(WandManager.KEY_WAND))
			return;
		final Location location = block.getLocation();
		if (action.isLeftClick()) {
			selection.setFirst(location);
			player.sendMessage("§aYou select a first block in :" + formatLocation(block.getLocation()));
			event.setCancelled(true);
		} else if (action.isRightClick()) {
			selection.setSecond(location);
			player.sendMessage("§cYou select a second block in :" + formatLocation(block.getLocation()));
			event.setCancelled(true);
		}
	}

	private String formatLocation(Location loc) {
		return "x: " + loc.getBlockX() + ", y: " + loc.getBlockY() + ", z: " + loc.getBlockZ() + ".";
	}

	public PlayerSelection getSelection(Player player) {
		return selection.get(player.getUniqueId());
	}
}
