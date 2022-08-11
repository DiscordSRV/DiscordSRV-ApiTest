package com.discordsrv.apitest;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.commands.PluginSlashCommand;
import github.scarsz.discordsrv.api.commands.SlashCommand;
import github.scarsz.discordsrv.api.commands.SlashCommandProvider;
import github.scarsz.discordsrv.dependencies.jda.api.entities.User;
import github.scarsz.discordsrv.dependencies.jda.api.events.interaction.SlashCommandEvent;
import github.scarsz.discordsrv.dependencies.jda.api.interactions.commands.build.CommandData;
import github.scarsz.discordsrv.dependencies.jda.api.interactions.commands.build.SubcommandData;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Plugin extends JavaPlugin implements Listener, SlashCommandProvider {

    private final DiscordSRVListener discordsrvListener = new DiscordSRVListener(this);

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        DiscordSRV.api.subscribe(discordsrvListener);
    }
    @Override
    public void onDisable() {
        DiscordSRV.api.unsubscribe(discordsrvListener);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        String discordId = DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(event.getPlayer().getUniqueId());
        if (discordId == null) {
            event.getPlayer().sendMessage(ChatColor.RED + "You are not linked");
            return;
        }

        User user = DiscordUtil.getJda().getUserById(discordId);
        if (user == null) {
            event.getPlayer().sendMessage(ChatColor.YELLOW + "Couldn't find the user you're linked to");
            return;
        }

        event.getPlayer().sendMessage(ChatColor.GREEN + "You're linked to " + user.getAsTag());
    }

    @Override
    public Set<PluginSlashCommand> getSlashCommands() {
        return new HashSet<>(Arrays.asList(
                // ping pong
                new PluginSlashCommand(this, new CommandData("ping", "A classic match of ping pong")),

                // bests
                new PluginSlashCommand(this, new CommandData("best", "Best _____")
                        .addSubcommands(new SubcommandData("friend", "Best friend"))
                        .addSubcommands(new SubcommandData("plugin", "Best plugin"))
                ),

                // linked account
                new PluginSlashCommand(this, new CommandData("linked", "Check the Minecraft account you have linked with your Discord")),

                // most important command
                new PluginSlashCommand(this, new CommandData("life", "What is the meaning of life?"))
        ));
    }

    @SlashCommand(path = "ping")
    public void pingCommand(SlashCommandEvent event) {
        event.reply("Pong!").queue();
    }

    @SlashCommand(path = "best/plugin")
    public void bestPlugin(SlashCommandEvent event) {
        event.reply("DiscordSRV!").queue();
    }
    @SlashCommand(path = "best/friend")
    public void bestFriend(SlashCommandEvent event) {
        event.reply("Dogs!").queue();
    }

    @SlashCommand(path = "linked", deferReply = true, deferEphemeral = true)
    public void linkedCommand(SlashCommandEvent event) {
        UUID uuid = DiscordSRV.getPlugin().getAccountLinkManager().getUuid(event.getUser().getId());
        if (uuid != null) {
            event.getHook().sendMessage("✅ Your account is linked to " + Bukkit.getOfflinePlayer(uuid).getName() + ".").queue();
        } else {
            event.getHook().sendMessage("❌ Your account is not linked.").queue();
        }
    }

    @SlashCommand(path = "life", deferReply = true)
    public void meaningOfLife(SlashCommandEvent event) {
        event.getHook().sendMessage("42").queueAfter(5, TimeUnit.SECONDS);
    }

}
