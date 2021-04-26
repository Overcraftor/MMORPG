package fr.overcraftor.mmo.commands.guildcommands;

import fr.overcraftor.mmo.mysql.GuildSQL;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.List;

public class GListCommand {

    public static void onCommand(Player p, Command cmd, String label, String[] args) {

        // /g list <guild> ou /g list
        if(args.length == 1){
            final String guildName = GuildSQL.getGuildName(p.getUniqueId());
            if(guildName.equals(GuildSQL.NO_GUILD)){
                p.sendMessage("§cVeuillez indiquer le nom de la guilde §7(§e/guild list §6[GUILD]§7)");
                return;
            }

            showGuildList(p, guildName);
        }else{
            if(GuildSQL.exists(args[1])){
                showGuildList(p, args[1]);
            }else{
                p.sendMessage("§cCette guilde n'existe pas");
            }
        }

    }

    private static void showGuildList(Player p, String guildName){
        final String owner = GuildSQL.getOwner(guildName);
        final List<String> players = GuildSQL.getPlayersName(guildName);

        final StringBuilder stringBuilder = new StringBuilder();
        players.stream().filter(player -> !player.equals(owner)).forEach(player ->{
            stringBuilder.append("§7, §b" + player);
        });

        final String listMembersMessage = "§e♕ " + owner + stringBuilder.toString();

        p.sendMessage("§c§lListe des membres de la Guilde §e" + guildName + "§c: \n" + listMembersMessage);
    }
}
