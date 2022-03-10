package net.mythigame.commonsMGC.Zeus.managers;

import net.mythigame.commons.Account;

public class LevelManager {

    final Account account;

    public LevelManager(Account account) {
        this.account = account;
    }

    public void addLevel(int coins){
        account.setLevel(account.getCoins() + coins);
    }

    public void removeLevel(int coins){
        account.setLevel(account.getCoins() - coins);
    }

    public void setLevel(int coins){
        account.setLevel(coins);
    }

    public void resetLevel(){
        account.setLevel(0);
    }

    public void addXp(int xp){
        account.setXp(account.getXp() + xp);
    }

    public void removeXp(int xp){
        account.setXp(account.getXp() - xp);
    }

    public void setXp(int xp){
        account.setXp(xp);
    }

    public void resetXp(){
        account.setXp(0);
    }

}
