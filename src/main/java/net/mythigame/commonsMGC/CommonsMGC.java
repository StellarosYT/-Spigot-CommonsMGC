package net.mythigame.commonsMGC;

import net.mythigame.commons.Account;
import net.mythigame.commons.Storage.MySQL.MySQLManager;
import net.mythigame.commons.Storage.Redis.RedisAccess;
import net.mythigame.commonsMGC.Kronos.commands.*;
import net.mythigame.commonsMGC.Kronos.redis.ReportRedisAccess;
import net.mythigame.commonsMGC.Zeus.commands.*;
import net.mythigame.commonsMGC.Zeus.commands.Utiles.*;
import net.mythigame.commonsMGC.Kronos.listeners.ModCancels;
import net.mythigame.commonsMGC.Kronos.listeners.ModItemsInteract;
import net.mythigame.commonsMGC.Kronos.listeners.MuteListener;
import net.mythigame.commonsMGC.Kronos.listeners.ReportInventoryClick;
import net.mythigame.commonsMGC.Zeus.commands.Utiles.gamemode.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static net.mythigame.commonsMGC.Kronos.commands.modCommand.Modmoderations;

public final class CommonsMGC extends JavaPlugin {

    private static CommonsMGC INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        registerListeners();
        registerCommands();

        MySQLManager.initAllConnection();
        RedisAccess.init();
        ReportRedisAccess.init();

        if(Bukkit.getOnlinePlayers().size() > 0){
            for(Player on : Bukkit.getOnlinePlayers()){
                new Account().getAccount(on.getUniqueId());
            }
        }
    }

    @Override
    public void onDisable() {
        if(Bukkit.getOnlinePlayers().size() > 0){
            for(Player on : Bukkit.getOnlinePlayers()){
                if(Modmoderations.contains(on)){
                    modCommand modCommand = new modCommand();
                    modCommand.leave(on);
                }
            }
        }
        RedisAccess.close();
        ReportRedisAccess.close();
        MySQLManager.closeAllConnection();
    }

    private void registerListeners(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new MuteListener(), this);
        pluginManager.registerEvents(new ReportInventoryClick(), this);
        pluginManager.registerEvents(new ModCancels(), this);
        pluginManager.registerEvents(new ModItemsInteract(), this);
    }

    @SuppressWarnings("ConstantConditions")
    private void registerCommands(){
        getCommand("ban").setExecutor(new banCommand());
        getCommand("ban").setTabCompleter(new banCommand());

        getCommand("case").setExecutor(new caseCommand());
        getCommand("case").setTabCompleter(new caseCommand());

        getCommand("kick").setExecutor(new kickCommand());
        getCommand("kick").setTabCompleter(new kickCommand());

        getCommand("mod").setExecutor(new modCommand());
        getCommand("mod").setTabCompleter(new modCommand());

        getCommand("mute").setExecutor(new muteCommand());
        getCommand("mute").setTabCompleter(new muteCommand());

        getCommand("report").setExecutor(new reportCommand());
        getCommand("report").setTabCompleter(new reportCommand());

        getCommand("unban").setExecutor(new unbanCommand());
        getCommand("unban").setTabCompleter(new unbanCommand());

        getCommand("unmute").setExecutor(new unmuteCommand());
        getCommand("unmute").setTabCompleter(new unmuteCommand());

        getCommand("warn").setExecutor(new warnCommand());
        getCommand("warn").setTabCompleter(new warnCommand());

        getCommand("whois").setExecutor(new whoisCommand());
        getCommand("whois").setTabCompleter(new whoisCommand());

        getCommand("broadcast").setExecutor(new broadcastCommand());
        getCommand("broadcast").setTabCompleter(new broadcastCommand());

        getCommand("coolkill").setExecutor(new coolKillCommand());
        getCommand("coolkill").setTabCompleter(new coolKillCommand());

        getCommand("feed").setExecutor(new feedCommand());
        getCommand("feed").setTabCompleter(new feedCommand());

        getCommand("fly").setExecutor(new flyCommand());
        getCommand("fly").setTabCompleter(new flyCommand());

        getCommand("gamemode").setExecutor(new gamemodeCommand());
        getCommand("gamemode").setTabCompleter(new gamemodeCommand());

        getCommand("gma").setExecutor(new gmaCommand());
        getCommand("gma").setTabCompleter(new gmaCommand());

        getCommand("gmc").setExecutor(new gmcCommand());
        getCommand("gmc").setTabCompleter(new gmcCommand());

        getCommand("gms").setExecutor(new gmsCommand());
        getCommand("gms").setTabCompleter(new gmsCommand());

        getCommand("gmsp").setExecutor(new gmspCommand());
        getCommand("gmsp").setTabCompleter(new gmspCommand());

        getCommand("god").setExecutor(new godCommand());
        getCommand("god").setTabCompleter(new godCommand());

        getCommand("heal").setExecutor(new healCommand());
        getCommand("heal").setTabCompleter(new healCommand());

        getCommand("vanish").setExecutor(new vanishCommand());
        getCommand("vanish").setTabCompleter(new vanishCommand());

        getCommand("coins").setExecutor(new coinsCommand());
        getCommand("coins").setTabCompleter(new coinsCommand());

        getCommand("level").setExecutor(new levelCommand());
        getCommand("level").setTabCompleter(new levelCommand());

        getCommand("rank").setExecutor(new rankCommand());
        getCommand("rank").setTabCompleter(new rankCommand());
    }

    public static CommonsMGC getInstance(){
        return INSTANCE;
    }
}
