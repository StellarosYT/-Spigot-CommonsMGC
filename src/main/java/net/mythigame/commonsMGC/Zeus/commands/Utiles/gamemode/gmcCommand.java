package net.mythigame.commonsMGC.Zeus.commands.Utiles.gamemode;

import net.mythigame.commons.Account;
import net.mythigame.commons.RankUnit;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class gmcCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player player)){
            return true;
        }

        Account account = new Account().getAccount(player.getUniqueId());
        if(account == null){
            player.sendMessage("§9[Zeus] Votre compte est introuvable !");
            return true;
        }

        if(account.getGrade().getPower() < RankUnit.DEVELOPPEUR.getPower()){
            player.sendMessage("§9[Zeus] Vous n'avez pas la permission d'utiliser cette commande !");
            return true;
        }

        if(args.length == 0){
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage("§9[Zeus] Vous êtes désormais en gamemode créatif.");
        }else if(args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);

            if(target == null){
                sender.sendMessage("§9[Zeus] Cette personne n'est pas connecté.");
                return true;
            }

            target.setGameMode(GameMode.CREATIVE);
            target.sendMessage("§9[Zeus] Vous êtes désormais en gamemode créatif.");
            player.sendMessage("§9[Zeus] " + target.getName() + " est désormais en gamemode créatif.");
        }else helpMessage(player);
        return true;
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage("§9[Zeus] Usage : /gmc [(<player>)]");
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
