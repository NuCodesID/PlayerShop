package team.nucodes.playershop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class Constructor {

	private static Main plugin = Main.getPlugin(Main.class);
	public final static String advPrefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix-advertise"));
	public final static String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"));
	public static List<String> listedPlayer = new ArrayList<String>();
	public static List<Player> advPlayer = new ArrayList<Player>();
	
	public static ItemStack createItem(ItemStack item, String name) {
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static String getShopOwner(int i) {
		return listedPlayer.get(i);
	}
	
	public static void tpShop(String target, String teleporter) {
		
		World world = Bukkit.getWorld(plugin.getConfig().getString("shop." + target + ".world"));
		double x = plugin.getConfig().getDouble("shop." + target + ".x");
		double y = plugin.getConfig().getDouble("shop." + target + ".y");
		double z = plugin.getConfig().getDouble("shop." + target + ".z");
		float yaw = Float.parseFloat(plugin.getConfig().getString("shop." + target + ".yaw"));
		float pitch = Float.parseFloat(plugin.getConfig().getString("shop." + target + ".pitch"));
		
		Location loc = new Location(world, x, y, z, yaw, pitch);
		Bukkit.getPlayer(teleporter).teleport(loc);
		
		Bukkit.getPlayer(teleporter).sendMessage(prefix + ChatColor.AQUA + " Teleported to " + net.md_5.bungee.api.ChatColor.GREEN + target + net.md_5.bungee.api.ChatColor.AQUA + " shop");
	}
	
	public static void listedShop() {
		
		listedPlayer.clear();
		
		for (Player o : Bukkit.getOnlinePlayers()) {
			try {
				
				if (plugin.getConfig().getString("shop." + o.getName() + ".enable").equals("true") && !listedPlayer.contains(o.getName())) {
					listedPlayer.add(o.getName());
				}
			}catch(Exception e) {
				
			}
		}
		for (OfflinePlayer o : Bukkit.getOfflinePlayers()) {
			String name = o.getName();
			try {
				
				if (plugin.getConfig().getString("shop." + name + ".enable").equals("true") && !listedPlayer.contains(o.getName())) {
					listedPlayer.add(o.getName());
				}
			}catch(Exception e) {}
			
		}
		
	}
	
}
