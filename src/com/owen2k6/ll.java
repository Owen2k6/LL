package com.owen2k6;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;

public class ll  extends JavaPlugin implements Listener {

    @Override
    public void onDisable() {
        getServer().getLogger().log(Level.INFO, "shutting down login logger");
    }

    @Override
    public void onEnable() {
        getServer().getLogger().log(Level.INFO, "Starting up login logger");
        getServer().getPluginManager().registerEvents(new ll(), this);

        }


    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event){
        try {
            DiscordWebhook webhook = new DiscordWebhook("YOUR WEBHOOK GOES HERE!");
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
        }catch (Exception e){
            getServer().getLogger().log(Level.SEVERE, "Fuck man it broke. Anyways here is what fucked up: " + e);
        }
    }
}
