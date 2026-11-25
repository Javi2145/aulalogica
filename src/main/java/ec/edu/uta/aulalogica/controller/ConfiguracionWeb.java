package ec.edu.uta.aulalogica.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfiguracionWeb implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccesoInterceptor()).addPathPatterns(
            "/contenidos", "/leccion/**", "/practica/**", "/mecanografia/**",
            "/evaluaciones", "/evaluacion/**", "/progreso", "/perfil", "/configuracion", "/equipo", "/glosario");
    }
}
