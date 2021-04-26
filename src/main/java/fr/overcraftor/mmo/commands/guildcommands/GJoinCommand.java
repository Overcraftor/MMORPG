package fr.overcraftor.mmo.commands.guildcommands;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.mysql.GuildSQL;
import fr.overcraftor.mmo.utils.guilds.GuildInvitation;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class GJoinCommand {

    public static void onCommand(Player p, Command cmd, String label, String[] args){

        if(args.length != 2){
            p.sendMessage("§c/guild join §e[§6NOM§e]");
            return;
        }

        final String guildName = args[1];

        if(GuildSQL.hasGuild(p.getUniqueId())){
            p.sendMessage("§cVous êtes déjà dans une guild");
            return;
        }

        if(!GuildSQL.exists(guildName)){
            p.sendMessage("§cCette guild n'existe pas !");
            return;
        }

        if(Main.getInstance().invitations.stream().anyMatch(invitation -> invitation.isInvited(p.getUniqueId(), guildName))){
            GuildSQL.addPlayer(guildName, p.getUniqueId());
            p.sendMessage("§aVous avez bien rejoint la guild: §6" + guildName);
            Main.getInstance().getScoreboardManager().getScoreboard(p).setGuild(guildName);
            GuildInvitation.remove(p.getUniqueId(), guildName);
        }else{
            p.sendMessage("§cVous n'êtes pas invité dans cette guild");
        }

    }
}
