package fr.overcraftor.mmo.mysql;

import fr.overcraftor.mmo.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

    private Connection connection;
    private final String urlbase,host,database,user,pass;

    public SQLConnection(String urlbase, String host, String database, String user, String pass) {
        this.urlbase = urlbase;
        this.host = host;
        this.database = database;
        this.user = user;
        this.pass = pass;
    }

    public void connect() throws SQLException{
        if(!isConnected()) {
            long ping = System.currentTimeMillis();
            connection = DriverManager.getConnection(urlbase + host + "/" + database, user, pass);
            ping = System.currentTimeMillis() - ping;
            Main.getInstance().getLogger().info("MYSQL: CONNECTED (ping: " + ping  + "ms)");
        }
    }

    public void disconnect() {
        if(isConnected()) {
            try {
                connection.close();
                Main.getInstance().getLogger().info("MYSQL: DISCONNECTED");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }
}
