package dev.zerek.featherdeathmessage;

import dev.zerek.featherdeathmessage.commands.LastDeathCommand;
import dev.zerek.featherdeathmessage.listeners.PlayerDeathListener;
import dev.zerek.featherdeathmessage.managers.DatabaseManager;
import dev.zerek.featherdeathmessage.managers.DeathManager;
import org.bukkit.plugin.java.JavaPlugin;


public final class FeatherDeathMessage extends JavaPlugin {

    private DatabaseManager databaseManager;

    private DeathManager deathManager;


    @Override
    public void onEnable() {

        saveDefaultConfig();

        databaseManager = new DatabaseManager(this);

        deathManager = new DeathManager(this);

        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);

        this.getCommand("lastdeath").setExecutor(new LastDeathCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public DeathManager getDeathManager() {
        return deathManager;
    }
}
