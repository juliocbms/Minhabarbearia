package com.minhabarbearia.barbearia.dto;

import com.minhabarbearia.barbearia.services.JwtService;
import com.minhabarbearia.barbearia.services.impl.SecurityUserDetailsService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private SecurityUserDetailsService userDatailsService;

    public JwtTokenFilter(
            JwtService jwtService,
            SecurityUserDetailsService userDetailsService
    ){
        this.jwtService = jwtService;
        this.userDatailsService = userDetailsService;

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, java.io.IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer")) {
            String token = authorization.split(" ")[1];
            System.out.println("Token recebido: " + token);
            boolean isTokenValid = jwtService.isTokenValido(token);

            if (isTokenValid) {
                String login = jwtService.obterLoginUsuraio(token);
                UserDetails usuarioAutenticado = userDatailsService.loadUserByUsername(login);
                UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
                        usuarioAutenticado, null, usuarioAutenticado.getAuthorities());
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(user);
            } else {
                System.out.println("Token inválido ou expirado.");
            }
        }
        filterChain.doFilter(request, response);
    }
}
