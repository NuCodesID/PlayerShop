package team.nucodes.playershop.cmd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;
import team.nucodes.playershop.Constructor;
import team.nucodes.playershop.Main;

public class CmdPlayerShop implements CommandExecutor {

	private static Main plugin = Main.getPlugin(Main.class);	
	public static Inventory inv;
	
	public static void menu(int i, Player p) {
		int num = ((4*9)*i) - (4*9);
		
		Constructor.listedShop();
		inv = Bukkit.createInventory(null, 4*9, "SHOP PAGE " + i);
		inv.setItem(4*9 - 1, Constructor.createItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 5), "&aNext Page"));
		for (int a = 0; a < 8; a++) {
			inv.setItem((4*9 - 2) - a, Constructor.createItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15), " "));
		}
		if (i > 1) {
			inv.setItem(4*9 - 9, Constructor.createItem(new ItemStack(Material.STAINED_CLAY, 1, (short) 11), "&9Previous Page"));
		}
		inv.setItem(4*9 - 4, Constructor.createItem(new ItemStack(Material.PAPER), "&cIn Development Version!"));
		for (int a = 0; a < 4*9 - 1; a++) {
				try {
					ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
					SkullMeta meta = (SkullMeta) item.getItemMeta();
					meta.setDisplayName(" ");
					meta.setOwner(Constructor.listedPlayer.get(num));
					
					List<String> lore = new ArrayList<String>();
					lore.add(ChatColor.translateAlternateColorCodes('&', "&7&m----------------------------"));
					lore.add(ChatColor.translateAlternateColorCodes('&', "&3Owner&7: &a" + Constructor.listedPlayer.get(num)));
					lore.add(ChatColor.translateAlternateColorCodes('&', " "));
					lore.add(ChatColor.translateAlternateColorCodes('&', "&bClick to Teleport"));
					lore.add(ChatColor.translateAlternateColorCodes('&', "&7&m----------------------------"));
					meta.setLore(lore);
					
					item.setItemMeta(meta);
					num++;
					inv.setItem(a, item);
				}catch(Exception e) {
					break;
				}
			
		}
		
		p.openInventory(inv);
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("playershop")) {
				if (args.length == 0) {
					menu(1, p);
					return true;
				}
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("help") || args[0].equals("?")) {
						sender.sendMessage(ChatColor.AQUA + "/playershop set" + ChatColor.DARK_AQUA + " Setup your shop");
						sender.sendMessage(ChatColor.AQUA + "/playershop toggle" + ChatColor.DARK_AQUA + " Toggle shop teleport");
						sender.sendMessage(ChatColor.AQUA + "/playershop tp <player>" + ChatColor.DARK_AQUA + " Teleport to other shop");
						sender.sendMessage(ChatColor.AQUA + "/playershop adv <Text>" + ChatColor.DARK_AQUA + " Broadcast ADV message " + ChatColor.DARK_GRAY + "(" + ChatColor.RED + plugin.getConfig().getInt("advertise-delay") + " seconds" + ChatColor.DARK_GRAY + ")");
						return true;
					}
					if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("create")) {
						try {
							double x = p.getLocation().getX(), y = p.getLocation().getY(), z = p.getLocation().getZ(), yaw = p.getLocation().getYaw(), pitch = p.getLocation().getPitch();
							String world = p.getLocation().getWorld().getName();
							 
							plugin.getConfig().set("shop." + p.getName() + ".enable", "true");
							plugin.getConfig().set("shop." + p.getName() + ".x", x);
							plugin.getConfig().set("shop." + p.getName() + ".y", y);
							plugin.getConfig().set("shop." + p.getName() + ".z", z);
							plugin.getConfig().set("shop." + p.getName() + ".pitch", pitch);
							plugin.getConfig().set("shop." + p.getName() + ".yaw", yaw);
							plugin.getConfig().set("shop." + p.getName() + ".world", world);
							plugin.saveConfig();

							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Constructor.prefix + " &bYour shop has been set on your location!"));
							return true;
						}catch(Exception e) {
							e.printStackTrace();
							sender.sendMessage(Constructor.prefix + ChatColor.RED + " Error occured on the console!");
							return true;
						}
					}
					if (args[0].equalsIgnoreCase("toggle")) {
						try {
							if (plugin.getConfig().getString("shop." + sender.getName() + ".enable").equals("true")) {
								plugin.getConfig().set("shop." + sender.getName() + ".enable", "false");
								sender.sendMessage(Constructor.prefix + ChatColor.AQUA + " Your shop has been toggled to " + ChatColor.RED + "false");
								return true;
							}else {
								plugin.getConfig().set("shop." + sender.getName() + ".enable", "true");
								sender.sendMessage(Constructor.prefix + ChatColor.AQUA + " Your shop has been toggled to " + ChatColor.GREEN + "true");
								return true;
							}
						}catch(Exception e) {
							sender.sendMessage(Constructor.prefix + ChatColor.RED + " Error occured");
							return true;
						}
					}
					
				}
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("tp")) {
						if (args[1].length() > 0) {
							Constructor.tpShop(args[1], sender.getName());
							return true;
						}
					}
				}
				if (args.length == args.length) {
					if (args[0].equalsIgnoreCase("adv") || args[0].equalsIgnoreCase("advertise") || args[0].equalsIgnoreCase("promote")) {
						if (args.length == 1) {
							sender.sendMessage(Constructor.prefix + ChatColor.RED + " Please type the message to promote your shop!");
							return true;
						}
						if (Constructor.advPlayer.contains(Bukkit.getPlayer(sender.getName()))) {
							sender.sendMessage(Constructor.prefix + ChatColor.RED + " Please wait for the delay to promote your shop!");
							return true;
						}
						String result = "";
						Constructor.advPlayer.add(Bukkit.getPlayer(sender.getName()));
						for (int num = 0; num < args.length; num++) {
							int index = num + 1;
						
							if (index == 1) {
								result = ChatColor.translateAlternateColorCodes('&', args[1]);
							}else {
								try {
									result = ChatColor.translateAlternateColorCodes('&', result + " " + args[index]);
								}catch(Exception e) {
									
								}
							}
							
						}
						String finalPrefix = Constructor.advPrefix.replace("{PLAYER}", sender.getName());
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalPrefix + result));
						Bukkit.getScheduler().scheduleSyncDelayedTask(CmdPlayerShop.plugin, new Runnable() {
							public void run() {
								try {
									Constructor.advPlayer.remove(Bukkit.getPlayer(sender.getName()));
									sender.sendMessage(Constructor.prefix + ChatColor.BLUE + " Your promotion delay has been refreshed!");
								}catch(Exception e) {
									e.printStackTrace();
								}
							}
						}, 20L*(plugin.getConfig().getInt("advertise-delay")));
						return true;
						
					}
					return true;
				}
			}
		}
		
		
		return false;
	}

}
