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

public class gamemodeCommand implements CommandExecutor, TabCompleter {

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

        if(args.length < 1 || args.length > 2){
            player.sendMessage("§9[Zeus] Usage : /gm [(<player>)] <creative (1) / adventure (2) / survival (0) / spectator (3)>");
            return true;
        }

        if(args.length == 1){
            String gameMode = args[0].toLowerCase();

            switch (gameMode) {
                case "0", "survival" -> player.setGameMode(GameMode.SURVIVAL);
                case "1", "creative" -> player.setGameMode(GameMode.CREATIVE);
                case "2", "adventure" -> player.setGameMode(GameMode.ADVENTURE);
                case "3", "spectator" -> player.setGameMode(GameMode.SPECTATOR);
                default -> player.sendMessage("§9[Zeus] Veuillez préciser un mode correct : (0) survival, (1) crative, (2) adventure, (3) spectator.");
            }
        }else {
            Player target = Bukkit.getPlayer(args[0]);
            String gameMode = args[1];

            if(target == null){
                sender.sendMessage("§9[Zeus] Cette personne n'est pas connecté.");
                return true;
            }

            switch (gameMode){
                case "0", "survival" -> target.setGameMode(GameMode.SURVIVAL);
                case "1", "creative" -> target.setGameMode(GameMode.CREATIVE);
                case "2", "adventure" -> target.setGameMode(GameMode.ADVENTURE);
                case "3", "spectator" -> target.setGameMode(GameMode.SPECTATOR);
                default -> target.sendMessage("§9[Zeus] Veuillez préciser un mode correct : (0) survival, (1) crative, (2) adventure, (3) spectator.");
            }
        }
        return true;
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage("§9[Zeus] Usage : /gamemode [(<player>)] [(gamemode)]");
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
            arguments.add("creative");
            arguments.add("survival");
            arguments.add("adventure");
            arguments.add("spectator");
        }else if(args.length == 2){
            arguments.clear();
            arguments.add("creative");
            arguments.add("survival");
            arguments.add("adventure");
            arguments.add("spectator");
        }else arguments.clear();
        return arguments;
    }

}
