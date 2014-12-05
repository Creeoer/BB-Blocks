package me.creeoer.bb.main;

import java.io.File;





import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.Factions;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;



public class BB extends JavaPlugin {
     public static BB instance;
    
     public static Economy econ = null;
     PlayerListener x = new PlayerListener();	
     PluginManager pm = Bukkit.getPluginManager();
 
     
     
     public void onEnable(){
    	  instance = this;
    
	    
    	  if(!getDataFolder().exists()) {
    		  getDataFolder().mkdir();
    		  new File(getDataFolder() + File.separator + "schematics").mkdirs();
    		// new File(getDataFolder()+ File.separaotr + "schematics" );
    				
    	  } 
    	  
    	  pm.registerEvents(x, this);
    	    Commands co = new Commands();
    	  getCommand("bb").setExecutor(co);
    	  setupEconomy();
     }
    
    
     
     public void onDisable(){
    	 getLogger().info("Disabling FactionBases!");
    	 instance = null;
     }
     
     
 	public static BB getInstance(){
		return instance;
 	}
 public WorldGuardPlugin getWorldGuard() {
    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
    	return null;
    }
    return (WorldGuardPlugin) plugin;
 }


public Factions getFactions() {
	
	Plugin plugin = getServer().getPluginManager().getPlugin("Factions");
	
	if(plugin == null || !(plugin instanceof Factions)) {
		return null;
	}
	return (Factions) plugin;
}

public GriefPrevention getGriefPrevention() {
	Plugin plugin = getServer().getPluginManager().getPlugin("GriefPrevention");
	
	if (plugin == null || !(plugin instanceof GriefPrevention)) {
		return null;
	}
	return (GriefPrevention) plugin;
}

 
 			
 			
 	
 	
 	
 	//Sets up vault economy
	    private boolean setupEconomy() {
	        if (getServer().getPluginManager().getPlugin("Vault") == null) {
	            return false;
	        }
	        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
	        if (rsp == null) {
	        	getLogger().info("Vault can't register an economy! Shutting down plugin...");
	        	Bukkit.getPluginManager().disablePlugin(this);
	        
	            return false;
	        }
	        econ = rsp.getProvider();
	        return econ != null;
	    

     
	  

}

		
}