package net.mythigame.commonsMGC.Kronos.managers;

import net.mythigame.commons.Account;
import net.mythigame.commons.AccountCase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WarnManager {

    private final Account account;
    private final Player moderator;
    private final String warn_reason;

    public WarnManager(Account account, Player moderator, String kick_reason) {
        this.account = account;
        this.moderator = moderator;
        this.warn_reason = kick_reason;
    }

    public void warn(){
        if(account != null){
            Player target = Bukkit.getPlayer(account.getUuid());
            if(target == null){
                moderator.sendMessage("§c[Kronos] Ce joueur n'est pas connecté.");
                return;
            }
            final String id = account.getUuid() + ":" + moderator.getUniqueId() + ":" + System.currentTimeMillis();
            AccountCase accountCase = new AccountCase(id, account.getUuid(), "warn", warn_reason, -1, moderator.getUniqueId(), null, null);
            account.getCases().add(accountCase);
            account.update();
            target.sendMessage("§c[Kronos] Vous avez été averti pour : " + warn_reason);
            moderator.sendMessage("§c[Kronos] " + account.getUsername() + " a bien été averti pour : " + warn_reason);
        }else{
            moderator.sendMessage("§c[Kronos] Ce joueur ne s'est jamais connecté ou n'existe pas.");
        }
    }

}
