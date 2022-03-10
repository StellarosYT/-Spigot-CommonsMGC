package net.mythigame.commonsMGC.Zeus.commands;

import net.mythigame.commons.Account;
import net.mythigame.commons.RankUnit;
import net.mythigame.commons.Utils.MojangAPI;
import net.mythigame.commonsMGC.Zeus.managers.LevelManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class levelCommand implements CommandExecutor, TabCompleter {


    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player){
            Account account = new Account().getAccount(player.getUniqueId());
            if(account.getGrade().getPower() < RankUnit.DEVELOPPEUR.getPower()){
                player.sendMessage("§9[Zeus] Vous n'avez pas la permission de faire ceci.");
                return true;
            }

            if(args.length < 3) helpMessage(player);

            String subCommand = args[0];
            Player target = Bukkit.getPlayer(args[1]);
            String subCommand2 = args[2];
            if(target != null){
                Account targetAccount = new Account().getAccount(target.getUniqueId());
                LevelManager levelManager = new LevelManager(targetAccount);

                switch (subCommand) {
                    case "add" -> {
                        switch (subCommand2){
                            case "level" -> {
                                if (args.length < 4) helpMessage(player);
                                int newValue = Integer.parseInt(args[3]);
                                levelManager.addLevel(newValue);
                                targetAccount.update();
                                player.sendMessage("§9[Zeus] Le compte de " + target.getName() + " a bien reçu " + newValue + " level. (Total : " + account.getLevel() + ")");
                            }
                            case "xp" -> {
                                if (args.length < 4) helpMessage(player);
                                int newValue = Integer.parseInt(args[3]);
                                levelManager.addXp(newValue);
                                targetAccount.update();
                                player.sendMessage("§9[Zeus] Le compte de " + target.getName() + " a bien reçu " + newValue + " xp. (Total : " + account.getXp() + ")");
                            }
                            default -> helpMessage(player);
                        }
                    }
                    case "remove" -> {
                        switch (subCommand2){
                            case "level" -> {
                                if (args.length < 4) helpMessage(player);
                                int newValue = Integer.parseInt(args[3]);
                                levelManager.removeLevel(newValue);
                                targetAccount.update();
                                player.sendMessage("§9[Zeus] Le compte de " + target.getName() + " a bien perdu " + newValue + " level. (Total : " + account.getLevel() + ")");
                            }
                            case "xp" -> {
                                if (args.length < 4) helpMessage(player);
                                int newValue = Integer.parseInt(args[3]);
                                levelManager.removeXp(newValue);
                                targetAccount.update();
                                player.sendMessage("§9[Zeus] Le compte de " + target.getName() + " a bien perdu " + newValue + " xp. (Total : " + account.getXp() + ")");
                            }
                            default -> helpMessage(player);
                        }
                    }
                    case "set" -> {
                        switch (subCommand2){
                            case "level" -> {
                                if (args.length < 4) helpMessage(player);
                                int newValue = Integer.parseInt(args[3]);
                                levelManager.setLevel(newValue);
                                targetAccount.update();
                                player.sendMessage("§9[Zeus] Le compte de " + target.getName() + " a bien été défini au niveau " + newValue + ". (Total : " + account.getLevel() + ")");
                            }
                            case "xp" -> {
                                if (args.length < 4) helpMessage(player);
                                int newValue = Integer.parseInt(args[3]);
                                levelManager.setXp(newValue);
                                targetAccount.update();
                                player.sendMessage("§9[Zeus] Le compte de " + target.getName() + " a bien été défini à l'xp " + newValue + ". (Total : " + account.getXp() + ")");
                            }
                            default -> helpMessage(player);
                        }
                    }
                    case "reset" -> {
                        switch (subCommand2){
                            case "level" -> {
                                int newValue = Integer.parseInt(args[3]);
                                levelManager.resetLevel();
                                targetAccount.update();
                                player.sendMessage("§9[Zeus] Les niveaux du compte de " + target.getName() + " ont bien été réinitialisé. (Total : " + account.getLevel() + ")");
                            }
                            case "xp" -> {
                                int newValue = Integer.parseInt(args[3]);
                                levelManager.resetXp();
                                targetAccount.update();
                                player.sendMessage("§9[Zeus] L'XP du compte de " + target.getName() + " ont bien été réinitialisé. (Total : " + account.getXp() + ")");
                            }
                            default -> helpMessage(player);
                        }
                    }
                    default -> helpMessage(player);
                }
            }else{
                UUID targetUUID = MojangAPI.getUUIDFromUsername(args[1]);
                if(targetUUID == null) sender.sendMessage("§9[Zeus] Ce joueur n'existe pas.");
                Account targetAccount = new Account().getAccount(targetUUID);

                if(targetAccount != null){
                    LevelManager levelManager = new LevelManager(targetAccount);

                    switch (subCommand) {
                        case "add" -> {
                            switch (subCommand2){
                                case "level" -> {
                                    if (args.length < 4) helpMessage(player);
                                    int newValue = Integer.parseInt(args[3]);
                                    levelManager.addLevel(newValue);
                                    targetAccount.update();
                                    player.sendMessage("§9[Zeus] Le compte de " + targetAccount.getUsername() + " a bien reçu " + newValue + " level. (Total : " + account.getLevel() + ")");
                                }
                                case "xp" -> {
                                    if (args.length < 4) helpMessage(player);
                                    int newValue = Integer.parseInt(args[3]);
                                    levelManager.addXp(newValue);
                                    targetAccount.update();
                                    player.sendMessage("§9[Zeus] Le compte de " + targetAccount.getUsername() + " a bien reçu " + newValue + " xp. (Total : " + account.getXp() + ")");
                                }
                                default -> helpMessage(player);
                            }
                        }
                        case "remove" -> {
                            switch (subCommand2){
                                case "level" -> {
                                    if (args.length < 4) helpMessage(player);
                                    int newValue = Integer.parseInt(args[3]);
                                    levelManager.removeLevel(newValue);
                                    targetAccount.update();
                                    player.sendMessage("§9[Zeus] Le compte de " + targetAccount.getUsername() + " a bien perdu " + newValue + " level. (Total : " + account.getLevel() + ")");
                                }
                                case "xp" -> {
                                    if (args.length < 4) helpMessage(player);
                                    int newValue = Integer.parseInt(args[3]);
                                    levelManager.removeXp(newValue);
                                    targetAccount.update();
                                    player.sendMessage("§9[Zeus] Le compte de " + targetAccount.getUsername() + " a bien perdu " + newValue + " xp. (Total : " + account.getXp() + ")");
                                }
                                default -> helpMessage(player);
                            }
                        }
                        case "set" -> {
                            switch (subCommand2){
                                case "level" -> {
                                    if (args.length < 4) helpMessage(player);
                                    int newValue = Integer.parseInt(args[3]);
                                    levelManager.setLevel(newValue);
                                    targetAccount.update();
                                    player.sendMessage("§9[Zeus] Le compte de " + targetAccount.getUsername() + " a bien été défini au niveau " + newValue + ". (Total : " + account.getLevel() + ")");
                                }
                                case "xp" -> {
                                    if (args.length < 4) helpMessage(player);
                                    int newValue = Integer.parseInt(args[3]);
                                    levelManager.setXp(newValue);
                                    targetAccount.update();
                                    player.sendMessage("§9[Zeus] Le compte de " + targetAccount.getUsername() + " a bien été défini à l'xp " + newValue + ". (Total : " + account.getXp() + ")");
                                }
                                default -> helpMessage(player);
                            }
                        }
                        case "reset" -> {
                            switch (subCommand2){
                                case "level" -> {
                                    int newValue = Integer.parseInt(args[3]);
                                    levelManager.resetLevel();
                                    targetAccount.update();
                                    player.sendMessage("§9[Zeus] Les niveaux du compte de " + targetAccount.getUsername() + " ont bien été réinitialisé. (Total : " + account.getLevel() + ")");
                                }
                                case "xp" -> {
                                    int newValue = Integer.parseInt(args[3]);
                                    levelManager.resetXp();
                                    targetAccount.update();
                                    player.sendMessage("§9[Zeus] L'XP du compte de " + targetAccount.getUsername() + " ont bien été réinitialisé. (Total : " + account.getXp() + ")");
                                }
                                default -> helpMessage(player);
                            }
                        }
                        default -> helpMessage(player);
                    }
                }else sender.sendMessage("§9[Zeus] Ce joueur ne s'est jamais connecté !");
            }
        }
        return true;
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage("§9[Zeus] Usage : /level <param> <player> <level/xp> [(<value>)]");
    }

    List<String> arguments = new ArrayList<>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1){
            arguments.clear();
            arguments.add("add");
            arguments.add("remove");
            arguments.add("set");
            arguments.add("reset");
        }else if(args.length == 2){
            arguments.clear();
            Player[] players = new Player[Bukkit.getOnlinePlayers().size()];
            Bukkit.getOnlinePlayers().toArray(players);
            for (Player player : players) {
                arguments.add(player.getName());
            }
        }else if(args.length == 3){
            arguments.add("level");
            arguments.add("xp");
        }else if(!args[0].equalsIgnoreCase("reset") && args.length == 4){
            arguments.clear();
            arguments.add("montant");
        } else arguments.clear();
        return arguments;
    }
}
