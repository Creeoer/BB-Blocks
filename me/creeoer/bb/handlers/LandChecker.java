package me.creeoer.bb.handlers;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.creeoer.bb.main.BB;
import me.ryanhamshire.GriefPrevention.Claim;

import org.bukkit.Location;
import org.bukkit.World;

import org.bukkit.entity.Player;

import com.massivecraft.factions.entity.BoardColls;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.data.DataException;

import com.sk89q.worldedit.schematic.MCEditSchematicFormat;

import com.sk89q.worldguard.protection.managers.RegionManager;

public class LandChecker{



 	
 	
 	
 
     public boolean canPlayerPlaceSchematic(String schematicname, Player player) throws DataException, IOException, FileNotFoundException {	
        boolean fac = false;
        boolean world = false;
        boolean grief = false;
    	 BB main = BB.getInstance();
 	     File schematicsave = new File(main.getDataFolder() + File.separator + "schematics" + File.separator + schematicname + ".schematic");
 	     

 	     
         CuboidClipboard cc = MCEditSchematicFormat.MCEDIT.load(schematicsave);
         Location ploc = player.getLocation();
         
         //I'm so sorry about the variable names
         World w = player.getWorld();
         int x = ploc.getBlockX();
         int y = ploc.getBlockY();
         int z = ploc.getBlockZ();
         
         int xxx = cc.getLength();
         int yy = cc.getHeight();
         int zzz = cc.getWidth();
         List<Location> locs = new ArrayList<Location>();         
         
         Location xx = new Location (w,x + xxx, y, z + zzz);
         Location ookla = new Location (w, x + xxx, y + yy, z + zzz);
         Location sas = new Location(w, x  + xxx, y- yy, z + zzz);
         
         Location zz = new Location(w,x- xxx, y, z-zzz);
         Location bookla = new Location(w, x-xxx, y +yy, z -zzz);
         Location stun = new Location (w, x- xxx, y - yy, z - zzz);
         
         Location bb = new Location(w,x - xxx, y, z + zzz);
         Location sass = new Location(w, x - xxx, y - yy, z +zzz);
         Location ass = new Location(w, x - xxx, y + yy, z + zzz);
         
         
         Location ss = new Location (w,x + xxx, y, z - zzz);
         Location WOAH = new Location (w, x + xxx, y + yy, z - zzz);
         Location mooning = new Location (w, x + xxx, y- yy , z - zzz);
         
 
         
         

         //TODO If problems arise add schematic length and apply it to z location and create 2 new scenarios
         
         

         if (main.getFactions() == null && main.getWorldGuard() == null && main.getGriefPrevention() == null) {
       	  return true;
         }
		
         if(main.getFactions() != null) {
        	if (isPlayerFac(ploc, player) == false ||
        	     isPlayerFac(xx, player) == false ||
        	     isPlayerFac(ookla, player) == false ||
        	     isPlayerFac(sas, player) == false ||
        	     isPlayerFac(zz, player) == false ||
        	     isPlayerFac(bookla, player) == false ||
        	     isPlayerFac(stun, player) == false ||
        	     isPlayerFac(bb, player) == false ||
        	     isPlayerFac(sass, player) == false ||
        	     isPlayerFac(ass, player) == false ||
        	     isPlayerFac(ss, player) == false ||
        	     isPlayerFac(WOAH, player) == false ||
        	     isPlayerFac(mooning,player) ==false) {
        		 fac = false;
        		 
        	
        	} else {
        	fac = true;
        	}
        	 
         } else {
        	 fac = true;
         }
    
         
         
         
         //Good
         if(main.getGriefPrevention() != null) {
        	   if (isPlayerClaim(ploc, player) == false ||
            	     isPlayerClaim(xx, player) == false ||
            	     isPlayerClaim(ookla, player) == false ||
            	     isPlayerClaim(sas, player) == false ||
            	     isPlayerClaim(zz, player) == false ||
            	     isPlayerClaim(bookla, player) == false ||
            	     isPlayerClaim(stun, player) == false ||
            	     isPlayerClaim(bb, player) == false ||
            	     isPlayerClaim(sass, player) == false ||
            	     isPlayerClaim(ass, player) == false ||
            	     isPlayerClaim(ss, player) == false ||
            	     isPlayerClaim(WOAH, player) == false ||
            	     isPlayerClaim(mooning,player) == false) {
        		   
        		   
        		 grief = false;
        	   } else {
        		   grief = true;
        	   }
        	     
        	 } else {
        		 grief = true;
        	 }
        	 
         
        	 
        	 
        	 
        	 
        	 
        	 
        	 
         
         
         
         
         
         //Good
         if (main.getWorldGuard() != null) {
        	 if (isPlayerRegion(ploc, player) == false || 
            	     isPlayerRegion(xx, player) == false ||
            	     isPlayerRegion(ookla, player) == false||
            	     isPlayerRegion(sas, player) == false ||
            	     isPlayerRegion(zz, player) == false ||
            	     isPlayerRegion(bookla, player) == false ||
            	     isPlayerRegion(stun, player) == false ||
            	     isPlayerRegion(bb, player) == false ||
            	     isPlayerRegion(sass, player) == false ||
            	     isPlayerRegion(ass, player) == false ||
            	     isPlayerRegion(ss, player) == false ||
            	     isPlayerRegion(WOAH, player) == false ||
            	     isPlayerRegion(mooning,player) == false) {
        		
        		 world = false;
        	 } else {
        		 world = true;
        	 }
        	    
        
   
        	 } else {
        		 world = true;
        	 }
	
         
           if (world == true && fac == true && grief == true) {
        	   return true;
           } else {
        	   
           
           return false;
           }
           
       
       	 
       	 

    	

     }


 
 
 
 public boolean doesFileExist(File file){
	 File folder = new File(BB.getInstance().getDataFolder() + File.separator + "schematics");
	List<File> files = Arrays.asList(folder);
	  if(files.contains(file)){
		  
		  return true;
	  }
	  return false;
 }
 
 
 public boolean isPlayerFac(Location location, Player player) {
	 BB main = BB.getInstance();
	 Faction faction = BoardColls.get().getFactionAt(com.massivecraft.massivecore.ps.PS.valueOf(location));
	 UPlayer uplayer = UPlayer.get(player);
	 
	 
	 if (main.getFactions() == null) {
		 
		 return true;
	 }
	 
	 if (faction == null) {
		 return true;
	 }
	 
	 if (faction != null) {
		 List<UPlayer> fplayers = faction.getUPlayers();
	 if (fplayers.contains(uplayer)) {
		 return true;
	     }
		return false;
	 }
		return false;
	
 }
 
 public boolean isPlayerClaim(Location location, Player player) {
	 BB main = BB.getInstance();
 	 Claim claim = main.getGriefPrevention().dataStore.getClaimAt(location, true, null);
     
 	 if (main.getGriefPrevention() == null) {
 		 return true;
 	 }
 	 
 	 if (claim == null) {
 		 return true;
 	 }
 	 
	 if (claim != null) {
	 String owner = claim.getOwnerName();
		  if (owner == player.getName()) {
			  return true;
		  }
		  return false;
	 }
	 return false;
		 

		
 }
 
 
 public boolean isPlayerRegion(Location sas, Player player) {
	 BB main = BB.getInstance();
	 com.sk89q.worldguard.LocalPlayer p = main.getWorldGuard().wrapPlayer(player);
	 RegionManager regionManager = main.getWorldGuard().getRegionManager(player.getWorld());
	
     if (main.getWorldGuard() == null) {
    	 return true;
     }
	 
	 
     if (regionManager == null) {
   
    	 return true;
     }
     
     if (regionManager.getApplicableRegions(sas) == null){
    	 return true;
     }
     
     if (regionManager.getApplicableRegions(sas).canBuild(p) == true) {
         return true;
     } else {
        return false;
     }
    
 }
}
