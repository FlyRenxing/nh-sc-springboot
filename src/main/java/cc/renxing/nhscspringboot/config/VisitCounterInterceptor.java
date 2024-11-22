package cc.renxing.nhscspringboot.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class VisitCounterInterceptor implements HandlerInterceptor {

    private int visitCount = 0; // 全局计数器

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("hasVisited") == null) {
            synchronized (this) {
                visitCount++;
            }
            session.setAttribute("hasVisited", true);
        }
        if (modelAndView != null) {
            modelAndView.addObject("visitCount", visitCount);
        }
    }
}
