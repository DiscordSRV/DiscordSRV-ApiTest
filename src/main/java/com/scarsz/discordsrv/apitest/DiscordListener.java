package com.scarsz.discordsrv.apitest;

import com.scarsz.discordsrv.api.DiscordSRVListener;
import com.scarsz.discordsrv.api.events.ProcessChatEvent;
import com.scarsz.discordsrv.jda.events.Event;
import com.scarsz.discordsrv.jda.events.message.MessageReceivedEvent;

class DiscordListener extends DiscordSRVListener {

    private Plugin plugin;
    DiscordListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onDiscordMessageReceived(MessageReceivedEvent event) {
        plugin.getLogger().info("Message received from Discord: " + event.getMessage().getContent());
    }

    @Override
    public void onRawDiscordEventReceived(Event event) {
        plugin.getLogger().info("Raw event received from Discord: " + event);
    }

    @Override
    public void onProcessChat(ProcessChatEvent event) {
        plugin.getLogger().info("A message was processed for delivery to Discord: " + event.message);
    }

}