package me.Jeebiss.Assassin;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.TraitInfo;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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

		if (inargs[0] == null){
			return false;
		}
		
		if (inargs[0].equalsIgnoreCase("reload")) {
			this.reloadMyConfig();
			sender.sendMessage(ChatColor.GREEN + "reloaded!");
			return true; //return true if you have handled a command
		}



		//This block of code will allow your users to specify 
		// /myplugin [command] OR /myplugin # [command]
		//The first will run the command on the selected NPC, the second on the NPC with npcID #.
		int npcid = -1;
		int i = 0;
		//did player specify a id?
		try{
			npcid = Integer.parseInt(inargs[0]);
			i = 1;
		}
		catch(Exception e){
		}	
		//reprocess the args to remove the NPC indicator. 
		String[] args = new String[inargs.length-i];
		for (int j = i; j < inargs.length; j++) {
			args[j-i] = inargs[j];
		}


		//Now lets find the NPC this should run on.
		NPC npc;
		if (npcid == -1){
			//sneder didn't specify an id, use his selected NPC.
			npc =	((Citizens)	this.getServer().getPluginManager().getPlugin("Citizens")).getNPCSelector().getSelected(sender);
			if(npc != null ){
				// Gets NPC Selected for this sender
				npcid = npc.getId();
			}
			else{
				//no NPC selected.
				sender.sendMessage(ChatColor.RED + "You must have a NPC selected to use this command");
				return true;
			}			
		}

		npc = CitizensAPI.getNPCRegistry().getById(npcid); 
		if (npc == null) {
			//speicifed number doesn't exist.
			sender.sendMessage(ChatColor.RED + "NPC with id " + npcid + " not found");
			return true;
		}


		//	If you need access to the instance of MyTrait on the npc, get it like this
		AssassinTrait trait = null;
		if (!npc.hasTrait(AssassinTrait.class)) {
			sender.sendMessage(ChatColor.RED + "That command must be performed on a npc with trait: " + AssassinTrait.class.toString() );
			return true;
		}
		else trait = npc.getTrait(AssassinTrait.class);
		//



		if (args[0].equalsIgnoreCase("somecommand")) {
			//Do something to the NPC or trait

			return true;
		}

		return false; // do this if you didn't handle the command.
	}
	
	public String SomeChatString = "";

	private void reloadMyConfig(){
		//This copies the config.yml included in your .jar to the folder for this plugin, only if it does not exist.
		this.saveDefaultConfig();
		//load this config.yml into memory
		this.reloadConfig();
		//get default settings
		SomeChatString = this.getConfig().getString("SomeChatString");
	}
}