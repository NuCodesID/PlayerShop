package team.nucodes.playershop;

import org.bukkit.plugin.java.JavaPlugin;

import team.nucodes.playershop.cmd.CmdPlayerShop;
import team.nucodes.playershop.event.Events;

public class Main extends JavaPlugin{
	
	public void onEnable() {
		
		this.getServer().getPluginManager().registerEvents(new Events(), this);
		
		this.getCommand("playershop").setExecutor(new CmdPlayerShop());
		loadConfig();
	}

	private void loadConfig() {
		this.getConfig().options().copyDefaults(true);
		saveConfig();
		
	}

}
