package com.redislabs.university.RU102J.dao;

import com.redislabs.university.RU102J.api.MeterReading;
import redis.clients.jedis.*;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FeedDaoRedisImpl implements FeedDao {

    private final JedisPool jedisPool;
    private static final long globalMaxFeedLength = 10000;
    private static final long siteMaxFeedLength = 2440;

    public FeedDaoRedisImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    // Challenge #6
    @Override
    public void insert(MeterReading meterReading) {
        String siteKey = RedisSchema.getFeedKey(meterReading.getSiteId());
        String globalKey = RedisSchema.getGlobalFeedKey();
        Map<String, String> data = meterReading.toMap();

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.xadd(siteKey, StreamEntryID.NEW_ENTRY, data, siteMaxFeedLength, false);
            jedis.xadd(globalKey, StreamEntryID.NEW_ENTRY, data, globalMaxFeedLength, false);
        }
    }

    @Override
    public List<MeterReading> getRecentGlobal(int limit) {
        return getRecent(RedisSchema.getGlobalFeedKey(), limit);
    }

    @Override
    public List<MeterReading> getRecentForSite(long siteId, int limit) {
        return getRecent(RedisSchema.getFeedKey(siteId), limit);
    }

    public List<MeterReading> getRecent(String key, int limit) {
        List<MeterReading> readings = new ArrayList<>(limit);
        try (Jedis jedis = jedisPool.getResource()) {
            List<StreamEntry> entries = jedis.xrevrange(key, null,
                    null, limit);
            for (StreamEntry entry : entries) {
                readings.add(new MeterReading(entry.getFields()));
            }
            return readings;
        }
    }
}
