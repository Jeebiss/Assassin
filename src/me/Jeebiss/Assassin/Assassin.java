package me.Jeebiss.Assassin;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.TraitInfo;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Assassin extends JavaPlugin {

	public void onEnable() {
		if(getServer().getPluginManager().getPlugin("Citizens") != null) {
			if(getServer().getPluginManager().getPlugin("Citizens").isEnabled()) { 
				CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(AssassinTrait.class).withName("assassin"));
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] inargs) {

		if (inargs.length < 1) {
			sender.sendMessage(ChatColor.RED + "Use /assassin help for command reference.");
			return true;
		}
		
		if (inargs[0].equalsIgnoreCase("reload")) {
			this.reloadMyConfig();
			sender.sendMessage(ChatColor.GREEN + "reloaded!");
			return true;
		}
		
		if (inargs[0].equalsIgnoreCase("help")) {
			sender.sendMessage(ChatColor.WHITE + "Assassin Help!");
			sender.sendMessage(ChatColor.WHITE + "===================");
			sender.sendMessage(ChatColor.RED + "/assassin settarget <name>");
			sender.sendMessage(ChatColor.WHITE + "Set the assassin's current target");
			sender.sendMessage(ChatColor.RED + "/assassin go");
			sender.sendMessage(ChatColor.WHITE + "Assassinate target!!!");
			sender.sendMessage(ChatColor.RED + "/assassin stop");
			sender.sendMessage(ChatColor.WHITE + "Stops assassin from tracking the target.");
			
			return true; 
		}

		int npcid = -1;
		int i = 0;
		try {
			npcid = Integer.parseInt(inargs[0]);
			i = 1;
		}
		catch (Exception e){
		}	
		String[] args = new String[inargs.length-i];
		for (int j = i; j < inargs.length; j++) {
			args[j-i] = inargs[j];
		}

		NPC npc;
		if (npcid == -1){
			npc =	((Citizens)	this.getServer().getPluginManager().getPlugin("Citizens")).getNPCSelector().getSelected(sender);
			if(npc != null ){
				npcid = npc.getId();
			}
			else{
				sender.sendMessage(ChatColor.RED + "You must have a NPC selected to use this command");
				return true;
			}			
		}

		npc = CitizensAPI.getNPCRegistry().getById(npcid); 
		if (npc == null) {
			sender.sendMessage(ChatColor.RED + "NPC with id " + npcid + " not found");
			return true;
		}


		AssassinTrait trait = null;
		if (!npc.hasTrait(AssassinTrait.class)) {
			sender.sendMessage(ChatColor.RED + "That command must be performed on a npc with trait: " + AssassinTrait.class.toString() );
			return true;
		}
		else trait = npc.getTrait(AssassinTrait.class);

		if (args.length < 1){
			sender.sendMessage(ChatColor.RED + "Use /assassin help for command reference.");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("settarget")) {
			if (npc.isSpawned()){
				Player target = this.getServer().getPlayer(args[1]);
				if (target != null && target.isOnline()){
					if (npc.getBukkitEntity().getLocation().getWorld() == target.getLocation().getWorld()){
						trait.confirmedTarget = target;
						String confirmedTargetSt = target.getName();
						sender.sendMessage(ChatColor.RED + "You've targeted " + confirmedTargetSt + ".");
					} else sender.sendMessage(ChatColor.RED + "Target needs to be in the same world.");
				} else sender.sendMessage(ChatColor.RED + "Player is not online.");
			} else sender.sendMessage(ChatColor.RED + "Assassin is not spawned.");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("go")){
			if (npc.isSpawned()){
				if (trait.confirmedTarget != null){
					if (trait.isKillable(trait.confirmedTarget)){
						npc.getNavigator().setTarget(trait.confirmedTarget, true);
					} else sender.sendMessage(ChatColor.RED + "Target is either offline, or in a different world. Can not assassinate.");
				} else sender.sendMessage(ChatColor.RED + "No target selected.");
			}
		}
		
		if (args[0].equalsIgnoreCase("stop")){
			if(npc.getNavigator().isNavigating()){
				npc.getNavigator().cancelNavigation();
				sender.sendMessage(ChatColor.RED + "Assassin stopped.");
			} else sender.sendMessage(ChatColor.RED + "Assassin isnt tracking the target.");
		}
		return false;
	}
	
	public String SomeChatString = "";

	private void reloadMyConfig(){
		this.saveDefaultConfig();
		this.reloadConfig();
		SomeChatString = this.getConfig().getString("SomeChatString");
	}
	
}