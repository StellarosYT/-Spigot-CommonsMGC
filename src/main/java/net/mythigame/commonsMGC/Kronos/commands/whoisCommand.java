package net.mythigame.commonsMGC.Kronos.commands;

import net.mythigame.commons.Account;
import net.mythigame.commons.RankUnit;
import net.mythigame.commons.Utils.MojangAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class whoisCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player moderator) {
            Account account = new Account().getAccount(moderator.getUniqueId());

            if (account != null) {
                if (account.getGrade().getPower() < RankUnit.MODERATEUR.getPower())
                    moderator.sendMessage("§c[Kronos] Vous n'avez pas la permission d'utiliser cette commande.");
                if (!account.isModeration() && account.getGrade().getPower() < RankUnit.DEVELOPPEUR.getPower()) {
                    moderator.sendMessage("§c[Kronos] Vous devez être en mode modération pour executer cette commande.");
                }

                if(args.length != 1) helpMessage(moderator);

                Player target = Bukkit.getPlayer(args[0]);

                Account targetAccount;
                if(target != null){
                    targetAccount = new Account().getAccount(target.getUniqueId());
                }else {
                    targetAccount = new Account().getAccount(MojangAPI.getUUIDFromUsername(args[0]));
                }

                if(targetAccount == null){
                    moderator.sendMessage("§c[Kronos] Ce joueur ne s'est jamais connecté.");
                    return true;
                }

                if(targetAccount.isConnected()){
                    String server = targetAccount.getServer();

                    moderator.sendMessage("§9=================================\n" +
                            "§9UUID: §r" + targetAccount.getUuid() + "\n" +
                            "§9Pseudo: §r" + targetAccount.getUsername() + "\n" +
                            "§9Rang: §r" + targetAccount.getGrade().getName() + "\n" +
                            "§9Connecté : " + (targetAccount.isConnected() ? "§2Oui" : "§cNon") + "\n" +
                            "§9Serveur: §r" + targetAccount.getServer() + "\n" +
                            "§9=================================\n" +
                            "§9Mute: " + (targetAccount.isMuted() ? "§2Oui" : "§cNon") + "\n" +
                            "§9Banni: " + (targetAccount.isBanned() ? "§2Oui" : "§cNon") + "\n" +
                            "§9Nombre de mute: §r" + getCaseSize(targetAccount, "mute") + "\n" +
                            "§9Nombre de ban: §r" + getCaseSize(targetAccount, "ban") + "\n" +
                            "§9Nombre d'avertissement: §r" + getCaseSize(targetAccount, "warn") + "\n" +
                            "§9Nombre de kick: §r" + getCaseSize(targetAccount, "kick") + "\n" +
                            "§9=================================\n" +
                            "§9Mode modération: " + (targetAccount.isModeration() ? "§2Oui" : "§cNon") + "\n"
                    );
                }else{
                    moderator.sendMessage("§9=================================\n" +
                            "§9UUID: §r" + targetAccount.getUuid() + "\n" +
                            "§9Pseudo: §r" + targetAccount.getUsername() + "\n" +
                            "§9Rang: §r" + targetAccount.getGrade().getName() + "\n" +
                            "§9Connecté : " + (targetAccount.isConnected() ? "§2Oui" : "§cNon") + "\n" +
                            "§9=================================\n" +
                            "§9Mute: " + (targetAccount.isMuted() ? "§2Oui" : "§cNon") + "\n" +
                            "§9Banni: " + (targetAccount.isBanned() ? "§2Oui" : "§cNon") + "\n" +
                            "§9Nombre de mute: §r" + getCaseSize(targetAccount, "mute") + "\n" +
                            "§9Nombre de ban: §r" + getCaseSize(targetAccount, "ban") + "\n" +
                            "§9Nombre d'avertissement: §r" + getCaseSize(targetAccount, "warn") + "\n" +
                            "§9Nombre de kick: §r" + getCaseSize(targetAccount, "kick") + "\n" +
                            "§9=================================\n"
                    );
                }

            }else{
                moderator.sendMessage("§c[Kronos] Votre compte est introuvable.");
            }
        }
        return true;
    }

    private int getCaseSize(Account account, String type){
        if(account != null){
            if(account.getCases() != null){
                return (int) account.getCases().stream().filter(e -> e.getType().equals(type)).count();
            }
        }
        return 0;
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage("§c[Kronos] Usage : /whois <player>");
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
