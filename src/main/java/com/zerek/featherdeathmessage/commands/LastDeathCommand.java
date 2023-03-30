package com.zerek.featherdeathmessage.commands;

import com.zerek.featherdeathmessage.FeatherDeathMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.TimeZone;

public class LastDeathCommand implements CommandExecutor {

    private final FeatherDeathMessage plugin;

    public LastDeathCommand(FeatherDeathMessage plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player && sender.hasPermission("feather.deathmessage.coords") && plugin.getDeathManager().isPlayerDeath((OfflinePlayer) sender)){

            Component locationMessage = GsonComponentSerializer.gson().deserialize(plugin.getDeathManager().getDeathLocation((OfflinePlayer) sender));

            DateFormat dateFormat = DateFormat.getDateTimeInstance();

            dateFormat.setTimeZone(TimeZone.getTimeZone("Canada/Eastern"));

            Component dateMessage = Component.text(dateFormat.format(plugin.getDeathManager().getDeathDate((OfflinePlayer) sender)));

            sender.sendMessage(locationMessage.append(Component.text(" on: ")).append(dateMessage).append(Component.text("\n/servertime").color(TextColor.fromCSSHexString("#5c5c5c"))));

            return true;
        }

        else sender.sendMessage("No death on record");

        return false;
    }
}
