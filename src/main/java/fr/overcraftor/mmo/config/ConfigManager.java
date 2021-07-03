package fr.overcraftor.mmo.config;

import fr.overcraftor.mmo.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigManager {

    public static final HashMap<String, List<String>> blockBreak = new HashMap<>();
    public static YamlConfiguration levelUpJopConfig;
    public static YamlConfiguration levelUpConfig;
    private static YamlConfiguration config;
    public static final List<String> regions = new ArrayList<>();

    public static void init(){
        Main.getInstance().getLogger().info("Reading yaml files...");

        //--------------- BLOCK BREAK ------------------ //
        final ConfigurationAPI blockBreak = new ConfigurationAPI("block_break.yml", Main.getInstance());
        blockBreak.create();

        blockBreak.get().getKeys(true).forEach(key ->{
            if(Material.getMaterial(key.toUpperCase()) != null){
                ConfigManager.blockBreak.put(key.toLowerCase(), blockBreak.get().getStringList(key));
            }else{
                Main.getInstance().getLogger().warning("Le block " + key + " n'exite pas [block_break.yml]");
            }
        });

        //----------------------- LEVEL JOB UP --------------------------//
        final ConfigurationAPI levelUpJob = new ConfigurationAPI("job_level_up.yml", Main.getInstance());
        levelUpJob.create();
        levelUpJopConfig = levelUpJob.get();

        //----------------------- LEVEL JOB GENERAL --------------------------//
        final ConfigurationAPI levelUp = new ConfigurationAPI("level_up.yml", Main.getInstance());
        levelUp.create();
        levelUpConfig = levelUp.get();

        //-------------------------- CONFIG GENERALE ------------------------//
        final ConfigurationAPI configApi = new ConfigurationAPI("config.yml", Main.getInstance());
        configApi.create();
        config = configApi.get();

        //-------------------------- REGIONS ------------------------//
        final ConfigurationAPI regionConfig = new ConfigurationAPI("region.yml", Main.getInstance());
        configApi.create();
        regions.addAll(regionConfig.get().getStringList("regions"));

        Main.getInstance().getLogger().info("Yaml files readed successfully !");
    }

    public static YamlConfiguration getConfig() {
        return config;
    }
}