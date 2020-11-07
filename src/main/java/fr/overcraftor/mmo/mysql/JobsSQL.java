package fr.overcraftor.mmo.mysql;

import fr.overcraftor.mmo.utils.jobs.JobsNames;
import fr.overcraftor.mmo.Main;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JobsSQL {

    public static void createTable(Main main){
        try {
            PreparedStatement sts = main.getSql().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS jobs(" +
                                                                "UUID VARCHAR(255) NOT NULL," +
                                                                "minerExp INT NOT NULL," +
                                                                "farmerExp INT NOT NULL," +
                                                                "woodCutterExp INT NOT NULL," +
                                                                "enchanterExp INT NOT NULL," +
                                                                "blacksmithExp INT NOT NULL)");
            sts.execute();
            sts.close();
            main.getLogger().info("La table pour les metiers est correctement cree (sauf si elle l'etait deja)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkTableContainUUID(UUID uuid){
        try {
            PreparedStatement isIn = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM jobs WHERE UUID = ?");
            isIn.setString(1, uuid.toString());
            ResultSet rs = isIn.executeQuery();

            if(!rs.next()){
                PreparedStatement insert = Main.getInstance().getSql().getConnection().prepareStatement("INSERT INTO jobs(UUID,minerExp,farmerExp,woodCutterExp,enchanterExp,blacksmithExp) VALUES(?,?,?,?,?,?)");
                insert.setString(1, uuid.toString());
                for(int i = 2; i < 7; i++){
                    insert.setInt(i, 0);
                }

                insert.execute();
                insert.close();

                Main.getInstance().getLogger().info("{job} Le joueur " + Bukkit.getOfflinePlayer(uuid).getName() + " a ete ajoute dans la base de donnee");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setXp(JobsNames job, UUID uuid, int xp){
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("UPDATE jobs SET " + job.toMysql() + " = ? WHERE UUID = ?");
            sts.setInt(1, xp);
            sts.setString(2, uuid.toString());
            sts.execute();
            sts.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<JobsNames, Integer> getXp(UUID uuid){
        final HashMap<JobsNames, Integer> map = new HashMap<>();
        try {
            PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("SELECT * FROM jobs WHERE UUID = ?");
            sts.setString(1, uuid.toString());
            ResultSet rs = sts.executeQuery();

            if(!rs.next())
                return null;

            for(JobsNames job : JobsNames.values()){
                map.put(job, rs.getInt(job.toMysql()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }

    public static void setAllXp(HashMap<JobsNames, Integer> map, UUID uuid){
        try {
            for(Map.Entry<JobsNames, Integer> job : map.entrySet()){
                PreparedStatement sts = Main.getInstance().getSql().getConnection().prepareStatement("UPDATE jobs SET " + job.getKey().toMysql() + " = ? WHERE UUID = ?");
                sts.setInt(1, job.getValue());
                sts.setString(2, uuid.toString());
                sts.execute();
                sts.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}