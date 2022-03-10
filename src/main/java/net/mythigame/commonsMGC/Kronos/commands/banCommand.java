package net.mythigame.commonsMGC.Kronos.commands;

import net.mythigame.commons.Account;
import net.mythigame.commons.RankUnit;
import net.mythigame.commons.Utils.MojangAPI;
import net.mythigame.commons.Utils.TimeUnit;
import net.mythigame.commonsMGC.Kronos.managers.BanManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class banCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player moderator){
            Account account = new Account().getAccount(moderator.getUniqueId());

            if(account != null){
                if(account.getGrade().getPower() < RankUnit.MODERATEUR.getPower()) moderator.sendMessage("§c[Kronos] Vous n'avez pas la permission d'utiliser cette commande.");
                if(!account.isModeration() && account.getGrade().getPower() < RankUnit.DEVELOPPEUR.getPower()){
                    moderator.sendMessage("§c[Kronos] Vous devez être en mode modération pour executer cette commande.");
                }

                if(args.length < 3) helpMessage(sender);

                Player target = Bukkit.getPlayer(args[0]);

                Account targetAccount;

                if(target != null){
                    targetAccount = new Account().getAccount(target.getUniqueId());
                }else{
                    targetAccount = new Account().getAccount(MojangAPI.getUUIDFromUsername(args[0]));
                }

                String time = args[1];
                String timeType = args[2].toLowerCase();
                String reason = "";

                if(time.equals("permanent") || time.equals("perma")){
                    for(int i = 2; i < args.length; i++){
                        reason += args[i] + " ";
                    }
                    BanManager banManager = new BanManager(moderator, targetAccount, reason);
                    banManager.ban();
                }else{
                    if(args.length < 4) helpMessage(sender);
                    int duration;
                    try {
                        duration = Integer.parseInt(time);
                    }catch (NumberFormatException e){
                        sender.sendMessage("§c[Kronos] Veuillez spécifier un nombre !");
                        return true;
                    }

                    switch (timeType){
                        case "seconde", "secondes" -> timeType = "s";
                        case "minute", "minutes" -> timeType = "m";
                        case "heure", "heures" -> timeType = "h";
                        case "jour", "jours" -> timeType = "d";
                        case "semaine", "semaines" -> timeType = "sem";
                        case "mois" -> timeType = "mo";
                        case "annee", "annees", "année", "années" -> timeType = "y";
                    }

                    if(!TimeUnit.existFromShortcut(timeType)){
                        sender.sendMessage("§c[Kronos] Cette unité de temps n'existe pas !");
                        for(TimeUnit units : TimeUnit.values()){
                            sender.sendMessage("§c" + units.getName() + " §c: §c" + units.getShortcut());
                        }
                        return true;
                    }
                    TimeUnit unit = TimeUnit.getFromShortcut(timeType);
                    long banTime = unit.getToSecond() * duration;

                    for(int i = 3; i < args.length; i++){
                        reason += args[i] + " ";
                    }

                    BanManager banManager = new BanManager(moderator, targetAccount, reason, banTime);
                    banManager.ban();
                }
            }
        }
        return true;
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage("§c[Kronos] Usage : /ban <player> <time> <timeKey> <reason>");
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
            arguments.add("nombre");
            arguments.add("permanent");
            arguments.add("perma");
        }else if(args.length == 3){
            arguments.clear();
            if(args[1].equalsIgnoreCase("permanent") || args[1].equalsIgnoreCase("perma")){
                arguments.add("raison");
            }else{
                arguments.add("secondes");
                arguments.add("minutes");
                arguments.add("heures");
                arguments.add("jours");
                arguments.add("semaines");
                arguments.add("mois");
                arguments.add("années");
            }
        }else if(args.length == 4){
            arguments.clear();
            arguments.add("raison");
        }else arguments.clear();
        return arguments;
    }
}
