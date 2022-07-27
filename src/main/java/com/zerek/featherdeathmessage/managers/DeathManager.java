package com.zerek.featherdeathmessage.managers;

import com.zerek.featherdeathmessage.FeatherDeathMessage;
import com.zerek.featherdeathmessage.data.Death;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.OfflinePlayer;

import java.sql.Date;
import java.sql.Timestamp;

public class DeathManager {

    private final FeatherDeathMessage plugin;

    public DeathManager(FeatherDeathMessage plugin) {
        this.plugin = plugin;
    }

    public boolean isPlayerDeath (OfflinePlayer offlinePlayer){
        return Death.exists(offlinePlayer.getUniqueId().toString());
    }

    public void storeDeath (OfflinePlayer offlinePlayer, String location){
        Death playerDeath = new Death().set("mojang_uuid", offlinePlayer.getUniqueId().toString(), "location", location, "updated_at", System.currentTimeMillis());
        if (isPlayerDeath(offlinePlayer)) playerDeath.saveIt();
        else playerDeath.insert();
    }

    public String getDeathLocation (OfflinePlayer offlinePlayer){
        Death death = Death.findById(offlinePlayer.getUniqueId().toString());
        return (String) death.get("location");
    }
}
