package net.mythigame.commonsMGC.Kronos.commands;

import net.mythigame.commons.Account;
import net.mythigame.commons.RankUnit;
import net.mythigame.commons.Utils.MojangAPI;
import net.mythigame.commonsMGC.Kronos.managers.UnmuteManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class unmuteCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player moderator){
            Account account = new Account().getAccount(moderator.getUniqueId());

            if(account != null){
                if(account.getGrade().getPower() < RankUnit.MODERATEUR.getPower()) moderator.sendMessage("§c[Kronos] Vous n'avez pas la permission d'utiliser cette commande.");
                if(!account.isModeration() && account.getGrade().getPower() < RankUnit.DEVELOPPEUR.getPower()){
                    moderator.sendMessage("§c[Kronos] Vous devez être en mode modération pour executer cette commande.");
                }

                if(args.length < 2) helpMessage(sender);

                Player targetPlayer = Bukkit.getPlayer(args[0]);

                Account targetAccount;
                if(targetPlayer != null){
                    targetAccount = new Account().getAccount(targetPlayer.getUniqueId());
                }else{
                    targetAccount = new Account().getAccount(MojangAPI.getUUIDFromUsername(args[0]));
                }
                String reason = "";

                for(int i = 1; i < args.length; i++){
                    reason += args[i] + " ";
                }
                UnmuteManager unmuteManager = new UnmuteManager(targetAccount, moderator, reason);
                unmuteManager.unmute();
                if(targetPlayer != null) targetPlayer.sendMessage("§c[Kronos] Vous avez été dé-mute !");
            }
        }

        return true;
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage("§c[Kronos] Usage : /unmute <player> <reason>");
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
            arguments.add("raison");
        }else arguments.clear();
        return arguments;
    }

}
