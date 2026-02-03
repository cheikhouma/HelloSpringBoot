package sn.edu.ept.git.dic2.HelloSpringBoot.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sn.edu.ept.git.dic2.HelloSpringBoot.entities.Employe;

import java.io.IOException;
import java.util.List;

@Component
public class CustomSecurityFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

//      Recuperer l'entete authorization
//        Extraire le token
//        verifier que le token est valide
//        Tu recuperes le proprietaire du token
//        et tu l'injectes la.
        if (request.getParameter("email") != null) {
            String email = request.getParameter("email");
            Employe e = new Employe();
            e.setPrenom("Maimouna");
            e.setNom("Ndiaye");
            e.setSalary(500.0);

            List<GrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_EMPLOYE"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );

            // Authentifier l'utilisateur
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(e, email, authorities));
        } else {
            logger.warn("L'utilisateur ne s'est pas authentifié");
        }
        filterChain.doFilter(request, response);
    }
}
