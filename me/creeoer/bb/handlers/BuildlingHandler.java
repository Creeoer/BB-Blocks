package me.creeoer.bb.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import me.creeoer.bb.main.BB;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
















import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;


public class BuildlingHandler {
	
	
	WorldEdit instance = WorldEdit.getInstance();

		
	

			@SuppressWarnings("unused")
			public void loadBuildling(String base ,Player player) throws DataException, IOException, MaxChangedBlocksException{
				WorldEdit instance = WorldEdit.getInstance();

			
			    BB main = BB.getInstance();
				 World world = player.getWorld();
	
		         try{
		         File schematicsave = new File(main.getDataFolder() + File.separator + "schematics" + File.separator + base + ".schematic");
		       
		         
		 EditSession s = new EditSession(new BukkitWorld(world), 900000000);
         s.flushQueue();
         CuboidClipboard cs =  MCEditSchematicFormat.MCEDIT.load(schematicsave);
            cs.paste(s, BukkitUtil.toVector(player.getLocation()), true);
       
		         } catch (FileNotFoundException f) {
		        	 f.printStackTrace();
		         }
}

	
}
