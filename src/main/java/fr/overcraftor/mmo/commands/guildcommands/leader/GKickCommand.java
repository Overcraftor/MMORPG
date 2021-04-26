package fr.overcraftor.mmo.commands.guildcommands.leader;

import fr.overcraftor.mmo.mysql.GuildSQL;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class GKickCommand {

    public static void onCommand(Player p, Command cmd, String label, String[] args){
        if(!GuildSQL.isOwner(p.getUniqueId())){
            p.sendMessage("§cTu dois être le chef d'une guild pour executer cette commande.");
            return;
        }

        if(args.length != 2){
            p.sendMessage("§c/guild kick §e[§6joueur§e]");
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

        if(target.getUniqueId() == p.getUniqueId()){
            p.sendMessage("§cVous ne pouvez pas vous exclure vous même !");
            return;
        }

        final String senderGuildName = GuildSQL.getGuildName(p.getUniqueId());
        if(!GuildSQL.getGuildName(target.getUniqueId()).equals(senderGuildName)){
            p.sendMessage("§cLe joueur " + target.getName() + " n'est pas dans votre guild");
            return;
        }

        GuildSQL.removePlayer(target.getUniqueId());
        p.sendMessage("§aVous avez kick §e" + target.getName() + " §ade votre guild !");
        if(target.isOnline())
            target.getPlayer().sendMessage("§cVous avez été kick de la guild §e" + senderGuildName);
    }

}
