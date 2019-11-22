package fr.isiz.epikube.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Class to register the mobs.
 * @author IsiZ_
 */
public abstract class MobsManager implements Listener {
	
	private static File mobsFile;
	private static FileConfiguration config;
	private static List<MobsManager> mobs;
	
	static {
		mobs = new ArrayList<>();
	}
	
	private String entity;
	private String displayName;
	private double life;
	private int luckyspawn;
	
	/**
	 * Main constructor
	 * @param displayName
	 * @param life
	 * @param luckySpawn
	 * @param entity
	 */
	public MobsManager(String displayName, double life, int luckySpawn, EntityType entity) {
		this.displayName = displayName;
		this.life = life;
		this.luckyspawn = luckySpawn;
		this.entity = entity.toString();
	}
	
	/*
	 * Getter
	 */
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public double getLife() {
		return life;
	}
	
	public int getLuckyspawn() {
		return luckyspawn;
	}
	
	/*
	 * EventHandler
	 */
	
	@EventHandler
	public void creatureSpawn(CreatureSpawnEvent event) {}
	@EventHandler
	public void damageEntity(EntityDamageByEntityEvent event) {}
	
	/**
	 * Equipe une armor à votre mob
	 * @param entity
	 */
	public void equipArmor(Entity entity, Material mat) {
		// Entity no null
		if (entity == null) return;
		// An living entity ?
		if (entity instanceof LivingEntity) {
			// If yes, I set the equipment on the entity.
			// Else nothing
			LivingEntity lentity = (LivingEntity) entity;
			lentity.getEquipment().setHelmet(new ItemStack(Material.MOSSY_COBBLESTONE));
			lentity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			lentity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			lentity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
		}
	}
	
	/**
	 * Register mobs
	 * @param plugin
	 * @param mobs
	 */
	public static boolean registerMobs(Plugin plugin, MobsManager mobs) {
		if (!MobsManager.mobs.contains(mobs)) {
			if (mobsFile == null || config == null) {
				Bukkit.getConsoleSender().sendMessage("File or config doesn't exist.");
				return false;
			}
			plugin.getConfig().set("displayName", mobs.displayName);
			plugin.getConfig().set("type", mobs.entity);
			plugin.getConfig().set("life", mobs.life);
			plugin.getConfig().set("naturally-spawn", mobs.luckyspawn);
			try {
				plugin.getConfig().save(mobsFile);
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage("Contact @IsiZ_ to show error line: 118.MobsManager");
				return false;
			}
			Bukkit.getPluginManager().registerEvents(mobs, plugin);
			MobsManager.mobs.add(mobs);
			return true;
		}
		return false;
	}
	
	/**
	 * Get by Name
	 * @param displayName
	 * @return
	 */
	public static MobsManager getByName(String displayName) {
		for (MobsManager mobs : MobsManager.mobs)
			if (mobs.getDisplayName().equals(displayName))
				return mobs;
		return null;
	}
	
	/**
	 * No need to use this method.
	 */
	public static void setupConfig(File file) {
		if (!file.exists()) {
			new FileNotFoundException(file.getName() + " not found. You probably need to create it.").printStackTrace();
			return;
		}
		mobsFile = file;
		config = YamlConfiguration.loadConfiguration(file);
	}
	
}
