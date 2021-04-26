package fr.overcraftor.mmo.commands.manacommands;

import fr.overcraftor.mmo.mysql.ManaSQL;
import fr.overcraftor.mmo.utils.Permissions;
import fr.overcraftor.mmo.utils.mana.PlayerMana;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddMaxManaCommand implements CommandExecutor {

    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {

        if(!Permissions.ADD_MAX_MANA.hasPerm(sender)){
            sender.sendMessage("§cVous n'avez pas la permission d'executer cette commande.");
            return true;
        }

        if(args.length != 2){
            sender.sendMessage(ChatColor.RED + "La commande est /addmaxmana §6[§eJoueur§6] §3[§bMana§3]");
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

            if(mana <= 0){
                sender.sendMessage(ChatColor.RED + "Le nombre doit être superieur à 0");
                return true;
            }
        }catch (NumberFormatException ignored){
            sender.sendMessage(ChatColor.RED + "Le nombre de mana indiqué est invalide !");
            return true;
        }

        ManaSQL.addManaMaxBonus(target.getUniqueId(), mana);
        PlayerMana.getFromPlayer(target).addMaxManaBonus(mana);
        sender.sendMessage("§aVous avez bien ajouté " + mana + " max mana à " + target.getName());

        return true;
    }

}
