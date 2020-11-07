package fr.overcraftor.mmo;

import fr.overcraftor.mmo.commands.guildcommands.GManagerCommand;
import fr.overcraftor.mmo.commands.jobscommands.AddXpJobCommand;
import fr.overcraftor.mmo.commands.jobscommands.JobCommand;
import fr.overcraftor.mmo.commands.jobscommands.SetXpJobCommand;
import fr.overcraftor.mmo.commands.xpCommands.AddXpCommand;
import fr.overcraftor.mmo.commands.xpCommands.SetXpCommand;
import fr.overcraftor.mmo.events.*;
import fr.overcraftor.mmo.mysql.GeneralXpSQL;
import fr.overcraftor.mmo.mysql.GuildSQL;
import fr.overcraftor.mmo.mysql.JobsSQL;
import fr.overcraftor.mmo.mysql.SQLConnection;
import fr.overcraftor.mmo.config.ConfigManager;
import fr.overcraftor.mmo.config.ConfigurationAPI;
import fr.overcraftor.mmo.timers.XpSaveTimer;
import fr.overcraftor.mmo.utils.jobs.JobsNames;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {

    private SQLConnection sql;
    private static Main instance;

    public static final HashMap<Player, HashMap<JobsNames, Integer>> jobsXp = new HashMap<>();
    public final HashMap<Player, Integer> generalXp = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "===================================================================");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Chargement du plugin" + ChatColor.GOLD + "[" + getName() + "]");
        instance = this;

        ConfigManager.init();

        getLogger().info("Chargement de la bdd mysql...");
        if(dataConnection()){
            JobsSQL.createTable(this);
            GuildSQL.createTable();
            GeneralXpSQL.createTable();

            getLogger().info("MYSQL: Correctement initialise");
            registration();

            final Long fiveMn = 5 * 60 * 20L;
            new XpSaveTimer().runTaskTimer(this, fiveMn, fiveMn);
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "===================================================================");
    }

    @Override
    public void onDisable() {

        if(sql != null){
            for(Map.Entry<Player, HashMap<JobsNames, Integer>> entry : jobsXp.entrySet()){
                JobsSQL.setAllXp(entry.getValue(), entry.getKey().getUniqueId());
            }
            for(Map.Entry<Player, Integer> entry : generalXp.entrySet()){
                GeneralXpSQL.setXp(entry.getKey().getUniqueId(), entry.getValue());
            }
            sql.disconnect();
        }
    }

    private boolean dataConnection(){
        ConfigurationAPI config = new ConfigurationAPI("database.yml", this);
        config.create();

        final String urlbase = config.get().getString("urlbase");
        final String host = config.get().getString("host");
        final String database = config.get().getString("database");
        final String user = config.get().getString("user");
        final String pass = config.get().getString("pass");

        this.sql = new SQLConnection(urlbase, host, database, user, pass);
        try {
            sql.connect();
            return true;
        } catch (SQLException ignored) {
            getLogger().warning("La connection MYSQL a echoue, les fonctionnalites du plugin sont desactivees");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        return false;
    }

    private void registration(){
        //events
        getServer().getPluginManager().registerEvents(new OnBlockBreak(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new OnInventoryClick(), this);
        getServer().getPluginManager().registerEvents(new OnDamage(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatEvent(), this);

        //commands
        getCommand("jobs").setExecutor(new JobCommand());
        getCommand("addxpjobs").setExecutor(new AddXpJobCommand());
        getCommand("setxpjobs").setExecutor(new SetXpJobCommand());

        getCommand("guild").setExecutor(new GManagerCommand());

        getCommand("addxp").setExecutor(new AddXpCommand());
        getCommand("setxp").setExecutor(new SetXpCommand());

        //table completer
        getCommand("jobs").setTabCompleter(new JobCommand());
        getCommand("addxpjobs").setTabCompleter(new AddXpJobCommand());
        getCommand("setxpjobs").setTabCompleter(new SetXpJobCommand());

        getCommand("addxp").setTabCompleter(new AddXpJobCommand());
        getCommand("setxp").setTabCompleter(new SetXpJobCommand());

        getCommand("guild").setTabCompleter(new GManagerCommand());
    }

    //GETTERS
    public SQLConnection getSql() {
        return sql;
    }

    public static Main getInstance() {
        return instance;
    }
}