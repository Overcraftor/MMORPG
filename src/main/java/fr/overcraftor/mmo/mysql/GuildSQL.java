package fr.overcraftor.mmo.mysql;

import fr.overcraftor.mmo.Main;
import fr.overcraftor.mmo.utils.guilds.Guild;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GuildSQL {

    public static final String NO_GUILD = "Aucune guilde";

    public static void createTable(){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS guilds(" +
                    "UUIDOwner VARCHAR(255) NOT NULL," +
                    "UUIDPlayers VARCHAR(255) NOT NULL," +
                    "tag VARCHAR(3) NOT NULL," +
                    "name VARCHAR(10) NOT NULL)");
            sts.execute();
            sts.close();
            Main.getInstance().getLogger().info("guild table loaded successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addGuild(String guildName, String guildTag, UUID guildOwner){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("INSERT INTO guilds(UUIDOwner,UUIDPlayers,tag,name) VALUES(?,?,?,?)");
            sts.setString(1, guildOwner.toString());
            sts.setString(2, guildOwner.toString());
            sts.setString(3, guildTag);
            sts.setString(4, guildName);
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean tagExists(String guildTag){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM guilds WHERE tag = ?");
            sts.setString(1, guildTag);

            return sts.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean exists(String guildName){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM guilds WHERE name = ?");
            sts.setString(1, guildName);

            return sts.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean hasGuild(UUID uuid){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM guilds");
            ResultSet rs = sts.executeQuery();

            while(rs.next()){
                List<String> playersUUID = Arrays.asList(rs.getString("UUIDPlayers").split(","));

                if(playersUUID.contains(uuid.toString()))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getGuildTag(UUID uuid){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM guilds");
            ResultSet rs = sts.executeQuery();

            while(rs.next()){
                List<String> playersUUID = Arrays.asList(rs.getString("UUIDPlayers").split(","));

                if(playersUUID.contains(uuid.toString()))
                    return rs.getString("tag");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getGuildName(UUID uuid){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM guilds");
            ResultSet rs = sts.executeQuery();

            while(rs.next()){
                List<String> playersUUID = Arrays.asList(rs.getString("UUIDPlayers").split(","));

                if(playersUUID.contains(uuid.toString()))
                    return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return NO_GUILD;
    }

    public static List<Guild> getAllGuilds(){
        final ArrayList<Guild> guilds = new ArrayList<>();
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM guilds");
            ResultSet rs = sts.executeQuery();

            while(rs.next()){
                final ArrayList<UUID> players = new ArrayList<>();
                for(String p : rs.getString("UUIDPlayers").split(",")){
                    players.add(UUID.fromString(p));
                }

                guilds.add(new Guild(rs.getString("name"), rs.getString("tag"),players ,UUID.fromString(rs.getString("UUIDOwner"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guilds;
    }

    private static List<UUID> getPlayers(String guildName){
        final ArrayList<UUID> players = new ArrayList<>();
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM guilds WHERE name = ?");
            sts.setString(1, guildName);
            ResultSet rs = sts.executeQuery();

            if(rs.next()){
                final String playersStr = rs.getString("UUIDPlayers");

                if(playersStr.contains(",")){
                    for(String p : playersStr.split(",")){
                        if(p == null || p.equals("")) continue;
                        players.add(UUID.fromString(p));
                    }
                }else{
                    players.add(UUID.fromString(playersStr));
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return players;
    }

    public static void addPlayer(String guildName, UUID pUUID){
        try {
            List<UUID> players = getPlayers(guildName);
            final StringBuilder playersString = new StringBuilder();
            for(UUID p : players)
                playersString.append(",").append(p);

            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("UPDATE guilds SET UUIDPlayers = ? WHERE name = ?");
            sts.setString(1, playersString.toString() + "," + pUUID);
            sts.setString(2, guildName);
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removePlayer(UUID pUUID){
        try {
            final String guildName = getGuildName(pUUID);
            final List<UUID> players = getPlayers(guildName);
            final StringBuilder playersString = new StringBuilder();
            for(UUID p : players) {
                if(!p.equals(pUUID)){
                    playersString.append(p).append(",");
                }
            }

            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("UPDATE guilds SET UUIDPlayers = ? WHERE name = ?");
            sts.setString(1, playersString.toString());
            sts.setString(2, guildName);
            sts.execute();
            sts.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isOwner(UUID uuid){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM guilds");
            ResultSet rs = sts.executeQuery();

            while(rs.next()){
                if(rs.getString("UUIDOwner").equals(uuid.toString()))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void delete(UUID uuid){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("DELETE FROM guilds WHERE UUIDOwner = ?");
            sts.setString(1, uuid.toString());
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setLeader(UUID uuid, String guildName){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("UPDATE guilds SET UUIDOwner = ? WHERE name = ?");
            sts.setString(1, uuid.toString());
            sts.setString(2, guildName);
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setTag(String tag, String guildName){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("UPDATE guilds SET tag = ? WHERE name = ?");
            sts.setString(1, tag);
            sts.setString(2, guildName);
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getPlayersName(String guildName){
        final List<UUID> playersUUID = getPlayers(guildName);
        final List<String> players = new ArrayList<>();

        playersUUID.forEach(uuid ->{
            players.add(Bukkit.getOfflinePlayer(uuid).getName());
        });
        return players;
    }

    public static String getOwner(String guildName){

        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT UUIDOwner FROM guilds WHERE name = ?");
            sts.setString(1, guildName);

            ResultSet rs = sts.executeQuery();
            if(rs.next()){
                final UUID UUIDOwner = UUID.fromString(rs.getString("UUIDOwner"));
                return Bukkit.getOfflinePlayer(UUIDOwner).getName();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean areInSameGuild(UUID p1, UUID p2){
        for(Guild guild : getAllGuilds()){
            if(guild.getPlayers().contains(p1) && guild.getPlayers().contains(p2))
                return true;
        };
        return false;
    }
}
