package net.mythigame.commonsMGC.Zeus.commands.Utiles;

import net.mythigame.commons.Account;
import net.mythigame.commons.AccountProvider;
import net.mythigame.commons.RankUnit;
import net.mythigame.commonsMGC.CommonsMGC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class vanishCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player player){
            Account account = new AccountProvider(player.getUniqueId()).getAccount();

            if(args.length != 1){
                if(account != null){
                    if(account.getGrade().getPower() < RankUnit.DEVELOPPEUR.getPower()) player.sendMessage("§c[Kronos] Vous n'avez pas la permission d'utiliser cette commande.");
                    if(!account.isModeration() && account.getGrade().getPower() < RankUnit.ADMINISTRATEUR.getPower()){
                        player.sendMessage("§c[Kronos] Vous devez être en mode modération pour executer cette commande.");
                        return true;
                    }
                    player.setInvisible(!player.isInvisible());
                    if(player.isInvisible()){
                        Bukkit.getOnlinePlayers().forEach(p -> p.hidePlayer(CommonsMGC.getInstance(), player));
                    }else Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(CommonsMGC.getInstance(), player));
                    player.sendMessage(player.isInvisible() ? "§c[Kronos] Vous êtes désormais invisible." : "§c[Kronos] §2Vous êtes désormais visible.");
                }
            }else{
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null){
                    player.sendMessage("§c[Kronos] Ce joueur n'est pas connecté.");
                    return true;
                }

                target.setInvisible(!target.isInvisible());
                if(target.isInvisible()){
                    Bukkit.getOnlinePlayers().forEach(p -> p.hidePlayer(CommonsMGC.getInstance(), target));
                }else Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(CommonsMGC.getInstance(), target));
                target.sendMessage(target.isInvisible() ? "§c[Kronos] Vous êtes désormais invisible." : "§c[Kronos] §2Vous êtes désormais visible.");
                player.sendMessage(target.isInvisible() ? "§c[Kronos] " + target.getName() + " est désormais invisible." : "§c[Kronos] " + target.getName() + " est désormais visible.");
            }
        }

        return true;
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage("§c[Kronos] Usage : /vanish [(<player>)]");
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
