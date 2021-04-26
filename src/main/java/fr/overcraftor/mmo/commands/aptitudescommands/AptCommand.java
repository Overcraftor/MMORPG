package fr.overcraftor.mmo.commands.aptitudescommands;

import fr.overcraftor.mmo.inventories.AptInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender,Command cmd,String label, String[] args) {

        if(!(sender instanceof Player))
            return true;
        AptInventory.openInventory((Player) sender);

        return true;
    }
}
