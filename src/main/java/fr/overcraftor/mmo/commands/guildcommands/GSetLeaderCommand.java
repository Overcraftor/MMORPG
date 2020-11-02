package fr.overcraftor.mmo.commands.guildcommands;

import fr.overcraftor.mmo.mysql.GuildSQL;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class GSetLeaderCommand {

    public static void onCommand(Player p, Command cmd, String label, String[] args){

        if(args.length < 2){
            p.sendMessage("§c/g setleader [JOUEUR]");
            return;
        }

        if(!GuildSQL.isOwner(p.getUniqueId())){
            p.sendMessage("§cVous n'êtes le chef d'aucune guild !");
            return;
        }

        final OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

        if(!GuildSQL.areInSameGuild(target.getUniqueId(), p.getUniqueId())){
            p.sendMessage("§cLe joueur " + target.getName() + " n'est pas dans votre guild !");
            return;
        }

        final String guildName = GuildSQL.getGuildName(p.getUniqueId());

        GuildSQL.setLeader(target.getUniqueId(), guildName);
        p.sendMessage("§aLe nouveau leader de votre guild est " + target.getName());
    }

}
