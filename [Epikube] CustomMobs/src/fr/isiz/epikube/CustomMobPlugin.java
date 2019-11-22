package fr.isiz.epikube;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import fr.isiz.epikube.manager.MobsManager;
import fr.isiz.epikube.mobs.Kamikaze;

/**
 * Main class
 * @author IsiZ_
 */
public class CustomMobPlugin extends JavaPlugin {
	
	public static CustomMobPlugin instance;
	
	@Override
	public void onEnable() {
		instance = this;
		// Load script mobs
		this.loadMobs();
		// Info
		log(ChatColor.RED + "[MobsPlugin] enable !");
	}
	
	/**
	 * Chargement de tous les mobs custom
	 */
	private void loadMobs() {
		try {
			File mobsFile = new File(this.getDataFolder(), "mobs.yml"); // Create a file in the main folder.
			if (!mobsFile.exists()) // If there aren't file
				mobsFile.createNewFile(); // I create it .
			MobsManager.setupConfig(mobsFile); // I setup the configuration
		} catch (IOException e) {
			log("Contact @IsiZ_ error: 34.CustomMobPlugin");
		}
		
		/** Register Mobs **/
		CustomMobAPI.registerMobs(this, new Kamikaze());
	}
	
	/**
	 * Send debug message to console
	 * @param message
	 */
	public static void log(final String message) {
		Bukkit.getConsoleSender().sendMessage(message);
	}

}
