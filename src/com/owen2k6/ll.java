package com.owen2k6;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

public class ll  extends JavaPlugin implements Listener {
    private static ll plugin;


    @Override
    public void onDisable() {
        getServer().getLogger().log(Level.INFO, "shutting down login logger");
    }

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getLogger().log(Level.INFO, "Starting up login logger");
        getServer().getPluginManager().registerEvents(new ll(), this);

        }


    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event){
        try {
            DiscordWebhook webhook = new DiscordWebhook("URL GO HERE NOW!");
            webhook.setUsername("Login Logger");
            webhook.setTts(false);
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle("Player Login Initialised.")
                    .setDescription("Player " + event.getPlayer().getName() + " initiated the login.")
                    .setColor(Color.CYAN)
                    .addField("Result", event.getResult().toString(), true)
                    .addField("Kick Message", event.getKickMessage(), true)
                    .addField("IP", event.getAddress().toString(), true)
                    .setFooter("Owen2k6 Login logger ;)", null));
            webhook.execute(); //Handle exception
            getServer().getLogger().log(Level.INFO, "Login logged to webhook.");
        }catch (Exception e){
            getServer().getLogger().log(Level.SEVERE, "Fuck man it broke. Anyways here is what fucked up: " + e);
        }
    }
}
