package fr.overcraftor.mmo.mysql;

import fr.overcraftor.mmo.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class GeneralXpSQL {

    public static void createTable(){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS generalxp(" +
                    "UUID VARCHAR(255) NOT NULL," +
                    "xp INT NOT NULL)");
            sts.execute();
            sts.close();
            Main.getInstance().getLogger().info("general xp table loaded successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isInTable(UUID uuid){
        try{
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM generalxp WHERE UUID = ?");
            sts.setString(1, uuid.toString());

            return sts.executeQuery().next();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void insert(UUID uuid, int xp){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("INSERT INTO generalxp(UUID,xp) VALUES(?,?)");
            sts.setString(1, uuid.toString());
            sts.setInt(2, xp);
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setXp(UUID uuid, int xp){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("UPDATE generalxp SET xp = ? WHERE UUID = ?");
            sts.setInt(1, xp);
            sts.setString(2, uuid.toString());
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getXp(UUID uuid){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM generalxp WHERE UUID = ?");
            sts.setString(1, uuid.toString());
            ResultSet rs = sts.executeQuery();

            if(rs.next())
                return rs.getInt("xp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}