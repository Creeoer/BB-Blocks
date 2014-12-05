package me.creeoer.bb.handlers;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.command.CommandSender;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;

import me.creeoer.bb.main.BB;

public class SchematicHandler {
	BB main = BB.getInstance();

   
 	public void addSchematic(String base, CommandSender sender) throws IOException, DataException, EmptyClipboardException{
 		//Takes name and creates new schematic file using it
 		File schematicsave = new File(main.getDataFolder() + File.separator + "schematics" + File.separator + base + ".schematic");
 		schematicsave.createNewFile();
 		WorldEdit instance = WorldEdit.getInstance();
 		LocalSession session = instance.getSession(sender.getName());
 		CuboidClipboard clipboard = session.getClipboard();
 	
 		//This saves the schematic in that file
 		MCEditSchematicFormat.MCEDIT.save(clipboard, schematicsave);
 
 	    MCEditSchematicFormat.MCEDIT.load(schematicsave);
 		
 		
 	
        	   
 	} 
 	public boolean clipboardcheck(CuboidClipboard board) {
 		if (board != null ) {
 			return true;
 		}
 		return false;
 	}
 	
 }