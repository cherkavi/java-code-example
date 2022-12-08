package com.redislabs.university.RU102J.dao;

import com.redislabs.university.RU102J.api.Coordinate;
import com.redislabs.university.RU102J.api.GeoQuery;
import com.redislabs.university.RU102J.api.Site;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisDataException;

import java.util.*;
import java.util.stream.Collectors;

public class SiteGeoDaoRedisImpl implements SiteGeoDao {
    private JedisPool jedisPool;
    final static private Double capacityThreshold = 0.2;

    public SiteGeoDaoRedisImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public Site findById(long id) {
        try (Jedis jedis = jedisPool.getResource()) {
            Map<String, String> fields =
                    jedis.hgetAll(RedisSchema.getSiteHashKey(id));
            if (fields == null || fields.isEmpty()) {
                return null;
            }
            return new Site(fields);
        }
    }

    @Override
    public Set<Site> findAll() {
        try (Jedis jedis = jedisPool.getResource()) {
            Set<String> keys = jedis.zrange(RedisSchema.getSiteGeoKey(), 0, -1);
            Set<Site> sites = new HashSet<>(keys.size());
//            for (String key : keys) {
//                Map<String, String> site = jedis.hgetAll(key);
//                if (!site.isEmpty()) {
//                    sites.add(new Site(site));
//                }
//            }

            Pipeline pipeline = jedis.pipelined();
            Set<Response<Map<String, String>>> request = keys.stream().map(pipeline::hgetAll).collect(Collectors.toSet());
            pipeline.sync();

            return request.stream()
                    .map(this::getResponse)
                    .filter(value-> (value!=null && !value.isEmpty()))
                    .map(Site::new)
                    .collect(Collectors.toSet());
        }
    }

    private Map<String, String> getResponse(Response<Map<String, String>> response){
        try{
            return response.get();
        }catch(JedisDataException ex){
            return null;
        }
    }

    @Override
    public Set<Site> findByGeo(GeoQuery query) {
        if (query.onlyExcessCapacity()) {
            return findSitesByGeoWithCapacity(query);
        } else {
            return findSitesByGeo(query);
        }
    }

    // Challenge #5
//    private Set<Site> findSitesByGeoWithCapacity(GeoQuery query) {
//        return Collections.emptySet();
//    }
    private Set<Site> findSitesByGeoWithCapacity(GeoQuery query) {
        Set<Site> results = new HashSet<>();
        Coordinate coord = query.getCoordinate();
        Double radius = query.getRadius();
        GeoUnit radiusUnit = query.getRadiusUnit();

         try (Jedis jedis = jedisPool.getResource()) {
             // START Challenge #5
             List<GeoRadiusResponse> radiusResponses =
                     jedis.georadius(RedisSchema.getSiteGeoKey(), coord.getLng(), coord.getLat(), radius, radiusUnit);
             // END Challenge #5

             Set<Site> sites = radiusResponses.stream()
                     .map(response -> jedis.hgetAll(response.getMemberByString()))
                     .filter(Objects::nonNull)
                     .map(Site::new).collect(Collectors.toSet());

             // START Challenge #5
             Pipeline pipeline = jedis.pipelined();
             Map<Long, Response<Double>> scores = new HashMap<>(sites.size());
             for (Site eachSite: sites){
                 scores.put(eachSite.getId(), pipeline.zscore(RedisSchema.getCapacityRankingKey(), eachSite.getId().toString()));
             }
             pipeline.sync();
             // END Challnege #5

             for (Site site : sites) {
                 if (scores.get(site.getId()).get() >= capacityThreshold) {
                     results.add(site);
                 }
             }
         }
         return results;
    }

    private Set<Site> findSitesByGeo(GeoQuery query) {
        Coordinate coord = query.getCoordinate();
        Double radius = query.getRadius();
        GeoUnit radiusUnit = query.getRadiusUnit();

        try (Jedis jedis = jedisPool.getResource()) {
            List<GeoRadiusResponse> radiusResponses =
                    jedis.georadius(RedisSchema.getSiteGeoKey(), coord.getLng(),
                            coord.getLat(), radius, radiusUnit);

            return radiusResponses.stream()
                    .map(response -> jedis.hgetAll(response.getMemberByString()))
                    .filter(Objects::nonNull)
                    .map(Site::new).collect(Collectors.toSet());
        }
    }

    @Override
    public void insert(Site site) {
         try (Jedis jedis = jedisPool.getResource()) {
             String key = RedisSchema.getSiteHashKey(site.getId());
             jedis.hmset(key, site.toMap());

             if (site.getCoordinate() == null) {
                 throw new IllegalArgumentException("Coordinate required for Geo " +
                         "insert.");
             }
             Double longitude = site.getCoordinate().getGeoCoordinate().getLongitude();
             Double latitude = site.getCoordinate().getGeoCoordinate().getLatitude();
             jedis.geoadd(RedisSchema.getSiteGeoKey(), longitude, latitude,
                     key);
         }
    }
}
