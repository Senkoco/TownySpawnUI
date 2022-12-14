package me.senkoco.townyspawnui.commands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.senkoco.townyspawnui.utils.metadata.MetaData.setBlockInMenu;

public class CommandSetItemTown implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("townyspawnui.set.town.item")) {
            sender.sendMessage(ChatColor.RED + "You can't do that!");
            return false;
        }else if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
            showHelp(sender);
            return false;
        }else{
            Resident res = TownyAPI.getInstance().getResident((Player)sender);
            if(!res.hasTown()){
                sender.sendMessage(ChatColor.RED + "You aren't in a town!");
                return false;
            }else if(!res.isMayor()){
                sender.sendMessage(ChatColor.RED + "You aren't the mayor of your town!");
                return false;
            }

            Material material;
            try {
                material = Material.valueOf(args[0].toUpperCase());
            }catch(IllegalArgumentException e){
                sender.sendMessage(ChatColor.RED + "Please provide a valid item or block name!\n" + ChatColor.RED + "Example: nether_star (Insensitive to case, spaces must be replaced by underscores.");
                return false;
            }

            setBlockInMenu(res.getTownOrNull(), material.name());
            sender.sendMessage(ChatColor.GREEN + "Your town's item/block in the menu now is: " + material.name());
        }
        return false;
    }

    private void showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Usage: /town set menu-item <item-name>\n" + ChatColor.RED + "<> = Mandatory");
    }
}

