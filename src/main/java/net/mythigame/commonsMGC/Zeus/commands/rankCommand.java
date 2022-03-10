package net.mythigame.commonsMGC.Zeus.commands;

import net.mythigame.commons.Account;
import net.mythigame.commons.RankUnit;
import net.mythigame.commons.Utils.MojangAPI;
import net.mythigame.commonsMGC.Zeus.managers.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class rankCommand implements CommandExecutor, TabCompleter {
    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player){
            Account account = new Account().getAccount(player.getUniqueId());
            if(account == null){
                player.sendMessage("§9[Zeus] Une erreur s'est produite !");
                return true;
            }
            if(args.length < 2){
                helpMessage(player);
                return true;
            }

            if(account.getGrade().getPower() < RankUnit.DEVELOPPEUR.getPower()){
                helpMessage(player);
                return true;
            }
        }else{
            if(args.length < 2){
                helpMessage(sender);
            }
        }

        Player target = Bukkit.getPlayer(args[0]);
        RankUnit rank = RankUnit.getByName(args[1]);
        if(target != null && rank != null){
            Account targetAccount = new Account().getAccount(target.getUniqueId());
            RankManager rankManager = new RankManager(targetAccount);
            if(args.length > 3){
                long time = Long.parseLong(args[2]);
                rankManager.setRank(rank, time);
            }else{
                rankManager.setRank(rank);
            }
            sender.sendMessage("§9[Zeus] Le rang de " + target.getName() + " est désormais " + rank.getName());
            target.sendMessage("§9[Zeus] Votre nouveau rang est " + rank.getName());
        }else if (target == null && rank != null){
            UUID targetUUID = MojangAPI.getUUIDFromUsername(args[0]);
            if(targetUUID == null) sender.sendMessage("§9[Zeus] Ce joueur n'existe pas.");
            Account targetAccount = new Account().getAccount(targetUUID);
            RankManager rankManager = new RankManager(targetAccount);

            if(targetAccount != null){
                if(args.length > 3){
                    long time = Long.parseLong(args[2]);
                    rankManager.setRank(rank, time);
                }else{
                    rankManager.setRank(rank);
                }
                sender.sendMessage("§9[Zeus] Le rang de " + targetAccount.getUsername() + " est désormais " + rank.getName());
            }else sender.sendMessage("§9[Zeus] Ce joueur ne s'est jamais connecté !");
        }
        return true;
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage("§9[Zeus] Usage : /rank <player> <rank>");
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
        }else if(args.length == 2){
            arguments.clear();
            for (RankUnit rankUnit : RankUnit.values()) {
                arguments.add(rankUnit.getName());
            }
        }else arguments.clear();
        return arguments;
    }
}
