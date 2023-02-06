// servlet filter, apply filter only for endpoint with annotation
import com.ubs.jetty.api.rest.security.KeyCloakJWTBody;
import com.ubs.jetty.api.rest.usage.JWTTokenTracking;
import com.ubs.jetty.authentication.AuthenticationUtil;
import com.ubs.metrics.postgres.usertracking.UserTracking;
import com.ubs.metrics.postgres.usertracking.UserTrackingDAO;
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
import lombok.Setter;

@Provider
@JWTTokenTracking // filter will work only for methods with this annotation
@Priority(Priorities.USER)
public class JWTTokenRequestTracker implements ContainerRequestFilter {

  @Context @Setter private HttpServletRequest request;

  static Cache<String, UserTracking> jwtUserCache = CacheBuilder.newBuilder().maximumSize(2000).expireAfterWrite(360, TimeUnit.SECONDS).build();

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    KeyCloakJWTBody keyCloakJWTBody = AuthenticationUtil.getKeyCloakJWTBodyFromRequest(request);
    ...
  }

}
