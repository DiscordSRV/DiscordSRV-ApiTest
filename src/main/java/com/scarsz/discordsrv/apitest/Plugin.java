package com.scarsz.discordsrv.apitest;

import com.scarsz.discordsrv.api.DiscordSRVAPI;
import com.scarsz.discordsrv.api.DiscordSRVListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    private DiscordSRVListener listener = new DiscordListener(this);

    @Override
    public void onEnable() {
        DiscordSRVAPI.addListener(listener);
    }

    @Override
    public void onDisable() {
        DiscordSRVAPI.removeListener(listener);
    }

}