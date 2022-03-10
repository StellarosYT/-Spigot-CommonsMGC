package net.mythigame.commonsMGC.Kronos.managers;

import net.mythigame.commons.Account;
import net.mythigame.commons.AccountCase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.mythigame.commonsMGC.Kronos.managers.TimeManager.getTimeLeft;

public class MuteManager {

    private final Player moderator;
    private final Account account;
    private final String reason;
    private final long end_time;

    public MuteManager(Player moderator, Account account, String reason, long end_time) {
        this.moderator = moderator;
        this.account = account;
        this.reason = reason;
        this.end_time = end_time;
    }

    public MuteManager(Player moderator, Account account, String reason) {
        this.moderator = moderator;
        this.account = account;
        this.reason = reason;
        this.end_time = -1;
    }

    public void mute(){
        if(account != null){
            if(account.isMuted()){
                moderator.sendMessage("§c[Kronos] Ce joueur est déjà réduit au silence.");
                return;
            }
            Player targetPlayer = Bukkit.getPlayer(account.getUuid());
            final String id = account.getUuid() + ":" + moderator.getUniqueId() + ":" + System.currentTimeMillis();
            long endToMillis = end_time * 1000;
            long mute_end = -1;
            if(end_time != -1){
                mute_end = endToMillis + System.currentTimeMillis();
            }
            AccountCase accountCase = new AccountCase(id, account.getUuid(), "mute", reason, mute_end, moderator.getUniqueId(), null, null);
            account.setMuted(true);
            account.setMute_id(id);
            account.getCases().add(accountCase);
            if(targetPlayer != null){
                account.update();
                if(end_time == -1){
                    targetPlayer.sendMessage("§c[Kronos] Vous avez été réduit au silence définitivement !");
                    moderator.sendMessage("§c[Kronos] Vous avez bien réduit au silence définitivement " + account.getUsername() + " pour : " + reason);
                }else{
                    targetPlayer.sendMessage("§c[Kronos] Vous avez été réduit au silence pendant " + getTimeLeft(accountCase) + " pour : " + reason);
                    moderator.sendMessage("§c[Kronos] Vous avez bien réduit au silence " + account.getUsername() + " pendant " + getTimeLeft(accountCase) + " pour : " + reason);
                }
            }else {
                if(end_time == -1){
                    moderator.sendMessage("§c[Kronos] Vous avez bien réduit au silence définitivement " + account.getUsername() + " pour : " + reason);
                }else{
                    moderator.sendMessage("§c[Kronos] Vous avez bien réduit au silence " + account.getUsername() + " pendant " + getTimeLeft(accountCase) + " pour : " + reason);
                }
            }
            account.update();
        }else{
            moderator.sendMessage("§c[Kronos] Ce joueur ne s'est jamais connecté ou n'existe pas.");
        }
    }

}
