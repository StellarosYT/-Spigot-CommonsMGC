package net.mythigame.commonsMGC.Kronos.managers;

import net.mythigame.commons.Account;
import net.mythigame.commons.AccountCase;
import net.mythigame.commonsMGC.Kronos.redis.ReportRedisAccess;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReportManager {

    public ReportManager(){

    }

    public List<AccountCase> getReports(){

        final RedissonClient redissonClient = ReportRedisAccess.INSTANCE.getRedissonClient();
        final Set<AccountCase> accountCases = new HashSet<>();
        final Iterable<String> keys = redissonClient.getKeys().getKeysByPattern("report:*");
        for(String key : keys){
            accountCases.add((AccountCase) redissonClient.getBucket(key).get());
        }

        return accountCases.stream().toList();
    }

    public List<AccountCase> getReports(Account account){

        final RedissonClient redissonClient = ReportRedisAccess.INSTANCE.getRedissonClient();
        final Set<AccountCase> accountCases = new HashSet<>();
        final Iterable<String> keys = redissonClient.getKeys().getKeysByPattern("report:*");
        for(String key : keys){
            AccountCase accountCase = (AccountCase) redissonClient.getBucket(key).get();
            if(accountCase.getUuid().equals(account.getUuid())){
                accountCases.add(accountCase);
            }
        }

        return accountCases.stream().toList();
    }

    public void addReport(AccountCase accountCase){
        final RedissonClient redissonClient = ReportRedisAccess.INSTANCE.getRedissonClient();
        final String key = "report:" + accountCase.getId();
        final RBucket<AccountCase> accountCaseRBucket = redissonClient.getBucket(key);
        accountCaseRBucket.set(accountCase);
    }

    public void removeReport(AccountCase accountCase){
        final RedissonClient redissonClient = ReportRedisAccess.INSTANCE.getRedissonClient();
        final String key = "report:" + accountCase.getId();
        final RBucket<AccountCase> accountCaseRBucket = redissonClient.getBucket(key);
        accountCaseRBucket.deleteAsync();
    }

    public void clearReports(){
        final RedissonClient redissonClient = ReportRedisAccess.INSTANCE.getRedissonClient();
        final Iterable<String> keys = redissonClient.getKeys().getKeysByPattern("report:*");
        for(String key : keys){
            redissonClient.getBucket(key).delete();
        }
    }

}
