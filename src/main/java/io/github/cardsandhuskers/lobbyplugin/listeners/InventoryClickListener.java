package io.github.cardsandhuskers.lobbyplugin.listeners;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import io.github.cardsandhuskers.lobbyplugin.handlers.VotingListHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    private LobbyPlugin plugin;
    VotingListHandler votingListHandler;

    public InventoryClickListener(LobbyPlugin plugin) {
        this.plugin = plugin;
        votingListHandler = new VotingListHandler();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        String invName = e.getView().getTitle();
        if (e.getCurrentItem() != null) {
            Player p = (Player) e.getWhoClicked();
            if (p.getWorld().equals(plugin.getConfig().getLocation("Lobby").getWorld())) {
                if (invName.equalsIgnoreCase(ChatColor.AQUA + "voting menu")) {
                    if (e.getCurrentItem().getType() == Material.PURPLE_CONCRETE) {
                        votingListHandler.setVote(p, "battlebox");
                    } else if (e.getCurrentItem().getType() == Material.SPRUCE_PLANKS) {
                        votingListHandler.setVote(p, "buildbattle");
                    } else if (e.getCurrentItem().getType() == Material.TNT) {
                        votingListHandler.setVote(p, "tntrun");
                    } else if (e.getCurrentItem().getType() == Material.WOODEN_SWORD) {
                        votingListHandler.setVote(p, "survivalgames");
                    }
                }
                e.setCancelled(true);
            }
        }
    }

}
