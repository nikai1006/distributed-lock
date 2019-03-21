package com.nikai.distributed.lock.redis;

import java.util.Collections;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

/**
 * distributed-lock com.nikai.distributed.lock.redis
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 1:49 2019/3/21
 * @Modified By:
 */
public class RedisLock {

    public static final String lua = "if redis.call('get', KEYS[1]) == ARGV[1]  then   return redis.call('del', KEYS[1]) else return 0 end";

    ThreadLocal<Jedis> redis = new ThreadLocal<Jedis>();

    public void init() {
        redis.set(new Jedis("nikai.org"));
    }


    @Autowired
    private RedisTemplate redisTemplate;

    public boolean tryLock(final String key, final String value) {
        while (true) {
            Long lock = redis.get().setnx(key, value);
            if (lock == 1) {
                return true;
            }
        }
    }

    public void lock(final String key, final String value, final int cacheSeconds) {
        init();
        if (tryLock(key, value)) {
            redis.get().set(key, value, SetParams.setParams().nx().px(cacheSeconds));
        }
    }

    public void unlock(final String key, final String value) {
        redis.get().eval(lua, Collections.singletonList(key), Collections.singletonList(value));
    }
}
