package fr.isiz.epikube.mobs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.isiz.epikube.CustomMobPlugin;
import fr.isiz.epikube.manager.MobsManager;
import fr.isiz.epikube.utils.RandomUtils;

/**
 * Kamikaze
 * @author KingRider26
 *
 */
public class Kamikaze extends MobsManager {
	
	public Kamikaze() {
		super(ChatColor.RED + "Kamikaze", 20.0D, 100, EntityType.SKELETON);
	}
	
	@EventHandler
	public void on(CreatureSpawnEvent event) { // Event Bukkit when creature spawn on the world
		Entity entity = event.getEntity(); // Get the entity spawned
		if (event.getSpawnReason().equals(SpawnReason.CUSTOM)) return; // If it's a plugin I return the method.
		if (event.getSpawnReason().equals(SpawnReason.NATURAL)) { // If the cause is natural I continue.
			if (entity instanceof Skeleton) { // If entity equals a Skeleton
				Skeleton s = (Skeleton) entity;
				if (RandomUtils.getRandomByPercent(this.getLuckyspawn())) { // Lucky percent for spawn a mob.
					s.setCustomName(this.getDisplayName()); // Set a name for mob.
					s.setCustomNameVisible(true); // The name is visible
					s.setHealth(getLife()); // Put a life for mob
					s.getEquipment().setHelmet(new ItemStack(Material.TNT)); // The skull of mob is a Tnt.
					s.getEquipment().setItemInMainHand(new ItemStack(Material.TNT)); // In main hand he hold a Tnt.
					runTaskMobs(s); // Algo
				}
			}
		}
	}
	
	/**
	 * Run task mobs
	 * @param s
	 */
	private void runTaskMobs(Skeleton s) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for (Entity entity : s.getWorld().getNearbyEntities(s.getLocation(), 3.0f, 3.0f, 3.0f)) { // If a entity is nearby of Skeleton
					if (entity instanceof Player) { // If the entity is a Player
						entity.getWorld().createExplosion(entity.getLocation(), 3.0f); // I create a explosion
						this.cancel(); // Stop the task.
						return;
					}
				}
			}
		}.runTaskTimer(CustomMobPlugin.instance, 1L, 0L);
	}
}
