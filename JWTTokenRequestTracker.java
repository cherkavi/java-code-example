import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Provider
@Priority(Priorities.USER)
public class JWTTokenRequestTracker implements ContainerRequestFilter {


    static Cache<String, UserTracking> jwtUserCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(600, TimeUnit.SECONDS)
            .build();

    public boolean isTokenCached(UserTracking userTracking) {
        List<UserTracking> userTrackingCacheFilterList = jwtUserCache.asMap().values().stream()
                .filter(userTracking1 ->
                        userTracking1.getUserName().equals(userTracking.getUserName())
                                && userTracking1.getExpirationTime().equals(userTracking.getExpirationTime()))
                .collect(Collectors.toList());
        if (userTrackingCacheFilterList.isEmpty()) {
            addUserTokenToCache(userTracking);
            return false;
        }
        return true;
    }

}
