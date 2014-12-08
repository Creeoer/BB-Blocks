package me.creeoer.bb.main;


import me.creeoer.bb.handlers.BuildlingHandler;
import me.creeoer.bb.handlers.LandChecker;
import net.milkbowl.vault.economy.EconomyResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
































import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;



























import org.bukkit.metadata.MetadataValue;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.data.DataException;

public class PlayerListener implements Listener {
  
  
  BuildlingHandler bh = new BuildlingHandler();
  LandChecker land = new LandChecker();
  
 
  
 
  

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
			FileConfiguration config = YamlConfiguration.loadConfiguration(new File (BB.getInstance().getDataFolder() + File.separator + "config.yml"));
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
		                Material mat = Material.getMaterial(config.getString("Options.bb_material"));
		                //Gives a player a block and names it based on what the sign says
		                //This itemstack will be dependent on what the player asks for
		                ItemStack Build = new ItemStack(mat);
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
			 
	   

			      @EventHandler (priority = EventPriority.LOW)
			      //Used for listening to a BLockPlaceEvent to check if the block is a bb block, determines schematic name, does check and loads base on that check boolean
			      public void buildlingPlace(BlockPlaceEvent e) throws MaxChangedBlocksException, DataException, IOException, FileNotFoundException, NotRegisteredException{
				 Player player = e.getPlayer();
				 ItemStack sas = e.getItemInHand();
				 int amount = sas.getAmount();
	             ItemMeta im = sas.getItemMeta();
	             
	               if(im.getDisplayName() != null) {
	            
	               if(im.getDisplayName().contains("BB Block")){

					   String building = im.getDisplayName().replace("BB Block", "").trim();
					   String modified = ChatColor.stripColor(building);

		                if (land.canPlayerPlaceSchematic(modified, player) == true) {
		                	  bh.loadBuildling(modified, player);
							  player.sendMessage(ChatColor.YELLOW + "[" + ChatColor.AQUA + "BB" + ChatColor.YELLOW + "]" + ChatColor.GREEN + " Successfully placed buildling titled " + building);
							  if(amount > 1) {
								  ItemStack sas1 = new ItemStack(e.getItemInHand().getType(), amount - 1);
								  ItemMeta sas2 = sas1.getItemMeta();
								  sas2.setDisplayName(building + ChatColor.GREEN + "BB Block");
								  sas1.setItemMeta(sas2);
							      player.setItemInHand(sas1);
							  } else {
								  player.setItemInHand(null);
							  }
							  player.updateInventory(); 
							  e.setCancelled(true);
		                
							  
		                } else {
		                	   player.sendMessage(ChatColor.YELLOW + "[" + ChatColor.AQUA + "BB" + ChatColor.YELLOW + "]" + ChatColor.RED + " You can't place this, you don't have access to this land!");
							   e.setCancelled(true);
		              }
	                }
				 
	             }
				 
			  }   
			    @EventHandler
			    public void anvilClick(InventoryClickEvent e) {
                       if(e.getWhoClicked() instanceof Player){
                    	   if (e.getInventory() instanceof AnvilInventory) {
                    		   //Gets view of inventory
                    		   InventoryView view = e.getView();
        
                    		   //Checks to make that the items at either slot 0, 1, or 2 aren't null
                    		   if(view.getItem(0) != null|| view.getItem(1) != null || view.getItem(2) != null) {
                    			   ItemStack sas = view.getItem(0);
                    			   ItemStack sassy = view.getItem(1);
                    			   ItemStack jelly = view.getItem(2);
                    			   //Makes sure the item nor itemmeta is null
                    			    if (sas != null) {
                    			    	if(sas.getItemMeta() != null) {
                    			    		//If the displayname would have bb block, cancel the event
                    			       if (sas.getItemMeta().getDisplayName().contains("BB Block")){
                    			    	   Bukkit.broadcastMessage("here");
                             			   Player player = (Player) e.getWhoClicked();
                             			   player.sendMessage(ChatColor.RED + "You can't use bb blocks in anvils");
           			    				  e.setCancelled(true);
                    			       }
                    			       }	
                    			    }
                    			    
                    			    if(sassy !=null) {
                    			    	if (sassy.getItemMeta() != null){
                                       if (sassy.getItemMeta().getDisplayName().contains("BB Block")){
                                    	  Bukkit.broadcastMessage("here");
                             			  Player player = (Player) e.getWhoClicked();
                             			  player.sendMessage(ChatColor.RED + "You can't use bb blocks in anvils");
           			    				  e.setCancelled(true);
                                       }
                                       }                  			    	
                    			    }
                    			    
                    			    if(jelly !=null) {
                    			    	if(jelly.getItemMeta() !=null) {
                    			      if(jelly.getItemMeta().getDisplayName().contains("BB Block")){
                    			    	  Bukkit.broadcastMessage("here");
                            			  Player player = (Player) e.getWhoClicked();
                            			  player.sendMessage(ChatColor.RED + "You can't use bb blocks in anvils");
          			    				  e.setCancelled(true);
                    			      }
                    			      }
                    			    }
                    		   }
                
			    			 }
			    			 
			    			 
			    		 }

			    }

			    		 
			    @EventHandler
			    public void anvilRenaming(InventoryClickEvent e){
			    	if(e.getWhoClicked() instanceof Player) {
			    		if(e.getInventory() instanceof AnvilInventory) {
			    			InventoryView view = e.getView();
			    			//If the resulting item contains bb block, cancel the event 
			    			if(view.getItem(2) != null && view.getItem(2).getItemMeta() != null) {
			    				String displayname = view.getItem(2).getItemMeta().getDisplayName();
			    				if (displayname.contains("BB Block")) {
			    					e.setCancelled(true);
			    				}
			    			}
			    			
			    		}
			    		
			    	}
			    	
			    	
			    	
			    }
			    	
			    
	
		   
		   
		   
	   }
			    
			    
			      
			      
			      


		 


			
     

   
 
