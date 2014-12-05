package me.creeoer.bb.main;
import java.io.IOException;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.creeoer.bb.handlers.SchematicHandler;







import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.data.DataException;


public class Commands implements CommandExecutor {
    SchematicHandler ch = new SchematicHandler();
    WorldEdit we = WorldEdit.getInstance();
 
    
  
	@Override
	public boolean onCommand(final CommandSender sender, Command cmd,String label, String[] args) {
	
 	
	   List<String> cmdlist = Arrays.asList("create");
		  if (cmd.getName().equalsIgnoreCase("bb")) {
			  if (!(sender instanceof Player)) {
				  sender.sendMessage("Sorry, only players can use in-game commands");
				
				  return true;
			  }
			  
			  if(args.length == 0 || !cmdlist.contains(args[0])) {
				 sender.sendMessage(ChatColor.RED + "Syntax error, please input a correct command.");
				 return true;
			  }
			
			    if (args[0].equalsIgnoreCase("create")) {
			    	 if(!sender.hasPermission("bb.create")) {
			    		 sender.sendMessage(ChatColor.RED + "You have no permission to create a schematic");
			    		return true;
			    	 } 
			      
			           if(args.length == 1) {
			        	  sender.sendMessage(ChatColor.RED +"The schematic name can't be blank");
			        	  return true;
			         } 
			   		LocalSession session = we.getSession(sender.getName());
			   		  if (session == null) {
			   			  sender.sendMessage(ChatColor.RED + "No WorldEdit session found, please make sure you have an item in your clipboard");
			   			  return true;
			   		  }
			  	      CuboidClipboard clipboard = null;
					try {
						clipboard = session.getClipboard();
					} catch (EmptyClipboardException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			   		  
			           if (clipboard == null) {
                           sender.sendMessage(ChatColor.RED + "Your clipboard is empty, saving of schematic has been cancelled.");
			        	   
			           }
			           //Boy these variable names, started running out of them.
			           String ss = args[1];
			           try {
						ch.addSchematic(ss, sender);
					} catch (EmptyClipboardException e) {
						
						e.printStackTrace();
					} catch (IOException e) {
				
						e.printStackTrace();
					} catch (DataException e) {

						e.printStackTrace();
					}
			           sender.sendMessage(ChatColor.GREEN + "Successfully created schematic tilted" + ss); 
			             
			    } else  {
			    	sender.sendMessage(ChatColor.RED + "Clipboard is empty, can not save");
			    	 return true;
			    }
			  
			  
		  }
		
	
	
		return false;
	}

}