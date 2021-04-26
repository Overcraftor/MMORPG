package fr.overcraftor.mmo.commands.xpcommands;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.utils.generalXp.LevelUp;
import fr.overcraftor.mmo.utils.Permissions;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AddXpCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command,String label, String[] args) {

        if(!Permissions.ADD_XP.hasPerm(sender)){
            sender.sendMessage("§cVous n'avez pas la permission !");
            return true;
        }

        if(args.length != 2){
            sender.sendMessage(ChatColor.RED + "La commande est /addxp §6[§eJoueur§6] §6[§eXP§6]");
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
            // + 15% si le joueur est premium
            if(Permissions.PREMIUM.hasPerm(target)) xp *= 1.15;

            if(xp <= 0){
                sender.sendMessage(ChatColor.RED + "Le nombre doit etre superieur a 0");
                return true;
            }
        }catch (NumberFormatException ignored){
            sender.sendMessage(ChatColor.RED + "Le nombre d'exp indique est invalide !");
            return true;
        }

        final int xpBefore = Main.getInstance().generalXp.get(target);

        final int i = xpBefore + xp;
        if(i < 0){
            Main.getInstance().generalXp.put(target, Integer.MAX_VALUE);
            return true;
        }

        LevelUp.checkLevelUp(xpBefore, i, target);

        Main.getInstance().generalXp.put(target, i);
        target.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§a+§2[§a" + xp + " XP§2]"));
        Main.getInstance().getScoreboardManager().getScoreboard(target).refreshGeneralLevel();

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> list = new ArrayList<>();

        if(args.length == 1 && Permissions.ADD_XP.hasPerm(sender)){
            Bukkit.getOnlinePlayers().forEach(p -> list.add(p.getName()));
        }

        return list;
    }
}
