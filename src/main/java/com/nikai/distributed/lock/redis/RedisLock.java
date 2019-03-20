package com.nikai.distributed.lock.redis;

import java.util.Collections;

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

    public static boolean tryLock(final String key, final String value) {
        while (true) {
            Long lock = JedisUtils.setnx(key, value);
            if (lock == 1) {
                return true;
            }
        }
    }

    public static void lock(final String key, final String value, final int cacheSeconds) {
        if (tryLock(key, value)) {
            JedisUtils.setexpire(key, value, cacheSeconds);
        }
    }

    public static void unlock(final String key, final String value) {
        JedisUtils.eval(lua, Collections.singletonList(key), Collections.singletonList(value));
    }
}
