package com.zerek.featherdeathmessage.listeners;

import com.zerek.featherdeathmessage.FeatherDeathMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final FeatherDeathMessage plugin;

    String deathCoordsMessage;

    public PlayerDeathListener(FeatherDeathMessage plugin) {
        this.plugin = plugin;
        deathCoordsMessage = plugin.getConfig().getString("death-coords");
    }

    @EventHandler
    public void onEntityDamageByEntity(PlayerDeathEvent event){

        if (event.getPlayer().hasPermission("feather.deathmessage.coords")) {
            Player player = event.getEntity();
            String x = String.valueOf(player.getLocation().getBlockX());
            String y = String.valueOf(player.getLocation().getBlockY());
            String z = String.valueOf(player.getLocation().getBlockZ());
            String world = player.getWorld().getName();
            Component message = MiniMessage.miniMessage().deserialize(deathCoordsMessage, Placeholder.unparsed("world", event.getPlayer().getWorld().getName()), Placeholder.unparsed("x", x), Placeholder.unparsed("y", y), Placeholder.unparsed("z", z));
            player.sendMessage(message);
            plugin.getDeathManager().storeDeath(event.getPlayer(), GsonComponentSerializer.gson().serialize(message));
        }

        if (event.getEntity().hasPermission("feather.deathmessage.silent")) event.deathMessage(null);
        else event.deathMessage(Component.text("• ").color(TextColor.fromHexString("#ffffff")).append(event.deathMessage().color(TextColor.fromHexString("#b98863"))));
    }
}
