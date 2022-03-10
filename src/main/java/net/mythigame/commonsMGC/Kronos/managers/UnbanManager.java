package net.mythigame.commonsMGC.Kronos.managers;

import net.mythigame.commons.Account;
import net.mythigame.commons.AccountCase;
import org.bukkit.entity.Player;

public class UnbanManager {

    private final Account account;
    private final Player unban_moderator;
    private final String unban_reason;

    public UnbanManager(Account account, Player unban_moderator, String unban_reason) {
        this.account = account;
        this.unban_moderator = unban_moderator;
        this.unban_reason = unban_reason;
    }

    public void unban(){
        if(account != null){
            if(!account.isBanned()){
                unban_moderator.sendMessage("§c[Kronos] Ce joueur n'est pas banni.");
                return;
            }
            AccountCase accountCase = account.getCaseById(account.getBan_id());
            accountCase.setCancel_moderator(unban_moderator.getUniqueId());
            accountCase.setCancel_reason(unban_reason);
            account.setBanned(false);
            account.setBan_id(null);
            account.update();
            unban_moderator.sendMessage("§c[Kronos] Vous avez bien dé-banni " + account.getUsername() + " pour : " + unban_reason);
        }else{
            unban_moderator.sendMessage("§c[Kronos] Ce joueur ne s'est jamais connecté ou n'existe pas.");
        }
    }

}
