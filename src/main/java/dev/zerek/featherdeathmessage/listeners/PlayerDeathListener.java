package dev.zerek.featherdeathmessage.listeners;

import dev.zerek.featherdeathmessage.FeatherDeathMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final FeatherDeathMessage plugin;

    String deathNoticeMessage;
    String deathCoordsMessage;

    public PlayerDeathListener(FeatherDeathMessage plugin) {

        this.plugin = plugin;
        this.deathNoticeMessage = this.plugin.getConfig().getString("death-notice");
        this.deathCoordsMessage = this.plugin.getConfig().getString("death-coords");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){

        if (event.getPlayer().hasPermission("feather.deathmessage.coords")) {

            Player player = event.getEntity();
            String x = String.valueOf(player.getLocation().getBlockX());
            String y = String.valueOf(player.getLocation().getBlockY());
            String z = String.valueOf(player.getLocation().getBlockZ());
            String world = player.getWorld().getName();
            Component coordsMessage = MiniMessage.miniMessage().deserialize(deathCoordsMessage, Placeholder.unparsed("world", world), Placeholder.unparsed("x", x), Placeholder.unparsed("y", y), Placeholder.unparsed("z", z));
            plugin.getDeathManager().storeDeath(event.getPlayer(), GsonComponentSerializer.gson().serialize(coordsMessage));

            player.sendMessage(MiniMessage.miniMessage().deserialize(deathNoticeMessage));
        }

        if (event.getEntity().hasPermission("feather.deathmessage.silent")) event.deathMessage(null);
        else event.deathMessage(Component.text("• ").color(TextColor.fromHexString("#ffffff")).append(event.deathMessage().color(TextColor.fromHexString("#b98863"))));
    }
}
