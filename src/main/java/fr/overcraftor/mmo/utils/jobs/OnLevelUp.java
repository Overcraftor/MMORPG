package fr.overcraftor.mmo.utils.jobs;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.config.ConfigManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class OnLevelUp {

    public OnLevelUp(Player p, JobsNames job, JobsLevel level){
        if(level == null) return;

        final YamlConfiguration config = ConfigManager.levelUpJop;
        final List<String> commands = config.getStringList(job.toName().replace("û", "u") + "." + level.getLevel());

        if(commands.size() != 0){
            commands.forEach(cmd ->{
                Main.getInstance().getServer().dispatchCommand(Main.getInstance().getServer().getConsoleSender(), cmd.replace("{user}", p.getName()));
            });
        }
    }

}
