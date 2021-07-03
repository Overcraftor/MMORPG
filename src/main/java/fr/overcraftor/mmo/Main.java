package fr.overcraftor.mmo;

import fr.overcraftor.mmo.commands.aptitudescommands.*;
import fr.overcraftor.mmo.commands.guildcommands.*;
import fr.overcraftor.mmo.commands.jobscommands.*;
import fr.overcraftor.mmo.commands.manacommands.*;
import fr.overcraftor.mmo.commands.xpcommands.*;
import fr.overcraftor.mmo.commands.*;
import fr.overcraftor.mmo.config.ConfigManager;
import fr.overcraftor.mmo.config.ConfigurationAPI;
import fr.overcraftor.mmo.events.*;
import fr.overcraftor.mmo.mysql.*;
import fr.overcraftor.mmo.scoreboard.MMOScoreboardManager;
import fr.overcraftor.mmo.spells.managers.SpellManager;
import fr.overcraftor.mmo.timers.XpSaveTimer;
import fr.overcraftor.mmo.utils.guilds.GuildInvitation;
import fr.overcraftor.mmo.utils.jobs.JobsNames;
import fr.overcraftor.mmo.utils.mana.PlayerMana;
import net.minecraft.server.v1_14_R1.EntityGolem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {

    private SQLConnection sql;
    private SpellManager spellManager;
    private static Main instance;

    public static final HashMap<Player, HashMap<JobsNames, Integer>> jobsXp = new HashMap<>();
    public final HashMap<Player, Integer> generalXp = new HashMap<>();
    public final HashMap<Player, PlayerMana> playerMana = new HashMap<>();
    public final HashMap<Player, List<EntityGolem>> customGolems = new HashMap<>();

    private MMOScoreboardManager scoreboardManager;
    public final ArrayList<GuildInvitation> invitations = new ArrayList<>();

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "===================================================================");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Chargement du plugin" + ChatColor.GOLD + "[" + getName() + "]");

        instance = this;
        ConfigManager.init();

        getLogger().info("Loading MYSQL database...");
        if(dataConnection()){
            JobsSQL.createTable(this);
            GuildSQL.createTable();
            GeneralXpSQL.createTable();
            AptSQL.createTable();
            ManaSQL.createTable();

            getLogger().info("MYSQL: Correctly initialized");
            registration();

            final long fiveMn = 5 * 60 * 20L;
            new XpSaveTimer().runTaskTimer(this, fiveMn, fiveMn);
        }

        scoreboardManager = new MMOScoreboardManager();
        spellManager = new SpellManager();

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
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new FireBallExplodeListener(), this);

        // ----------- COMMANDS ----------- //
        // jobs
        getCommand("jobs").setExecutor(new JobCommand());
        getCommand("addxpjobs").setExecutor(new AddXpJobCommand());
        getCommand("setxpjobs").setExecutor(new SetXpJobCommand());

        // others
        getCommand("guild").setExecutor(new GManagerCommand());
        getCommand("wimr").setExecutor(new WimrCommand());

        // general xp
        getCommand("addxp").setExecutor(new AddXpCommand());
        getCommand("setxp").setExecutor(new SetXpCommand());

        // aptitudes
        getCommand("addaptitude").setExecutor(new AddAptCommand());
        getCommand("setaptitude").setExecutor(new SetAptCommand());
        getCommand("competance").setExecutor(new AptCommand());

        //mana
        getCommand("addmaxmana").setExecutor(new AddMaxManaCommand());
        getCommand("setmaxmana").setExecutor(new SetMaxManaCommand());
        getCommand("addmana").setExecutor(new AddManaCommand());
        getCommand("setmana").setExecutor(new SetManaCommand());

        getCommand("mmo").setExecutor(new MMOResetCommand());

        // ------------ TAB COMPLETER ------------ //
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
    public MMOScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
    public SpellManager getSpellManager() {
        return spellManager;
    }
}
