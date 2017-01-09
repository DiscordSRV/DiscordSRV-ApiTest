package github.scarsz.discordsrv.apitest;

import github.scarsz.discordsrv.DiscordSRV.DiscordSRV;
import github.scarsz.discordsrv.DiscordSRV.api.DiscordSRVListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    private DiscordSRV discordSRV = (DiscordSRV) Bukkit.getPluginManager().getPlugin("DiscordSRV");
    private DiscordSRVListener apiTestDiscordSRVListener = new ApiTestDiscordSRVListener();

    @Override
    public void onEnable() {
        discordSRV.addListener(apiTestDiscordSRVListener);
        // see everything in "discordSRV" for what you can do
    }

    @Override
    public void onDisable() {
        discordSRV.removeListener(apiTestDiscordSRVListener);
    }

}