package kane.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import kane.Main;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class OnBlockBrokenClass implements Listener {

     Boolean ignoreWorldIf = false;


    @EventHandler
    public void OnBlockBreak(BlockBreakEvent event) {

        Main.plugin.getConfig().getConfigurationSection("blocks").getKeys(false).forEach(key -> {
            Player player = event.getPlayer();
            String WorldName = player.getWorld().getName();
            List<String> WorldsToWorkIN = new ArrayList<>();
            for (String i : Main.plugin.getConfig().getStringList("worlds")) {
                WorldsToWorkIN.add(i);

                if (i.equalsIgnoreCase("ignoreSelector.ALL")) {

                    ignoreWorldIf = true;
                    
                }
            }

            if(ignoreWorldIf) {

                if (key.equalsIgnoreCase(event.getBlock().getType().toString())) {
                    ItemStack[] items = new ItemStack[Main.plugin.getConfig().getStringList("blocks." + key).size()];
                    ItemStack item = null;
                    Random r = new Random();
                    int postion = 0;
                    for (String i : Main.plugin.getConfig().getStringList("blocks." + key)) {
                        try {
                            item = new ItemStack(Material.matchMaterial(i), (int)IsMaxOrSet());
                        } catch (Exception e) {
                            item = new ItemStack(Material.matchMaterial(key));
                        }
                        items[postion] = item;
                        postion++;
                    }
                    int num = r.nextInt(items.length);
                    event.getBlock().setType(Material.AIR);
                    World world = event.getPlayer().getWorld();
                    world.dropItemNaturally(event.getBlock().getLocation(), items[num]);
                }

            }
            else {
            if (WorldsToWorkIN.contains(WorldName)) {
                if (key.equalsIgnoreCase(event.getBlock().getType().toString())) {
                    ItemStack[] items = new ItemStack[Main.plugin.getConfig().getStringList("blocks." + key).size()];
                    ItemStack item = null;
                    Random r = new Random();
                    int postion = 0;
                    for (String i : Main.plugin.getConfig().getStringList("blocks." + key)) {
                        try {
                            item = new ItemStack(Material.matchMaterial(i), (int) IsMaxOrSet());
                        } catch (Exception e) {
                            item = new ItemStack(Material.matchMaterial(key));
                        }
                        items[postion] = item;
                        postion++;
                    }
                    int num = r.nextInt(items.length);
                    event.getBlock().setType(Material.AIR);
                    World world = event.getPlayer().getWorld();
                    world.dropItemNaturally(event.getBlock().getLocation(), items[num]);
                }
                }
        };
        });
    }

    public double IsMaxOrSet() {
        String Check = Main.plugin.getConfig().getString("drop-amount");
        if (Check.equalsIgnoreCase("false")) {
            Integer DropMaxAmount = Main.plugin.getConfig().getInt("Max-drop-amount");
            Integer DropMinAmount = Main.plugin.getConfig().getInt("Min-drop-amount");
            if (DropMaxAmount.intValue() < 1)
                DropMaxAmount = Integer.valueOf(2);
            if (DropMinAmount.intValue() < 2)
                DropMinAmount = Integer.valueOf(1);
            Double FinalizedNumber = Double.valueOf(Math.random() * (DropMaxAmount.intValue() - DropMinAmount.intValue()) + DropMinAmount.intValue());
            return Integer.parseInt(String.valueOf(Math.round(FinalizedNumber.doubleValue())));
        }
        Integer FinalAmount = Integer.valueOf(Main.plugin.getConfig().getInt("drop-amount"));
        return FinalAmount.intValue();
    }
}
