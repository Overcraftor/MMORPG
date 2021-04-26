package fr.overcraftor.mmo.commands.guildcommands.leader;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.mysql.GuildSQL;
import fr.overcraftor.mmo.utils.guilds.GuildInvitation;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class GInviteCommand {

    public static void onCommand(Player p, Command cmd, String label, String[] args){

        if(args.length != 2){
            p.sendMessage("§c/guild invite §e[§6JOUEUR§e]");
            return;
        }

        @Deprecated
        final OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

        if(!target.isOnline()){
            p.sendMessage("§cVous devez inviter un joueur connecté sur le serveur !");
            return;
        }

        if(!GuildSQL.isOwner(p.getUniqueId())){
            p.sendMessage("§cVous devez être le chef d'une guild pour inviter un joueur");
            return;
        }

        final String guildName = GuildSQL.getGuildName(p.getUniqueId());

        if(Main.getInstance().invitations.stream().anyMatch(invitation -> invitation.isInvited(target.getUniqueId(), guildName))){
            p.sendMessage("§cCe joueur est déjà invité dans votre guild");
        }else{
            new GuildInvitation(target.getUniqueId(), guildName).sendMessage();
            p.sendMessage("§aVous avez bien invité le joueur §6" + target.getName() + " §adans votre guild !");
        }

    }

}
