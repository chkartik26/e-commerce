package com.example.e_commerce.security.jwt;

import com.example.e_commerce.security.user.MyUserDetailsService;
import com.example.e_commerce.security.user.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;
    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //request that will come= Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdm5pIiwiaWF0IjoxNzI3NzYxNTY2LCJleHAiOjE3Mjc3NjE2NzR9.h_7I1N-jWKQ1lkhG8bwyD5rn2-AFPNSvRjx_pQKlKlY
        String authHeader=request.getHeader("Authorization");
        String token=null;
        String email=null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token=authHeader.substring(7);
            email=jwtService.extractEmail(token);
        }
        if(email !=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=context.getBean(MyUserDetailsService.class).loadUserByUsername(email);
            if(jwtService.validateToken(token,(UserPrincipal)userDetails)){
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
