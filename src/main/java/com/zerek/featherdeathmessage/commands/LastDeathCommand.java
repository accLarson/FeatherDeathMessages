package com.zerek.featherdeathmessage.commands;

import com.zerek.featherdeathmessage.FeatherDeathMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.sql.Timestamp;

public class LastDeathCommand implements CommandExecutor {

    private final FeatherDeathMessage plugin;

    public LastDeathCommand(FeatherDeathMessage plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player && sender.hasPermission("feather.deathmessage.coords") && plugin.getDeathManager().isPlayerDeath((OfflinePlayer) sender)){
            Component message = GsonComponentSerializer.gson().deserialize(plugin.getDeathManager().getDeathLocation((OfflinePlayer) sender));
//            Date date = plugin.getDeathManager().getDeathUpdateAt((OfflinePlayer) sender);
            sender.sendMessage(message);
            return true;
        }
        else sender.sendMessage("No death on record");
        return false;
    }
}
