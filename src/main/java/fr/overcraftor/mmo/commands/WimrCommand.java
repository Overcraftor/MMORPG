package fr.overcraftor.mmo.commands;

import fr.overcraftor.mmo.utils.Permissions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WimrCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player) || !Permissions.ADMIN.hasPermMessage(sender))
            return true;

        final String region = PlaceholderAPI.setPlaceholders((Player) sender, "%worldguard_region_name%");

        if(region.isEmpty() || region.isBlank())
            sender.sendMessage("§c§lVotre région actuel: §6(" + region + ")");
        else
            sender.sendMessage("§c§lVous n'êtes pas dans une région.");

        return true;
    }

}
