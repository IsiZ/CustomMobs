package fr.isiz.epikube;

import org.bukkit.plugin.Plugin;

import fr.isiz.epikube.manager.MobsManager;

public class CustomMobAPI {
	
	/**
	 * Constructeur à ne pas utiliser
	 */
	private CustomMobAPI() {
		throw new IllegalStateException("Use only the static method.");
	}
	
	/**
	 * Register the mobs by Abstract 
	 * @param particles
	 * @return
	 */
	public static boolean registerMobs(MobsManager mobs, Plugin owningPlugin) {
		return MobsManager.registerMobs(owningPlugin, mobs);
	}
	
	/**
	 * Register several particles.
	 */
	public static void registerMobs(Plugin owningPlugin, MobsManager... classes) {
		for (MobsManager clazz : classes) 
			registerMobs(clazz, owningPlugin);
	}

}
