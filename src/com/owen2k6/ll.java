package com.owen2k6;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ll extends JavaPlugin implements Listener {
    private static ll plugin;
    private PLConfig config;
    private Logger log;


    @Override
    public void onDisable() {
        getServer().getLogger().log(Level.INFO, "shutting down login logger");
    }

    @Override
    public void onEnable() {
        plugin = this;
        log = Bukkit.getServer().getLogger();
        config = new PLConfig();
        logInfo(Level.INFO, "Starting up login logger.");
        if (Objects.equals(config.getStringOption("webhook.url"), "CHANGEME") || config.getStringOption("webhook.url").isEmpty()) {
            logInfo(Level.WARNING, "Please enter a URL for us to push webhooks to.");
            logInfo(Level.WARNING, "Edit in plugins/LL/config.yml");
            logInfo(Level.WARNING, "Its always best to configure your plugins!");
            Bukkit.getServer().getPluginManager().disablePlugin(plugin);
        }
        getServer().getPluginManager().registerEvents(new ll(), this);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        try {
            if (config == null) config = new PLConfig();
            DiscordWebhook webhook = new DiscordWebhook(config.getStringOption("webhook.url"));
            String username = config.getStringOption("webhook.username", "Login Logger");
            webhook.setUsername(username);
            webhook.setTts(false);
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle("Player Login Initialised.")
                    .setDescription("Player " + event.getPlayer().getName() + " initiated the login.")
                    .setColor(Color.CYAN)
                    .addField("Result", event.getResult().toString(), true)
                    .addField("Kick Message", event.getKickMessage(), true)
                    .addField("IP", event.getAddress().toString(), true)
                    .setFooter(config.getStringOption("webhook.footer", "Owen2k6 Login logger ;)"), null));
            webhook.execute(); //Handle exception
            logInfo(Level.INFO, "Login logged to webhook.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logInfo(Level level, String s) {
        log.log(level, "[Login Logger] " + s);
    }
}
