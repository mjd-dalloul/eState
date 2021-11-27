package com.example.spring_tutorial.configuration.security;

import com.example.spring_tutorial.domain.dto.auth_dto.CustomUserSecurity;
import com.example.spring_tutorial.domain.entity.ApplicationUser;
import com.example.spring_tutorial.repository.ApplicationUserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final ApplicationUserRepository userRepo;

    public JwtFilter(JwtTokenUtil jwtTokenUtil,
                     ApplicationUserRepository userRepo) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context
        ApplicationUser user = userRepo
                .findByEmail(jwtTokenUtil.getUserEmail(token))
                .orElse(null);
        if (user == null) {
            chain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = new CustomUserSecurity(user);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                new ArrayList<>()
                //Optional.of(userDetails).map(UserDetails::getAuthorities).orElse(of())
        );

//        authentication.setDetails(
//                new WebAuthenticationDetailsSource().buildDetails(request)
//        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}