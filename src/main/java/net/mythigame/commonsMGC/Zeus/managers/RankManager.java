package net.mythigame.commonsMGC.Zeus.managers;

import net.mythigame.commons.Account;
import net.mythigame.commons.RankUnit;

public class RankManager {

    final Account account;

    public RankManager(Account account) {
        this.account = account;
    }

    public void setRank(RankUnit rank){
        account.setGrade(rank);
        account.update();
    }

    public void setRank(RankUnit rank, long end){
        account.setGrade(rank);
        account.setGrade_end(end);
        account.update();
    }

    public void resetRank(){
        account.setGrade(RankUnit.JOUEUR);
        account.setGrade_end(-1);
        account.update();
    }

}
