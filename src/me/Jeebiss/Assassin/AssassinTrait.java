package me.Jeebiss.Assassin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;

public class AssassinTrait extends Trait {

	public AssassinTrait() {
		super("Assassin");
		plugin = (Assassin) Bukkit.getServer().getPluginManager().getPlugin("Assassin");
	}

	Assassin plugin = null;
	Player confirmedTarget = null;
	String confirmedTargetSt = null;
	

	public void load(DataKey key) {
		confirmedTargetSt = (key.getString("assassinTarget", null));
	}

	public void save(DataKey key) {
		if (confirmedTargetSt !=null) key.setString("assassinTarget", confirmedTargetSt);
		else if (key.keyExists("assassinTarget")) key.removeKey("assassinTarget");
	}

	@EventHandler
	public void click(NPCClickEvent event){
		if(event.getNPC() == this.getNPC()){
			//stuff
		}
	}


	//Run code when your trait is attached to a NPC. 
      //This is called BEFORE onSpawn so do not try to access npc.getBukkitEntity(). It will be null.
      //This would be a good place to load configurable defaults for new NPCs.
	@Override
	public void onAttach() {
		plugin.getServer().getLogger().info(npc.getName() + "has been assigned Assassin!");
		
	}

      // Run code when the NPC is despawned. This is called before the entity actually despawns so npc.getBukkitEntity() is still valid.
	@Override
	public void onDespawn() {
      }

	//Run code when the NPC is spawned. Note that npc.getBukkitEntity() will be null until this method is called.
      //This is called AFTER onAttach and AFTER Load when the server is started.
	@Override
	public void onSpawn() {

	}

      //run code when the NPC is removed. Use this to tear down any repeating tasks.
	@Override
	public void onRemove() {
      }

	public boolean isKillable(Player targetToCheck){
		if (targetToCheck != null && targetToCheck.isOnline() && targetToCheck.getWorld() == npc.getBukkitEntity().getLocation().getWorld()){
			return true;
		}else return false;
	}
}