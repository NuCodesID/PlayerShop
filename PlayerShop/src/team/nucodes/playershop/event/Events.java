package team.nucodes.playershop.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import team.nucodes.playershop.Constructor;
import team.nucodes.playershop.Main;
import team.nucodes.playershop.cmd.CmdPlayerShop;

public class Events implements Listener {

	private Main plugin = Main.getPlugin(Main.class);
	
	@EventHandler
	public void shopInvClick(InventoryClickEvent event) {

		if (event.getClickedInventory().getName().contains("SHOP PAGE ")) {
			event.setCancelled(true);
			
			String[] split = event.getClickedInventory().getName().split("SHOP PAGE ");
			

			
			Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
				public void run() {
					try {
						int page = Integer.parseInt(split[1]);
						if (event.getSlot() >= 0 && event.getSlot() < 4*9 - 9) {
							int index = event.getSlot()*page;
							
							if (page > 1) {
								index = index + 26;
							}
							event.getWhoClicked().closeInventory();
							Constructor.tpShop(Constructor.getShopOwner(index), event.getWhoClicked().getName()); 
							return;
						}
						if (event.getSlot() == 4*9 - 1) {
							page++;			
							event.getWhoClicked().closeInventory();
							CmdPlayerShop.menu(page, (Player) event.getWhoClicked()); 
							return;
						}
						if (page > 1) {
							if (event.getSlot() == 4*9 - 9) {
								page--;
								event.getWhoClicked().closeInventory();
								CmdPlayerShop.menu(page, (Player) event.getWhoClicked()); 
								return;
							}
						}
					}catch(Exception e) {
						//e.printStackTrace();
					}
				}
			}, 1L);
			
			
		}
		
		return;
	}
	
}
