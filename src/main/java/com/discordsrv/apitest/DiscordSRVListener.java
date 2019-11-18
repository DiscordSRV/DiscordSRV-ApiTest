package com.discordsrv.apitest;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.ListenerPriority;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.*;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Member;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.dependencies.jda.api.entities.User;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.Bukkit;

public class DiscordSRVListener {

    @Subscribe(priority = ListenerPriority.MONITOR)
    public void discordMessageReceived(DiscordGuildMessageReceivedEvent event) {
        // Example of logging a message sent in Discord

        Bukkit.getLogger().info("Received a chat message on Discord: " + event.getMessage());
    }

    @Subscribe(priority = ListenerPriority.MONITOR)
    public void aMessageWasSentInADiscordGuildByTheBot(DiscordGuildMessageSentEvent event) {
        // Example of logging a message sent in Minecraft (being sent to Discord)

        Bukkit.getLogger().info("A message was sent to Discord: " + event.getMessage());
    }

    @Subscribe
    public void accountsLinked(AccountLinkedEvent event) {
        // Example of broadcasting a message when a new account link has been made

        Bukkit.broadcastMessage(event.getPlayer().getName() + " just linked their MC account to their Discord user " + event.getUser() + "!");
    }

    @Subscribe
    public void accountUnlinked(AccountUnlinkedEvent event) {
        // Example of DM:ing user on unlink
        User user = DiscordUtil.getJda().getUserById(event.getDiscordId());

        // will be null if the bot isn't in a Discord server with the user (eg. they left the main Discord server)
        if (user != null) {

            // opens/retrieves the private channel for the user & sends a message to it (if retrieving the private channel was successful)
            user.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("Your account has been unlined").queue());
        }



        // Example of sending a message to a channel called "unlinks" (defined in the config.yml using the Channels option) when a user unlinks
        TextChannel textChannel = DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName("unlinks");

        // null if the channel isn't specified in the config.yml
        if (textChannel != null) {
            textChannel.sendMessage(event.getPlayer().getName() + " (" + event.getPlayer().getUniqueId() + ") has unlinked their associated Discord account: "
                    + (event.getDiscordUser() != null ? event.getDiscordUser().getName() : "<not available>") + " (" + event.getDiscordId() + ")").queue();
        } else {
            Bukkit.getLogger().warning("Channel called \"unlinks\" could not be found in the DiscordSRV configuration");
        }
    }

    @Subscribe
    public void discordMessageProcessed(DiscordGuildMessagePostProcessEvent event) {
        // Example of modifying a Discord -> Minecraft message

        event.setProcessedMessage(event.getProcessedMessage().replace("cat", "dog")); // dogs are superior to cats, obviously
    }

}
