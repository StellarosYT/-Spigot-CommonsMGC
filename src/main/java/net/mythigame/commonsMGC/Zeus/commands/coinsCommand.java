package net.mythigame.commonsMGC.Zeus.commands;

import net.mythigame.commons.Account;
import net.mythigame.commons.RankUnit;
import net.mythigame.commons.Utils.MojangAPI;
import net.mythigame.commonsMGC.Zeus.managers.CoinsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class coinsCommand implements CommandExecutor, TabCompleter {

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player){
            Account account = new Account().getAccount(player.getUniqueId());
            if(account.getGrade().getPower() < RankUnit.DEVELOPPEUR.getPower()){
                player.sendMessage("§9[Zeus] Vous n'avez pas la permission de faire ceci.");
                return true;
            }

            if(args.length < 2) helpMessage(player);

            String subCommand = args[0];
            Player target = Bukkit.getPlayer(args[1]);
            if(target != null){
                Account targetAccount = new Account().getAccount(target.getUniqueId());
                CoinsManager coinsManager = new CoinsManager(targetAccount);

                switch (subCommand) {
                    case "give" -> {
                        if (args.length < 3) helpMessage(player);
                        int coins = Integer.parseInt(args[2]);
                        coinsManager.addCoins(coins);
                        targetAccount.update();
                        player.sendMessage("§9[Zeus] Le compte de " + target.getName() + " a bien été crédité de " + coins + " coins. (Total : " + account.getCoins() + ")");
                    }
                    case "remove" -> {
                        if (args.length < 3) helpMessage(player);
                        int coins = Integer.parseInt(args[2]);
                        coinsManager.removeCoins(coins);
                        targetAccount.update();
                        player.sendMessage("§9[Zeus] Le compte de " + target.getName() + " a bien été débité de " + coins + " coins. (Total : " + account.getCoins() + ")");
                    }
                    case "set" -> {
                        if (args.length < 3) helpMessage(player);
                        int coins = Integer.parseInt(args[2]);
                        coinsManager.setCoins(coins);
                        targetAccount.update();
                        player.sendMessage("§9[Zeus] Le solde du compte de " + target.getName() + " a bien été défini à " + coins + " coins. (Total : " + account.getCoins() + ")");
                    }
                    case "reset" -> {
                        coinsManager.resetCoins();
                        targetAccount.update();
                        player.sendMessage("§9[Zeus] Le compte de " + target.getName() + " a bien été réinitialisé. (Total : " + account.getCoins() + ")");
                    }
                    default -> helpMessage(player);
                }
            }else{
                UUID targetUUID = MojangAPI.getUUIDFromUsername(args[0]);
                if(targetUUID == null) sender.sendMessage("§9[Zeus] Ce joueur n'existe pas.");
                Account targetAccount = new Account().getAccount(targetUUID);

                if(targetAccount != null){
                    CoinsManager coinsManager = new CoinsManager(targetAccount);

                    switch (subCommand) {
                        case "give" -> {
                            if (args.length < 3) helpMessage(player);
                            int coins = Integer.parseInt(args[2]);
                            coinsManager.addCoins(coins);
                            targetAccount.update();
                            player.sendMessage("§9[Zeus] Le compte de " + targetAccount.getUsername() + " a bien été crédité de " + coins + " coins. (Total : " + account.getCoins() + ")");
                        }
                        case "remove" -> {
                            if (args.length < 3) helpMessage(player);
                            int coins = Integer.parseInt(args[2]);
                            coinsManager.removeCoins(coins);
                            targetAccount.update();
                            player.sendMessage("§9[Zeus] Le compte de " + targetAccount.getUsername() + " a bien été débité de " + coins + " coins. (Total : " + account.getCoins() + ")");
                        }
                        case "set" -> {
                            if (args.length < 3) helpMessage(player);
                            int coins = Integer.parseInt(args[2]);
                            coinsManager.setCoins(coins);
                            targetAccount.update();
                            player.sendMessage("§9[Zeus] Le solde du compte de " + targetAccount.getUsername() + " a bien été défini à " + coins + " coins. (Total : " + account.getCoins() + ")");
                        }
                        case "reset" -> {
                            coinsManager.resetCoins();
                            targetAccount.update();
                            player.sendMessage("§9[Zeus] Le compte de " + targetAccount.getUsername() + " a bien été réinitialisé. (Total : " + account.getCoins() + ")");
                        }
                        default -> helpMessage(player);
                    }
                }else sender.sendMessage("§9[Zeus] Ce joueur ne s'est jamais connecté !");
            }
        }
        return true;
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage("§9[Zeus] Usage : /coins <param> <player> [(<value>)]");
    }

    List<String> arguments = new ArrayList<>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1){
            arguments.clear();
            arguments.add("give");
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
        }else if(!args[0].equalsIgnoreCase("reset") && args.length == 3){
            arguments.clear();
            arguments.add("montant");
        } else arguments.clear();
        return arguments;
    }
}
