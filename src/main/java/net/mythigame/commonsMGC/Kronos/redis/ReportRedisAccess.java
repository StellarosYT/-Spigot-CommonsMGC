package net.mythigame.commonsMGC.Kronos.redis;

import net.mythigame.commons.Storage.Redis.RedisCredentials;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

public class ReportRedisAccess {

    public static ReportRedisAccess INSTANCE;
    private final RedissonClient redissonClient;

    public ReportRedisAccess(RedisCredentials credentials) {
        INSTANCE = this;
        this.redissonClient = initRedisson(credentials);
    }

    public static void init(){
        new ReportRedisAccess(new RedisCredentials("localhost", "KoPxS6PW?#?JzYh70dUI", 6379));
    }

    public static void close(){
        ReportRedisAccess.INSTANCE.getRedissonClient().shutdown();
    }

    public RedissonClient initRedisson(RedisCredentials credentials){
        final Config config = new Config();

        config.setCodec(new JsonJacksonCodec());
        config.setThreads(2 * 2); // nombre de coeur x 2
        config.setNettyThreads(2 * 2); // pareil qu'au dessus
        config.useSingleServer()
                .setAddress(credentials.toRedisURI())
                .setPassword(credentials.getPassword())
                .setDatabase(2)
                .setClientName(credentials.getClientName());

        return Redisson.create(config);
    }

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }

}
