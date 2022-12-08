package com.redislabs.university.RU102J.dao;

public interface RateLimiter {
    /**
     *
     * @param userId - user identifier
     * @throws RateLimitExceededException - user by id exceed the rate limits
     */
    void hit(String userId) throws RateLimitExceededException;
}
