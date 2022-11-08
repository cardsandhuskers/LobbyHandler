package io.github.cardsandhuskers.lobbyplugin.objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

import static io.github.cardsandhuskers.lobbyplugin.LobbyPlugin.remainingGames;

public class VotingMenu {
    private UUID player;
    private String vote;
    public VotingMenu(UUID player) {
        this.player = player;
    }

    public void openMenu() {
        Player p = Bukkit.getPlayer(player);
        Inventory inventory = Bukkit.createInventory(p, 18, ChatColor.AQUA + "Voting Menu");

        int index = 0;
        for(String s:remainingGames) {

            ItemStack stack = new ItemStack(getMaterial(s), 1);
            ItemMeta stackMeta = stack.getItemMeta();
            stackMeta.setDisplayName(s);
            stackMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            if(vote!= null && s.equals(vote)) {
                stackMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            }
            stack.setItemMeta(stackMeta);
            inventory.setItem(index, stack);

            index++;
        }

        p.openInventory(inventory);
    }
    public Material getMaterial(String s) {
        switch(s) {
            case "battlebox":
                return Material.PURPLE_CONCRETE;
            case "buildbattle":
                return Material.SPRUCE_PLANKS;
            case "tntrun":
                return Material.TNT;
            case "survivalgames":
                return Material.WOODEN_SWORD;
            default:
                return Material.AIR;
        }
    }

    public Player getPlayer() {
        Player p = Bukkit.getPlayer(player);
        return p;
    }

    public void setVote(String vote) {
        this.vote = vote;
        openMenu();
    }

    public String getVote() {
        return vote;
    }
}
