package web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import web.integration.OfertaIntegrationService;

public class UnlockOfferInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	OfertaIntegrationService integrationService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String uri = request.getRequestURI();
		System.out.println(uri);
		if (!uri.matches("/web-app/sales/\\d+") && !uri.contains("resources") && !uri.contains("process")) {
			Long lockId = (Long) request.getSession().getAttribute("lockId");
			if (lockId != null) {
				integrationService.setLock(lockId, false);
				System.out.println("unlocked " + lockId);
				request.getSession().removeAttribute("lockId");
			}
		}
		
		
		
		return true;
	}

}
