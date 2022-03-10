package net.mythigame.commonsMGC.Zeus.managers;

import net.mythigame.commons.Account;

public class CoinsManager {

    final Account account;

    public CoinsManager(Account account) {
        this.account = account;
    }

    public void addCoins(int coins){
        account.setCoins(account.getCoins() + coins);
    }

    public void removeCoins(int coins){
        account.setCoins(account.getCoins() - coins);
    }

    public void setCoins(int coins){
        account.setCoins(coins);
    }

    public void resetCoins(){
        account.setCoins(0);
    }

}
