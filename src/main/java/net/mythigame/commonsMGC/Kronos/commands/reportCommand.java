package net.mythigame.commonsMGC.Kronos.commands;

import net.mythigame.commons.Account;
import net.mythigame.commons.RankUnit;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.mythigame.commonsMGC.Kronos.listeners.ReportInventoryClick.getReportInventory;

public class reportCommand implements CommandExecutor, TabCompleter {

    private final Map<Player, Long> cooldown = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player player){

            if(args.length != 1){
                helpMessage(player);
                return true;
            }

            Account account = new Account().getAccount(player.getUniqueId());

            if(account == null) return true;

            if(account.getGrade().getPower() < RankUnit.DEVELOPPEUR.getPower()){
                if(cooldown.containsKey(player)){
                    long time = (System.currentTimeMillis() - cooldown.get(player)) / 1000;
                    if(time < 120){
                        player.sendMessage("§c[Kronos] Merci de patienter entre chaque signalements (" + (120 - time) + " secondes restantes).");
                        player.closeInventory();
                        return true;
                    }else cooldown.remove(player);
                }else{
                    cooldown.put(player, System.currentTimeMillis());
                }
            }

            Player target = Bukkit.getPlayer(args[0]);
            if(target != null){
                player.openInventory(getReportInventory(player, target));
                return true;
            }else{
                player.sendMessage("§c[Kronos] Vous n'êtes pas sur le même serveur que ce joueur.");
            }
        }

        return true;
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage("§c[Kronos] Usage : /report <player>");
    }

    List<String> arguments = new ArrayList<>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1){
            arguments.clear();
            Player[] players = new Player[Bukkit.getOnlinePlayers().size()];
            Bukkit.getOnlinePlayers().toArray(players);
            for (Player player : players) {
                arguments.add(player.getName());
            }
        }else arguments.clear();
        return arguments;
    }
}
