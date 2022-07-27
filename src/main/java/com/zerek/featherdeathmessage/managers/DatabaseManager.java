package com.zerek.featherdeathmessage.managers;

import com.zerek.featherdeathmessage.FeatherDeathMessage;
import org.javalite.activejdbc.Base;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    private static Connection connection;
    private final FeatherDeathMessage plugin;
    private File file;

    public DatabaseManager(FeatherDeathMessage plugin) {
        this.plugin = plugin;
        this.initConnection();
        this.initTables();
    }

    public Connection getConnection() {
        try {
            if(connection.isClosed()) {
                this.initConnection();
            }
        } catch (SQLException e) {
            plugin.getLog().severe("[FeatherDeathMessages] Unable to receive connection.");
        }
        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                Base.close();
                connection.close();
            } catch (SQLException e) {
                plugin.getLog().severe("[FeatherDeathMessages] Unable to close DatabaseManager connection.");
            }
        }
    }

    private void initConnection() {
        File folder = this.plugin.getDataFolder();
        if(!folder.exists()) {
            folder.mkdir();
        }
        this.file = new File(folder.getAbsolutePath() + File.separator +  "FeatherDeathMessages.db");
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.file.getAbsolutePath());
            Base.attach(this.connection);
        } catch (SQLException e) {
            plugin.getLog().severe("[FeatherDeathMessages] Unable to initialize DatabaseManager connection.");
        }
    }

    private boolean existsTable(String table) {
        try {
            if(!connection.isClosed()) {
                ResultSet rs = connection.getMetaData().getTables(null, null, table, null);
                return rs.next();
            } else {
                return false;
            }
        } catch (SQLException e) {
            plugin.getLog().severe("[FeatherDeathMessages] Unable to query table metadata.");
            return false;
        }
    }

    private void initTables() {
        if(!this.existsTable("DEATHS")) {
            plugin.getLog().info("[FeatherDeathMessages] Creating DEATHS table.");
            String query = "CREATE TABLE IF NOT EXISTS `DEATHS` ("
                    + " `mojang_uuid`               VARCHAR(255) PRIMARY KEY NOT NULL, "
                    + " `updated_at`                DATETIME, "
                    + " `location`                  JSON );";
            try {
                if(!connection.isClosed()) {
                    connection.createStatement().execute(query);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                plugin.getLog().severe("[FeatherDeathMessages] Unable to create DEATHS table.");
            }
        }
    }
}
