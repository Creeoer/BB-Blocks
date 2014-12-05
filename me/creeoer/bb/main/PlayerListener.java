package me.creeoer.bb.main;


import me.creeoer.bb.handlers.BuildlingHandler;
import me.creeoer.bb.handlers.LandChecker;
import net.milkbowl.vault.economy.EconomyResponse;

import java.io.FileNotFoundException;
import java.io.IOException;


















import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;



























import org.bukkit.metadata.MetadataValue;

import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.data.DataException;

public class PlayerListener implements Listener {
  
  
  BuildlingHandler bh = new BuildlingHandler();
  LandChecker land = new LandChecker();
  
  BB main = BB.getInstance();
  
 
  

	double amount;
	@EventHandler
	//If line 0 equals BB, it'll create the sign  as long as the player has the permission, also parses an interger from the first line of the sign
	public void onSignChange(SignChangeEvent e) {
		Player player = e.getPlayer();
		if(e.getLine(0).equalsIgnoreCase("[BB]") && player.hasPermission("bb.sign.create")) {
			e.setLine(0, ChatColor.YELLOW + "[" + ChatColor.DARK_AQUA + "BB" + ChatColor.YELLOW + "]");
		    amount = Integer.parseInt(e.getLine(1));
		    
		     
		    	
		    }
		    
		   
		}
		
	
	
		@SuppressWarnings("deprecation")
		@EventHandler
		public void SignClick(PlayerInteractEvent e){
	   Player player = e.getPlayer();
	   if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
		   if(e.getClickedBlock().getState() instanceof Sign){
				 Sign sign = (Sign) e.getClickedBlock().getState();
			   if(sign.getLine(0).equalsIgnoreCase(ChatColor.YELLOW + "[" + ChatColor.DARK_AQUA + "BB" + ChatColor.YELLOW + "]")){
				   if(player.hasPermission("bb.sign.use")) {
	             			  
					   //Withdraws money based on amount, which is determined on the second line of the sign
				   EconomyResponse r = BB.econ.withdrawPlayer(player.getName(), amount);
		            if(r.transactionSuccess()) {
		                player.sendMessage(String.format(ChatColor.GREEN + "%s was taken and you now have %s!", BB.econ.format(r.amount), BB.econ.format(r.balance)));
		                
		                //Gives a player a block and names it based on what the sign says
		                ItemStack Build = new ItemStack(Material.BEDROCK);
		          	    ItemMeta im = Build.getItemMeta();
		          	    String bn = sign.getLine(2);
		          	    
		          	    im.setDisplayName(bn + ChatColor.GREEN+ " BB Block");
		                Build.setItemMeta(im);
		                player.getInventory().addItem(Build);
		       	        player.updateInventory();
		       
		       	        
		       //TODO Fix spacing         
		 }  else {
			 player.sendMessage(ChatColor.RED + "You don't have the required funds to purchase this");
		 }
			 
		            
		    } else {
		    	player.sendMessage(ChatColor.RED + "You don't have permission to buy this!");
		    }
		  }//sign contains
		 }//if sign
        
   
        }//if rightclick
	   }//SIgnClick thing
			 
	   
	   
	   
	   
	   

			      @EventHandler
			      //Used for listening to a BLockPlaceEvent to check if the block is a bb block, determines schematic name, does check and loads base on that check boolean
			      public void buildlingPlace(BlockPlaceEvent e) throws MaxChangedBlocksException, DataException, IOException, FileNotFoundException{
				 Player player = e.getPlayer();
				 ItemStack sas = e.getItemInHand();
	             ItemMeta im = sas.getItemMeta();
	             
	               if(im.getDisplayName() != null) {
	            	   //Just debugging
	            
	               if(im.getDisplayName().contains("BB Block")){
	            	   //This prints

					   String building = im.getDisplayName().replace("BB Block", "").trim();
					   String modified = ChatColor.stripColor(building);

		           
                      
                        
		         
		                
		                if (land.canPlayerPlaceSchematic(modified, player) == true) {
		                	  bh.loadBuildling(modified, player);
							  player.sendMessage(ChatColor.YELLOW + "[" + ChatColor.AQUA + "BB" + ChatColor.YELLOW + "]" + ChatColor.GREEN + " Successfully placed buildling titled" + building);
							 
							 player.getInventory().remove(e.getItemInHand());
							 player.updateInventory(); 
							  e.setCancelled(true);
		                
							  
		                } else {
		                	 player.sendMessage(ChatColor.YELLOW + "[" + ChatColor.AQUA + "BB" + ChatColor.YELLOW + "]" + ChatColor.RED + " You can't place this, you don't have access to this land!");
							   e.setCancelled(true);
		                		
		                	
		           
		                }
	               }
				 
				 
				 
				 
	               }
				 
			      }

}
		 


			
     

   
 
