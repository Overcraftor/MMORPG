package fr.overcraftor.mmo.commands.manacommands;

import fr.overcraftor.mmo.utils.Permissions;
import fr.overcraftor.mmo.utils.mana.PlayerMana;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetManaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!Permissions.SET_MANA.hasPerm(sender)){
            sender.sendMessage("§cVous n'avez pas la permission d'executer cette commande.");
            return true;
        }

        if(args.length != 2){
            sender.sendMessage(ChatColor.RED + "La commande est /setmaxmana §6[§eJoueur§6] §3[§bMana§3]");
            return true;
        }

        final Player target = Bukkit.getPlayer(args[0]);
        int mana;

        if(target == null){
            sender.sendMessage(ChatColor.RED + "Le joueur cible n'est pas connecté");
            return true;
        }

        try{
            mana = Integer.parseInt(args[1]);

            if(mana < 0){
                sender.sendMessage(ChatColor.RED + "Le nombre doit etre superieur ou égale a 0");
                return true;
            }
        }catch (NumberFormatException ignored){
            sender.sendMessage(ChatColor.RED + "Le nombre de mana indique est invalide !");
            return true;
        }

        PlayerMana.getFromPlayer(target).setMana(mana);
        sender.sendMessage("§aVous avez défini le mana de " + target.getName() + " à " + mana);

        return true;
    }

}
