package cherkashyn.vitalii.tool.web.filter;

import javax.servlet.*;
import java.io.IOException;

public class LoggerListener implements ServletRequestListener, Filter {

	// --------------------- REQUEST listener ------------------
	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		System.out.println("Request destroyed: " + event);
	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		System.out.println("Request initialized: " + event);
	}

	// --------------------- FILTER part ------------------

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
		System.out.println("FILTER request: " + request);
		chain.doFilter(request, response);
		System.out.println("FILTER response: " + response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {

	}

}
