package com.comze_instancelabs.executesh;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

/**
 * 
 * @author instancelabs
 *
 */

public final class Main extends JavaPlugin{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    	if(cmd.getName().equalsIgnoreCase("esh")){
    		if(args.length < 1){
    			sender.sendMessage("§3/esh [sh file]");
    			sender.sendMessage("§3/ls");
    			sender.sendMessage("§3/ps");
    			sender.sendMessage("§3/start [process]");
    			sender.sendMessage("§3/kill [processid]");
    		}else{
				File f = new File(args[0]);
				f.setExecutable(true, true);
				
    			sender.sendMessage("§4FILE: " + args[0]);
				Process p = null;
				/*ProcessBuilder pb = new ProcessBuilder(args[0]);
				try {
					p = pb.start();
				} catch (IOException e) {
					sender.sendMessage("E0" + e.getMessage());
				}*/
				
				try {
					p = Runtime.getRuntime().exec(new String[]{"/bin/sh" ,"-c", args[0]});
				} catch (IOException e1) {
					sender.sendMessage("E1" + e1.getMessage());
				}
				
				/*try{
					Runtime.getRuntime().exec(args[0]);
				}catch(IOException e2){
					sender.sendMessage("E2" + e2.getMessage());
				}*/
				
				if(p != null){
					sender.sendMessage("§2Successfully started.");
					BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line = null;
					try {
						while ((line = reader.readLine()) != null) {
							sender.sendMessage(line);
						}
					} catch (IOException e) {
						sender.sendMessage(e.getMessage());
					}	
				}else{
					sender.sendMessage("§4Process was not started.");
				}
    		}
    		return true;
    	}else if(cmd.getName().equalsIgnoreCase("ls")){
    		sender.sendMessage("§3" + System.getProperty("user.dir"));
    		String path = "."; // "."

			String files;
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					files = listOfFiles[i].getName();
					sender.sendMessage("§2" + files);
				}
			}
			return true;
    	}else if(cmd.getName().equalsIgnoreCase("ps")){
    		Process p = null;
    		try {
				p = Runtime.getRuntime().exec("ps -A");
			} catch (IOException e) {
				sender.sendMessage("§4Error creating process: " + e.getMessage());
			}
    		
    		if(p != null){
    			sender.sendMessage("§2Successfully started.");
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sender.sendMessage(line);
					}
				} catch (IOException e) {
					sender.sendMessage(e.getMessage());
				}	
			}
    		return true;
    	}else if(cmd.getName().equalsIgnoreCase("pstree")){
    		Process p = null;
    		try {
				p = Runtime.getRuntime().exec("pstree");
			} catch (IOException e) {
				sender.sendMessage("§4Error creating process: " + e.getMessage());
			}
    		
    		if(p != null){
    			sender.sendMessage("§2Successfully started.");
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sender.sendMessage(line);
					}
				} catch (IOException e) {
					sender.sendMessage(e.getMessage());
				}	
			}
    		return true;
    	}else if(cmd.getName().equalsIgnoreCase("start")){
    		if(args.length > 0){
	    		Process p = null;
	    		try {
					p = Runtime.getRuntime().exec(args[0]);
				} catch (IOException e) {
					sender.sendMessage("§4Error creating process: " + e.getMessage());
				}
	    		
	    		if(p != null){
	    			sender.sendMessage("§2Successfully started.");
					BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line = null;
					try {
						while ((line = reader.readLine()) != null) {
							sender.sendMessage(line);
						}
					} catch (IOException e) {
						sender.sendMessage(e.getMessage());
					}	
				}
	    		return true;	
    		}else{
    			sender.sendMessage("§4Usage: /start [process]");
    			return true;
    		}
    	}else if(cmd.getName().equalsIgnoreCase("kill")){
    		if(args.length > 0){
	    		Process p = null;
	    		try {
					p = Runtime.getRuntime().exec("kill -9 " + args[0]);
				} catch (IOException e) {
					sender.sendMessage("§4Error creating process: " + e.getMessage());
				}
	    		
	    		if(p != null){
	    			sender.sendMessage("§2Successfully started.");
					BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line = null;
					try {
						while ((line = reader.readLine()) != null) {
							sender.sendMessage(line);
						}
					} catch (IOException e) {
						sender.sendMessage(e.getMessage());
					}	
				}
	    		return true;	
    		}else{
    			sender.sendMessage("§4Usage: /kill [process]");
    			return true;
    		}
    	}
    	return false;
    }
    
    
    

}
