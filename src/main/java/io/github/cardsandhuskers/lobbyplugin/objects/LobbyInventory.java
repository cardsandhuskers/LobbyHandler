package io.github.cardsandhuskers.lobbyplugin.objects;

import io.github.cardsandhuskers.lobbyplugin.handlers.CitizensHandler;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LobbyInventory {

    public LobbyInventory() {

    }
    public void addTeamSelector(Player p) {
        Inventory inv = p.getInventory();
        inv.clear();

        ItemStack netherStar = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta netherStarMeta = netherStar.getItemMeta();
        netherStarMeta.setDisplayName("Select Team");
        netherStar.setItemMeta(netherStarMeta);
        inv.setItem(0, netherStar);


        if(p.isOp()) {
            ItemStack adminTool = new ItemStack(Material.BLAZE_ROD, 1);
            ItemMeta adminToolMeta = adminTool.getItemMeta();
            adminToolMeta.setDisplayName("Game Start Rod");
            adminTool.setItemMeta(adminToolMeta);
            inv.setItem(8, adminTool);
        }
    }

    /**
     * adds voting diamond to inventory and updates it to enchanted when player has cast their vote
     * @param p
     * @param vote whether player has currently voted (use false if you're calling this)
     */
    public void addVotingItems(Player p, boolean vote) {
        Inventory inv = p.getInventory();

        ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
        ItemMeta diamondMeta = diamond.getItemMeta();

        if(vote == true) {
            diamondMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        }
        diamondMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        diamondMeta.setDisplayName("Vote Now!");
        diamond.setItemMeta(diamondMeta);
        inv.setItem(4, diamond);
        //CitizensHandler.clearEquipment();
    }
    public void votingAnimationA(Player p) {
        Inventory inv = p.getInventory();
        ItemStack paneA = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1);
        ItemMeta paneAMeta = paneA.getItemMeta();
        paneAMeta.setDisplayName(">>>");
        paneA.setItemMeta(paneAMeta);
        inv.setItem(3, paneA);
        paneAMeta.setDisplayName("<<<");
        paneA.setItemMeta(paneAMeta);
        inv.setItem(5, paneA);

        ItemStack paneB = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE, 1);
        ItemMeta paneBMeta = paneB.getItemMeta();
        paneAMeta.setDisplayName(">>>");
        paneB.setItemMeta(paneAMeta);
        inv.setItem(2, paneB);
        paneAMeta.setDisplayName("<<<");
        paneB.setItemMeta(paneAMeta);
        inv.setItem(6, paneB);


    }
    public void votingAnimationB(Player p) {
        Inventory inv = p.getInventory();
        ItemStack paneA = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE, 1);
        ItemMeta paneAMeta = paneA.getItemMeta();
        paneAMeta.setDisplayName(">>>");
        paneA.setItemMeta(paneAMeta);
        inv.setItem(3, paneA);
        paneAMeta.setDisplayName("<<<");
        paneA.setItemMeta(paneAMeta);
        inv.setItem(5, paneA);

        ItemStack paneB = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1);
        ItemMeta paneBMeta = paneB.getItemMeta();
        paneAMeta.setDisplayName(">>>");
        paneB.setItemMeta(paneAMeta);
        inv.setItem(2, paneB);
        paneAMeta.setDisplayName("<<<");
        paneB.setItemMeta(paneAMeta);
        inv.setItem(6, paneB);

    }


    public void removeVotingItems(Player p) {
        addTeamSelector(p);
    }


}
