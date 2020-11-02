package fr.overcraftor.mmo.commands.guildcommands;

import fr.overcraftor.mmo.mysql.GuildSQL;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class GQuitCommand {

    public static void onCommand(Player p, Command cmd, String label, String[] args){

        if(!GuildSQL.hasGuild(p.getUniqueId())){
            p.sendMessage("§cVous n'avez pas de guild !");
            return;
        }

        if(GuildSQL.isOwner(p.getUniqueId())){
            p.sendMessage("§cVous ne pouvez pas quitter votre propre guild, vous devez la supprimmer ou définir un nouveau chef !");
            return;
        }

        GuildSQL.removePlayer(p.getUniqueId());
        p.sendMessage("§aVous avez bien quitté votre guild !");
    }

}
