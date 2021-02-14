package fr.overcraftor.mmo.commands.xpcommands;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.mysql.GeneralXpSQL;
import fr.overcraftor.mmo.utils.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SetXpCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!Permissions.SET_XP.hasPerm(sender)){
            sender.sendMessage("§cVous n'avez pas la permission !");
            return true;
        }

        if(args.length != 2){
            sender.sendMessage(ChatColor.RED + "La commande est /setxp §6[§eJoueur§6] §6[§eXP§6]");
            return true;
        }

        final Player target = Bukkit.getPlayer(args[0]);
        int xp;

        if(target == null){
            sender.sendMessage(ChatColor.RED + "Le joueur cible n'est pas connecte");
            return true;
        }

        try{
            xp = Integer.parseInt(args[1]);

            if(xp < 0){
                sender.sendMessage(ChatColor.RED + "Le nombre doit etre superieur ou egale a 0");
                return true;
            }
        }catch (NumberFormatException ignored){
            sender.sendMessage(ChatColor.RED + "Le nombre d'exp indique est invalide !");
            return true;
        }

        Main.getInstance().generalXp.put(target, xp);
        GeneralXpSQL.setXp(target.getUniqueId(), xp);
        sender.sendMessage("§aVous avez défini l'xp de §e" + target.getName() + " §aà §6" + xp);
        Main.getInstance().getScoreboardManager().getScoreboard(target).refreshGeneralLevel();

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> list = new ArrayList<>();

        if(args.length == 1 && Permissions.SET_XP.hasPerm(sender)){
            Bukkit.getOnlinePlayers().forEach(p -> list.add(p.getName()));
        }

        return list;
    }
}
