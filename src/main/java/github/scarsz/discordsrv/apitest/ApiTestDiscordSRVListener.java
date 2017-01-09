package github.scarsz.discordsrv.apitest;

import github.scarsz.discordsrv.DiscordSRV.api.DiscordSRVListener;
import github.scarsz.discordsrv.DiscordSRV.api.events.DiscordGuildChatMessageEvent;
import github.scarsz.discordsrv.DiscordSRV.api.events.GamePlayerAchievementRewardedEvent;
import org.bukkit.Bukkit;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @on 1/8/2017
 * @at 5:55 PM
 */
public class ApiTestDiscordSRVListener extends DiscordSRVListener {

    public ApiTestDiscordSRVListener() {
        super("API Testing DiscordSRV Listener");
    }

    @Override
    public void onDiscordGuildChatMessage(DiscordGuildChatMessageEvent event) {
        Bukkit.getLogger().info("Received a chat message on Discord: " + event.getMessage());
    }

    @Override
    public void onGamePlayerAchievementRewarded(GamePlayerAchievementRewardedEvent event) {
        // nobody needs to see achievements earned in the nether
        if (event.getWorld().endsWith("_nether")) {
            event.setCanceled(true);
        }
    }

}
