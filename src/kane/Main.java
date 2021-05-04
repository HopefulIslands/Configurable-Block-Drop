package kane;

import kane.events.OnBlockBrokenClass;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main plugin;

    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[RANDOM UHC] &aPlugin by hopeful has been loaded"));
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents((Listener)new OnBlockBrokenClass(), (Plugin)this);
        plugin = this;
    }

    public void onDisable() {}

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("blockdrop")) {
            if (!sender.hasPermission("randomblock.reload")) {
                sender.sendMessage("Unknown command. Type \"/help\" for help.");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Correct Usage: /blockdrop reload");
                return true;
            }
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    for (String i : getConfig().getStringList("reload.message"))
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i));
                    return true;
                }
                if (args[0].equalsIgnoreCase("rl")) {
                    reloadConfig();
                    for (String i : getConfig().getStringList("reload.message"))
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i));
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Invalid Command usage for this plugin");
            }
        }
        return false;
    }
}
