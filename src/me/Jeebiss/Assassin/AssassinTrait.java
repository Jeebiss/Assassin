package me.Jeebiss.Assassin;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;

//This is your trait that will be applied to a npc using the /trait mytraitname command. Each NPC gets its own instance of this class.
//the Trait class has a reference to the attached NPC class through 'npc' or getNPC().
//The Trait class also implements Listener so you can add EventHandlers directly to your trait.
public class AssassinTrait extends Trait {

	public AssassinTrait() {
		super("AssassinTrait");
		plugin = (Assassin) Bukkit.getServer().getPluginManager().getPlugin("Assassin");
	}

	Assassin plugin = null;

	boolean SomeSetting = false;

	//Here you should load up any values you have previously saved. 
      //This does NOT get called when applying the trait for the first time, only loading onto an existing npc at server start.
      //This is called AFTER onAttach so you can load defaults in onAttach and they will be overridden here.
      //This is called BEFORE onSpawn so do not try to access npc.getBukkitEntity(). It will be null.
	public void load(DataKey key) {
		SomeSetting = key.getBoolean("SomeSetting", false);
	}

	//Save settings for this NPC. These values will be added to the citizens saves.yml under this NPC.
	public void save(DataKey key) {
		key.setBoolean("SomeSetting",SomeSetting);
	}

	@EventHandler
	public void click(net.citizensnpcs.api.event.NPCClickEvent event){
		//Handle a click on a NPC. The event has a getNPC() method. 
		//Be sure to check event.getNPC() == this.getNPC() so you only handle clicks on this NPC!

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

}