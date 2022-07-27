package com.zerek.featherdeathmessage;

import com.zerek.featherdeathmessage.commands.LastDeathCommand;
import com.zerek.featherdeathmessage.listeners.PlayerDeathListener;
import com.zerek.featherdeathmessage.managers.DatabaseManager;
import com.zerek.featherdeathmessage.managers.DeathManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class FeatherDeathMessage extends JavaPlugin {

    private static Logger logger;
    private DatabaseManager databaseManager;
    private DeathManager deathManager;


    @Override
    public void onEnable() {
        saveDefaultConfig();
        logger = Logger.getLogger("Minecraft");
        databaseManager = new DatabaseManager(this);
        deathManager = new DeathManager(this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        this.getCommand("lastdeath").setExecutor(new LastDeathCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Logger getLog(){
        return logger;
    }
    public DeathManager getDeathManager() {
        return deathManager;
    }
}
