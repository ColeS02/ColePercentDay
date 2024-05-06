package com.unclecole.colepercentday;

import com.unclecole.colepercentday.cmd.BaseCommand;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public final class ColePercentDay extends JavaPlugin {

    @Getter private static ColePercentDay instance;
    @Getter
    private String world;
    @Getter private Boolean isDay;
    @Getter private int votes;
    @Getter private ArrayList<UUID> voteDay;
    @Getter String voteMessage;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        world = getConfig().getString("World");
        voteMessage = getConfig().getString("broadcast");
        instance = this;
        isDay = true;
        voteDay = new ArrayList<>();
        Objects.requireNonNull(getCommand("voteday")).setExecutor(new BaseCommand());
        checkNight();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public void checkNight() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if(voteDay.isEmpty()) return;
                if(voteDay.size() >= (Bukkit.getOnlinePlayers().size()*0.25)) {
                    Bukkit.getScheduler().runTask(ColePercentDay.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            Bukkit.getWorld(world).setTime(0L);
                        }
                    });
                    voteDay.clear();
                }
            }
        }, 20L, 20L);
    }
}
