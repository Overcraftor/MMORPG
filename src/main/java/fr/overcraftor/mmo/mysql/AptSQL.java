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
                    "apt INT NOT NULL)");
            sts.execute();
            sts.close();
            Main.getInstance().getLogger().info("La table pour l'aptitude est correctement cree (sauf si elle l'etait deja)");
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

    public static void insert(UUID uuid, int apt){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("INSERT INTO aptitude(UUID,apt) VALUES(?,?)");
            sts.setString(1, uuid.toString());
            sts.setInt(2, apt);
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setApt(UUID uuid, int xp){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("UPDATE aptitude SET apt = ? WHERE UUID = ?");
            sts.setInt(1, xp);
            sts.setString(2, uuid.toString());
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getApt(UUID uuid){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM aptitude WHERE UUID = ?");
            sts.setString(1, uuid.toString());
            ResultSet rs = sts.executeQuery();

            if(rs.next())
                return rs.getInt("apt");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void addApt(UUID uuid, int apt){
        setApt(uuid, getApt(uuid) + apt);
    }
}
