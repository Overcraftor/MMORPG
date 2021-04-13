package fr.overcraftor.mmo.commands.aptitudescommands;

import fr.overcraftor.mmo.mysql.AptSQL;
import fr.overcraftor.mmo.utils.Permissions;
import fr.overcraftor.mmo.utils.aptitude.PlayerAptitude;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AddAptCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!Permissions.ADMIN.hasPerm(sender)){
            sender.sendMessage("§cVous n'avez pas la permission !");
            return true;
        }

        if(args.length != 2){
            sender.sendMessage(ChatColor.RED + "La commande est /addaptitude §6[§eJoueur§6] §6[§eAPT§6]");
            return true;
        }

        final Player target = Bukkit.getPlayer(args[0]);
        int apt;

        if(target == null){
            sender.sendMessage(ChatColor.RED + "Le joueur cible n'est pas connecte");
            return true;
        }

        try{
            apt = Integer.parseInt(args[1]);

            if(apt <= 0){
                sender.sendMessage(ChatColor.RED + "Le nombre doit être superieur à 0");
                return true;
            }
        }catch (NumberFormatException ignored){
            sender.sendMessage(ChatColor.RED + "Le nombre d'exp indiqué est invalide !");
            return true;
        }

        PlayerAptitude.getFromPlayer(target).setApt(PlayerAptitude.getFromPlayer(target).getApt() + apt);
        sender.sendMessage("Vous avez bien ajouté " + apt + " aptitude à " + target.getName());

        return true;
    }
}
