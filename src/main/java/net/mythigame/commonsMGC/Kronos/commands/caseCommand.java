package net.mythigame.commonsMGC.Kronos.commands;

import net.mythigame.commons.Account;
import net.mythigame.commons.AccountProvider;
import net.mythigame.commons.RankUnit;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class caseCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player player){
            Account account = new AccountProvider(player.getUniqueId()).getAccount();

            if(account != null){
                if(account.getGrade().getPower() < RankUnit.MODERATEUR.getPower()) player.sendMessage("§c[Kronos] Vous n'avez pas la permission d'utiliser cette commande.");
                if(!account.isModeration() && account.getGrade().getPower() < RankUnit.DEVELOPPEUR.getPower()){
                    player.sendMessage("§c[Kronos] Vous devez être en mode modération pour executer cette commande.");



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
        }else arguments.clear();
        return arguments;
    }
}
