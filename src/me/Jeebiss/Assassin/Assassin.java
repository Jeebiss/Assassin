package me.Jeebiss.Assassin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

//This is your bukkit plugin class. Use it to hook your trait into Citizens and handle any commands.
public class Assassin extends org.bukkit.plugin.java.JavaPlugin {

	public void onEnable() {
		//check if Citizens is present and enabled.
		if(getServer().getPluginManager().getPlugin("Citizens") == null || getServer().getPluginManager().getPlugin("Citizens").isEnabled() == false) {
			//Register your trait with Citizens.        
			net.citizensnpcs.api.trait.TraitInfo info = net.citizensnpcs.api.trait.TraitInfo.create(AssassinTrait.class).withName("Assassin");
			net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(info);	
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] inargs) {
		return false;
	}
}