package me.Jeebiss.Assassin;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
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
		if(cmd.getName().equalsIgnoreCase("assassin")){
			sender.sendMessage("Assassin v0.1 for Citizens 2!");
			return true;
		}
		
		return false;
	}
}