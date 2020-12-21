package fr.overcraftor.mmo.utils.generalXp;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class LevelUp {

    public static void checkLevelUp(int before, int after, Player p){
        final XpLevel xpLevelBefore = new XpLevel(before);
        final XpLevel xpLevelAfter = new XpLevel(after);

        // -> LEVEL UP
        if(xpLevelBefore.getLevel() != xpLevelAfter.getLevel()){
            final YamlConfiguration config = ConfigManager.levelUpConfig;

            int level = xpLevelBefore.getLevel();
            while(level < xpLevelAfter.getLevel()){
                level++;
                //global
                for(String cmd : config.getStringList("Global")){
                    Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(),
                            cmd.replace("{user}", p.getName())
                                    .replace("{level}", Integer.toString(level)));
                }

                //level precis
                if(config.contains(Integer.toString(level))){
                    config.getStringList(Integer.toString(level)).forEach(cmd -> {
                        Main.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(),
                                cmd.replace("{user}", p.getName()));
                    });
                }
            }
        }
    }
}
