package io.github.cardsandhuskers.lobbyplugin.handlers;

import io.github.cardsandhuskers.lobbyplugin.LobbyPlugin;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.profile.PlayerProfile;

import java.util.ArrayList;
import java.util.UUID;

import static io.github.cardsandhuskers.teams.Teams.handler;
import static io.github.cardsandhuskers.teams.Teams.ppAPI;

public class CitizensHandler {
    private LobbyPlugin plugin;
    private static ArrayList<NPC> npcTeamList;
    private static ArrayList<NPC> npcPlayerList;
    public CitizensHandler(LobbyPlugin plugin) {
        this.plugin = plugin;
        npcTeamList = new ArrayList<>();
        npcPlayerList = new ArrayList<>();
    }


    public void buildNPCS() {
        NPCRegistry registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());


        int i = 1;
        while(plugin.getConfig().getLocation("Podiums.team." + i) != null && i <= handler.getNumTeams()) {
            int finalI = i;
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ()-> {
                NPC npc = registry.createNPC(EntityType.PLAYER, "%Teams_team" + finalI + "%");
                SkinTrait skinTrait = npc.getOrAddTrait(SkinTrait.class);
                skinTrait.setSkinName("Ryan_7777");

                //skinTrait.setTexture("ewogICJ0aW1lc3RhbXAiIDogMTY3MDAxOTc3NzcxNiwKICAicHJvZmlsZUlkIiA6ICJiMjdjMjlkZWZiNWU0OTEyYjFlYmQ5NDVkMmI2NzE0YSIsCiAgInByb2ZpbGVOYW1lIiA6ICJIRUtUMCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kZWRkNTQxODBmNzFhZTZkZjM5NDczMGU1NGM2NGM3MzAwMDFlZWMzZjVkOWNhOTcwNGMzNTAzZmFjNWQxNWMzIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=", "K6cHJMwUS5lc6sdHtFFcCSJIhEa4HpU/Mv+3VrcRy6FI+mmUBQUhf41io3CtIYzL67FWU5T/J+Ne6PP03Cds4hL11zH3h6WuxVZwSIerrg5TizQLRBQDstuTv7YW1wgWdoPxhhOkTDVVM4KOQsFYLNERTR1FyvsrT4jW5XnUbzZ859D5KAnrs60yyiIzSfSP9cdWO4jeyWlQ1P4X74BFcYBys0aPsqcjoxHwnkXC2ND/suJSNs2Y4AuYQHq6hE0S0QUD2mCi++xb5OLMp7tiNPRCCvwTWcxxG/Vr35/5bqOyNfkWF4O2epxql2Vo6JzZaeYSyEejtalrWaCHLAQ6uhyR83hE/k7RnRycyde7M0zX37yzY+XWgAqxTProiASnzL8Ubs/asQGKVRU++Y9lJj6Y5X96bD+nyi3oDwuzdrNDpMC0fbWYcnZzMTntcH0owQisL6p+gWPdJzvWGICL+VAiUN/dCb0vdIxqIqh0Rlx7qgZy4xM8I0K/DdCxKbQe+PQqWLH5VBYB9E+ZAWxnqnRi2CZD/8Bl32PLsKEYhQj62eeJvJSjGhv7Sc5dBqBp4IlsWgTXzrl4xaA5atnMTlGJYPbLeNQalEEjanx8ay4wMQOHBzdvrS9v57zzl7Y1k4XO+00cnI5hEQNRpPbrVE9cTK5yrLP5S4snfFKLdlU=");

                //GameProfile skin2b90b54f = new GameProfile(UUID.fromString("e9130176-0d1c-4b56-99a2-ea296488b44d"), "skin2b90b54f");
                //skin2b90b54f.getProperties().put("textures", new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTY3MDAyMTEwOTI1NCwKICAicHJvZmlsZUlkIiA6ICJhZmExOTJmZTFhODY0N2FiOTQ4MzM4ZDY5OTg4ZWNkOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJpX2FtX2Ffc3F1aWRfa2lkIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2NiNjNjYjVhMzY4MWJlMDM5OGUwYzE2YjhkMjZjM2NlMDZlMjAwYjQxZTE1MDk1NjBjY2QwNzRjZTQwZTQ0OTIiCiAgICB9CiAgfQp9", "uv3AdbXBTDnG+b9ymlKPf8VtaFGidWNoaiVZmELFYnGY+AdaF5NinMQfiYxiZC7hLDo8w6jRrzGxNcueAq08g0ETgBSLb/ekfSb6Us6QhOXnvyKDvDSksOMOF7OYv9rMabrOqCMaUyJjT1jrmz9gx/lIVymq12GSf5qrXojANOfc4/eg4ntYJx58Thy9y1bsr31RCEKnqlvWdIiyl/P14fy/NMGS62koI72QKzzAtCs8d9ozs0ibUY8LMI7strk+y5mL8rwOiCVqvewYwLtSRH22sAGvpLvxh9OP7kx6zxmyr3V9632/Pq+edq2EGbd4pxrT7PLt5a6AqQgh0HGYfy7FX0GO9kd5NbkxQBTU7eoIM5koqKwm1pf7rfs1aySUEMowzxpxtS78yRUp6/B0ZXNWJq+KfaIDMGOyq6QLLrMkLImVC2fzyF1qC3E9ITrKzeylsbj1rqKhC3J2+D8DWbdajcEfEWaHeLbHPh0+wpwXdTdeEYJ4xiSkCmLFk3xil0EUT+lTKXjZGZcn+DgclOkY8uZJKLvvExf9dosvF6IbFG71plYya4tfxU0dGwZ1pmyT0COhdSnpUuxnyvEJVfzYJgdzNCrwTligpb/3S1P+xE5svh2Pz1yl5Al7Mk7m4lR0UV+x1+C/NNqN5M3dPNPUYbV/TtXVLrKrLz7wX58="));



                String name;
                switch(finalI) {
                    case 1:
                        name = ChatColor.RED + "" + ChatColor.BOLD + "1st" + ChatColor.RESET + ChatColor.GREEN + " Place";
                        break;
                    case 2:
                        name = ChatColor.RED + "" + ChatColor.BOLD + "2nd" + ChatColor.RESET + ChatColor.GREEN + " Place";
                        break;
                    case 3:
                        name = ChatColor.RED + "" + ChatColor.BOLD + "3rd" + ChatColor.RESET + ChatColor.GREEN + " Place";
                        break;
                    default:
                        name = ChatColor.RED + "" + ChatColor.BOLD + finalI + "th" + ChatColor.RESET + ChatColor.GREEN + " Place";
                        break;
                }
                npc.getOrAddTrait(net.citizensnpcs.trait.HologramTrait.class).setLine(0, name);
                npc.spawn(plugin.getConfig().getLocation("Podiums.team." + finalI));
                npcTeamList.add(npc);
            }, i * 2);
            i++;
        }

        i = 1;
        while(plugin.getConfig().getLocation("Podiums.player." + i) != null) {
            int finalI = i;
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ()-> {
                NPC npc = registry.createNPC(EntityType.PLAYER, "%Teams_player" + finalI + "%");
                SkinTrait skinTrait = npc.getOrAddTrait(SkinTrait.class);
                skinTrait.setSkinName(ppAPI.getTopSortedPoints().get(finalI-1).getUsername());

                String name;
                switch(finalI) {
                    case 1:
                        name = ChatColor.RED + "" + ChatColor.BOLD + "1st" + ChatColor.RESET + ChatColor.GREEN + " Place";
                        break;
                    case 2:
                        name = ChatColor.RED + "" + ChatColor.BOLD + "2nd" + ChatColor.RESET + ChatColor.GREEN + " Place";
                        break;
                    case 3:
                        name = ChatColor.RED + "" + ChatColor.BOLD + "3rd" + ChatColor.RESET + ChatColor.GREEN + " Place";
                        break;
                    default:
                        name = ChatColor.RED + "" + ChatColor.BOLD + finalI + "th" + ChatColor.RESET + ChatColor.GREEN + " Place";
                        break;
                }
                npc.getOrAddTrait(net.citizensnpcs.trait.HologramTrait.class).setLine(0, name);
                npc.spawn(plugin.getConfig().getLocation("Podiums.player." + finalI));
                npcPlayerList.add(npc);
            }, i * 2 + 24);
            i++;
        }



        //Player npc = Bukkit.getPlayer("%teams_team" + i + "%");
        //NPC npc2 = CitizensAPI.getNPCRegistry().getNPC(npc);
        //npc2.setName("TESTUPDATE");
        //System.out.println(CitizensAPI.getTraitFactory().getRegisteredTraits());
        //System.out.println(CitizensAPI.getTraitFactory().getTraitClass("HologramTrait"));


        //NPCRegistry registry = CitizensAPI.getNPCRegistry();
        //NPC npc = registry.createNPC(EntityType.PLAYER, "Gerald");
        //npc.spawn(p.getLocation());


        //should be correct way to update hologram
        /*
        NPC npc = getNPC(i);
        npc.getOrAddTrait(net.citizensnpcs.trait.HologramTrait.class).setLine(0, name);

         */
    }
    public void updateSkins() {
        int counter = 0;
        for(NPC npc:npcPlayerList) {
            SkinTrait skinTrait = npc.getOrAddTrait(SkinTrait.class);
            skinTrait.setSkinName(ppAPI.getTopSortedPoints().get(counter).getUsername());
            counter++;
        }

    }
    public static void clearEquipment() {
        for(NPC npc:npcTeamList) {
            System.out.println("CLEAR");
            Equipment equipment = npc.getOrAddTrait(Equipment.class);
            equipment.set(Equipment.EquipmentSlot.HAND, new ItemStack(Material.AIR));
        }
        for(NPC npc:npcPlayerList) {
            System.out.println("CLEAR");
            Equipment equipment = npc.getOrAddTrait(Equipment.class);
            equipment.set(Equipment.EquipmentSlot.HAND, new ItemStack(Material.AIR));
        }

    }
/*
    public String getPlayer(String color) {
        switch(color) {
            case "§2": return 4;
            case "§3": return Material.CYAN_WOOL;
            case "§5": return Material.PURPLE_WOOL;
            case "§6": return "orange";
            case "§7": return Material.LIGHT_GRAY_WOOL;
            case "§8": return Material.GRAY_WOOL;
            case "§9": return Material.BLUE_WOOL;
            case "§a": return Material.LIME_WOOL;
            case "§b": return "light_blue";
            case "§c": return Material.RED_WOOL;
            case "§d": return Material.MAGENTA_WOOL;
            case "§e": return "Ungodly";
            default: return Material.WHITE_WOOL;
        }
    }

 */

}
