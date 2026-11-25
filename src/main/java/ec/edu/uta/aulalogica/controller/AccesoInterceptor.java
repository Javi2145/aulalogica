package ec.edu.uta.aulalogica.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/** Si no hay sesión, envía al formulario de acceso. */
public class AccesoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        if (req.getSession().getAttribute(SesionUtil.CLAVE) == null) {
            res.sendRedirect(req.getContextPath() + "/acceso");
            return false;
        }
        return true;
    }
}
