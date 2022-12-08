package com.redislabs.university.RU102J.dao;

import com.redislabs.university.RU102J.core.KeyHelper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.time.ZonedDateTime;
import java.util.Random;

public class RateLimiterSlidingDaoRedisImpl implements RateLimiter {

    private final JedisPool jedisPool;
    private final long windowSizeMS;
    private final long maxHits;

    public RateLimiterSlidingDaoRedisImpl(JedisPool pool, long windowSizeMS,
                                          long maxHits) {
        this.jedisPool = pool;
        this.windowSizeMS = windowSizeMS;
        this.maxHits = maxHits;
    }

    /**
     * sliding window rate limiter
     * @param name
     * @throws RateLimitExceededException
     */
    @Override
    public void hit(String name) throws RateLimitExceededException {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = getKey(name);
            Pipeline pipeline = jedis.pipelined();
            final long currentTimestamp = System.currentTimeMillis();
            pipeline.zadd(key, currentTimestamp, String.valueOf(currentTimestamp + new Random().nextInt(1000)));
            pipeline.zremrangeByScore(key, 0, currentTimestamp - this.windowSizeMS);
            Response<Long> hits = pipeline.zcard(key);
            pipeline.sync();
            if (hits.get() > maxHits) {
                throw new RateLimitExceededException();
            }
        }
    }

    /**
     * @param userId
     * @return limiter:[windowSize]:[name]:[max-hits]
     */
    private String getKey(String userId) {
        return KeyHelper.getKey("limiter"
                + ":" + String.valueOf(this.windowSizeMS)
                + ":" + userId
                + ":" + String.valueOf(maxHits));
    }

}