package me.polishkrowa.mapcrasher;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.plugin.java.JavaPlugin;

public final class MapCrasher extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        // Plugin startup logic
        ImageManager manager = ImageManager.getInstance();
        manager.init();

        this.getCommand("getmap").setExecutor(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Seul un joueur peut executer cette commande.");
            return true;
        }
        Player player = (Player) sender;



        MapView view = Bukkit.createMap(player.getWorld());
        view.getRenderers().clear();

        /*ImageRenderer renderer = new ImageRenderer();
        if (!renderer.load("https://i.imgur.com/GRJwFTa.png")) {
            player.sendMessage(ChatColor.RED + "L'image n'a pas pu être chargée !");
            return true;
        }
        view.addRenderer(renderer);*/

        view.addRenderer(new ImageRenderer(ImageManager.getInstance().getImage(1)));
        view.setScale(MapView.Scale.FARTHEST);
        view.setTrackingPosition(false);

        ItemStack map = new ItemStack(Material.FILLED_MAP);
        MapMeta meta = (MapMeta) map.getItemMeta();
        meta.setMapView(view);
        meta.setMapId(1);
        meta.setDisplayName("Horaire Crash");
        map.setItemMeta(meta);



        Item itemEntity = player.getWorld().dropItemNaturally(player.getLocation(), map);
        itemEntity.setOwner(player.getUniqueId());
        itemEntity.setPickupDelay(-1);

        return true;

    }
}
