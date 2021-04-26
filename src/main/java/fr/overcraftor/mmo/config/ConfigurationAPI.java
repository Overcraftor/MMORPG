package fr.overcraftor.mmo.config;

import fr.overcraftor.mmo.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Logger;

public class ConfigurationAPI {

    private final File file;
    private final String resourcePath;
    private YamlConfiguration config;
    private final JavaPlugin plugin;

    public ConfigurationAPI(String configName, JavaPlugin javaPlugin) {
        this.resourcePath = configName;
        this.plugin = javaPlugin;

        this.file = new File(plugin.getDataFolder(), configName);
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration get(){
        return config;
    }

    public void save(){
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload(){
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public File getFile(){
        return this.file;
    }

    //CREATE CONFIG
    public void create(){
        final Logger logger = Main.getInstance().getLogger();

        if(resourcePath == null || resourcePath.isEmpty()){
            throw new IllegalArgumentException("ResourcePath can't be null or empty");
        }

        final InputStream in = plugin.getResource(resourcePath);
        if(in == null){
            throw new IllegalArgumentException("The resource '" + resourcePath + "' can't be found un plugin jar");
        }

        if(!plugin.getDataFolder().exists() && !plugin.getDataFolder().mkdir()){
            logger.severe("Failed to make directory");
        }

        final File outFile = this.file;

        try {
            if(!outFile.exists()){
                logger.info("The " + resourcePath + " was not found, creation in progress...");

                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int n;

                while((n = in.read(buf)) > 0){
                    out.write(buf, 0, n);
                }

                out.close();
                in.close();

                if(!outFile.exists()){
                    logger.severe("Unable to copy file");
                }
            }
        }catch(IOException e){
            logger.severe("Unable to copy file");
        }
    }
}
