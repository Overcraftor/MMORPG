package fr.overcraftor.mmo.mysql;

import fr.overcraftor.mmo.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AptSQL {

    public static void createTable(){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS aptitude(" +
                    "UUID VARCHAR(255) NOT NULL," +
                    "apt INT NOT NULL," +
                    "strength INT NOT NULL," +
                    "health INT NOT NULL," +
                    "mana INT NOT NULL," +
                    "speed INT NOT NULL)");
            sts.execute();
            sts.close();
            Main.getInstance().getLogger().info("aptitute table loaded successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isInTable(UUID uuid){
        try{
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM aptitude WHERE UUID = ?");
            sts.setString(1, uuid.toString());

            return sts.executeQuery().next();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void insert(UUID uuid){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("INSERT INTO aptitude(UUID,apt, strength, health, mana, speed) VALUES(?,?,?,?,?,?)");
            sts.setString(1, uuid.toString());
            sts.setInt(2, 0);
            sts.setInt(3, 0);
            sts.setInt(4, 0);
            sts.setInt(5, 0);
            sts.setInt(6, 0);
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int[] getAllApt(UUID uuid){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM aptitude WHERE UUID = ?");
            sts.setString(1, uuid.toString());
            ResultSet rs = sts.executeQuery();

            if(rs.next())
                return new int[]{rs.getInt("apt"), rs.getInt("strength"), rs.getInt("health"), rs.getInt("mana"), rs.getInt("speed")};
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addApt(UUID uuid, int apt){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("UPDATE aptitude SET apt = apt + ? WHERE UUID = ?");
            sts.setInt(1, apt);
            sts.setString(2, uuid.toString());
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setApt(UUID uuid, int apt){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("UPDATE aptitude SET apt = ? WHERE UUID = ?");
            sts.setInt(1, apt);
            sts.setString(2, uuid.toString());
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setAllApt(UUID uuid, int... apt){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("UPDATE aptitude SET apt = ?, strength = ?, health = ?, mana = ?, speed = ? WHERE UUID = ?");
            sts.setInt(1, apt[0]);
            sts.setInt(2, apt[1]);
            sts.setInt(3, apt[2]);
            sts.setInt(4, apt[3]);
            sts.setInt(5, apt[4]);
            sts.setString(6, uuid.toString());
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
