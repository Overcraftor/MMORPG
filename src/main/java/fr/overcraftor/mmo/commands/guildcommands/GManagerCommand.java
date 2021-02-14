package fr.overcraftor.mmo.commands.guildcommands;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.commands.guildcommands.leader.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GManagerCommand implements CommandExecutor, TabCompleter {

    private final List<String> cmd = Arrays.asList("create", "join", "settag", "setleader", "disband", "quit", "kick", "list");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) return false;
        final Player p = (Player) sender;

        if(args.length == 0){
            p.sendMessage("§c/guild <§ecreate§7/§edisband§7/§ejoin§7/§esetleader§7/§esettag§7/§equit§c>");
            return true;
        }

        //comme y'a bcp de requêtes SQL, c'est mieux de run asynchronously :D
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            switch(args[0]){

                case "create":
                case "c":
                    GCreateCommand.onCommand(p, cmd, label, args);
                    break;

                case "disband":
                case "d":
                    GDisbandCommand.onCommand(p, cmd, label, args);
                    break;

                case "join":
                case "j":
                    GJoinCommand.onCommand(p, cmd, label, args);
                    break;

                case "setleader":
                    GSetLeaderCommand.onCommand(p, cmd, label, args);
                    break;

                case "settag":
                    GSetTagCommand.onCommand(p, cmd, label, args);
                    break;

                case "quit":
                case "q":
                    GQuitCommand.onCommand(p, cmd, label, args);
                    break;

                case "kick":
                case "k":
                    GKickCommand.onCommand(p, cmd, label, args);
                    break;

                case "list":
                case "l":
                    GListCommand.onCommand(p, cmd, label, args);
                    break;

                default:
                    p.sendMessage("§c/guild <§ecreate§7/§edisband§7/§ejoin§7/§esetleader§7/§esettag§c>");
                    break;
            }
        });
        return true;
    }

    // --------------- TAB COMPLETER ---------------- //
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> list = new ArrayList<>();

        if(args.length == 1){
            this.cmd.stream().filter(command -> command.startsWith(args[0]))
                    .forEach(list::add);
        }

        return list;
    }
}
