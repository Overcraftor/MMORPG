package fr.overcraftor.mmo.mysql;

import fr.overcraftor.mmo.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ManaSQL {

    public static void createTable(){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS mana(" +
                    "UUID VARCHAR(255) NOT NULL," +
                    "MaxManaBonus INT NOT NULL)");
            sts.execute();
            sts.close();
            Main.getInstance().getLogger().info("mana table loaded successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isInTable(UUID uuid){
        try{
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM mana WHERE UUID = ?");
            sts.setString(1, uuid.toString());

            return sts.executeQuery().next();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void insert(UUID uuid){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("INSERT INTO mana(UUID,MaxManaBonus) VALUES(?,?)");
            sts.setString(1, uuid.toString());
            sts.setInt(2, 0);
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setManaMaxBonus(UUID uuid, int mana){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("UPDATE mana SET MaxManaBonus = ? WHERE UUID = ?");
            sts.setInt(1, mana);
            sts.setString(2, uuid.toString());
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addManaMaxBonus(UUID uuid, int mana){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("UPDATE mana SET MaxManaBonus = MaxManaBonus + ? WHERE UUID = ?");
            sts.setInt(1, mana);
            sts.setString(2, uuid.toString());
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getMaxManaBonus(UUID uuid){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM mana WHERE UUID = ?");
            sts.setString(1, uuid.toString());
            ResultSet rs = sts.executeQuery();

            if(rs.next())
                return rs.getInt("MaxManaBonus");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
