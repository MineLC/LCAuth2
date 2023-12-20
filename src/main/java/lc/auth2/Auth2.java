package lc.auth2;

import lc.core2.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;

public final class Auth2 extends JavaPlugin {
    private Connection connection;
    private static Auth2 instance;
    public int port;
    public String host;
    public String user;
    public String database;
    public String authtable;
    public String pass;
    public boolean dbexist;

    @Override
    public void onEnable() {
        instance = this;
        LCConfig lcConfig = new LCConfig(this);
        lcConfig.reloadConfig();
        Util.message(Bukkit.getConsoleSender(), "&a[Auth2] &aConectando con la Base de datos:");
        mysqlupdate(lcConfig.getConfig().getConfigurationSection("MySQL"));
        getServer().getPluginManager().registerEvents(new PlayersListener(), this);

    }

    public static Auth2 get() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void mysqlupdate(ConfigurationSection c) {
        this.host = c.getString("Host");
        this.user = c.getString("User");
        this.port = c.getInt("Port");
        this.database = c.getString("Database");
        this.authtable = c.getString("Table");
        this.pass = c.getString("Pass");
        try {
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed())
                    return;
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port, this.user, this.pass);
                if(con != null){
                    Util.message(Bukkit.getConsoleSender(), "&a¡Base de datos encontrada! Conectando...");
                    ResultSet rs = con.getMetaData().getCatalogs();
                    while(rs.next()){
                        String db = rs.getString(1);
                        if(this.database.equals(db)){
                            dbexist = true;
                            Util.message(Bukkit.getConsoleSender(), "&aConectado con la base de datos éxitosamente.");
                            setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.user, this.pass));
                            break;
                        }
                    }
                    con.close();
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            getLogger().severe("No se ha podido conectarse con la base de datos. Cerrando servidor...");
            Bukkit.shutdown();

        }
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.user, this.pass);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return con;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
