package net.mythigame.commonsMGC.Kronos.managers;

import net.mythigame.commons.Account;
import net.mythigame.commons.AccountCase;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static net.mythigame.commonsMGC.Kronos.managers.TimeManager.getTimeLeft;

public class BanManager {

    private final Player moderator;
    private final Account account;
    private final String reason;
    private final long end_time;

    public BanManager(Player moderator, Account account, String reason, long end_time) {
        this.moderator = moderator;
        this.account = account;
        this.reason = reason;
        this.end_time = end_time;
    }

    public BanManager(Player moderator, Account account, String reason) {
        this.moderator = moderator;
        this.account = account;
        this.reason = reason;
        this.end_time = -1;
    }

    @SuppressWarnings("ConstantConditions")
    public void ban(){
        if(account != null){
            if(account.isBanned()){
                moderator.sendMessage("§c[Kronos] Ce joueur est déjà banni.");
                return;
            }
            Player targetPlayer = Bukkit.getPlayer(account.getUuid());
            final String id = account.getUuid() + ":" + moderator.getUniqueId() + ":" + System.currentTimeMillis();
            long endToMillis = end_time * 1000;
            long ban_end = -1;
            if(end_time != -1){
                ban_end = endToMillis + System.currentTimeMillis();
            }
            AccountCase accountCase = new AccountCase(id, account.getUuid(), "ban", reason, ban_end, moderator.getUniqueId(), null, null);
            account.setBanned(true);
            account.setBan_id(id);
            account.getCases().add(accountCase);
            if(targetPlayer != null){
                if(targetPlayer.isDead()) targetPlayer.spigot().respawn();
                Bukkit.getWorld(targetPlayer.getWorld().getUID()).strikeLightningEffect(targetPlayer.getLocation());
                Bukkit.getWorld(targetPlayer.getWorld().getUID()).playSound(targetPlayer.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 0);
                account.update();
                if(end_time == -1){
                    targetPlayer.kickPlayer("§c[Kronos] Vous avez été banni définitivement !\n " +
                            "\n " +
                            "§6Raison : §f" + reason
                    );
                    moderator.sendMessage("§c[Kronos] Vous avez bien banni définitivement " + account.getUsername() + " pour : " + reason);
                }else{
                    targetPlayer.kickPlayer("§c[Kronos] Vous avez été banni !\n " +
                            "\n " +
                            "§6Raison : §f" + reason + "\n " +
                            "\n " +
                            "§aTemps restant : §f" + getTimeLeft(accountCase)
                    );
                    moderator.sendMessage("§c[Kronos] Vous avez bien banni " + account.getUsername() + " pendant " + getTimeLeft(accountCase) + " pour : " + reason);
                }
            }else {
                account.update();
                if(end_time == -1){
                    moderator.sendMessage("§c[Kronos] Vous avez bien banni définitivement " + account.getUsername() + " pour : " + reason);
                }else{
                    moderator.sendMessage("§c[Kronos] Vous avez bien banni " + account.getUsername() + " pendant " + getTimeLeft(accountCase) + " pour : " + reason);
                }
            }
        }else{
            moderator.sendMessage("§c[Kronos] Ce joueur ne s'est jamais connecté ou n'existe pas.");
        }
    }

}
