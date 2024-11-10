package com.yummy.puding.genshin.database.auth.config.security;

import com.yummy.puding.genshin.database.auth.exception.CustomException;
import com.yummy.puding.genshin.database.auth.service.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

//@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final Logger log = LogManager.getLogger(JwtAuthFilter.class);

//    @Autowired
    private final JwtUtil jwtUtil;

//    @Autowired
    private final UserDetailServiceImpl userDetailServiceImpl;

    public JwtAuthFilter(JwtUtil jwtUtil, UserDetailServiceImpl userDetailServiceImpl) {
        this.jwtUtil = jwtUtil;
        this.userDetailServiceImpl = userDetailServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String headerAuth = request.getHeader("Authorization");

            String jwt = "";
            if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
                jwt = headerAuth.substring(7);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED, "Header auth not provided");
            }

            if (StringUtils.hasText(jwt) && jwtUtil.validateJwtToken(jwt)) {

                String username = jwtUtil.getUsernameFromJwtToken(jwt);

                UserDetails userDetail = userDetailServiceImpl.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( userDetail, null, userDetail.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED, "Authorization token not valid");
            }

            filterChain.doFilter(request, response);
        } catch (CustomException ce) {
            log.error("Authentication failed");
            response.setStatus(ce.getHttpStatus().value());
            response.getWriter().write(ce.getMessage());
            response.getWriter().flush();
        } catch (UsernameNotFoundException usernameNotFoundException) {
            log.error("Username not found in token");
            String responseBody = "resp";
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(responseBody);
            response.getWriter().flush();
        } catch (Exception e) {
            log.error("Request not authenticated");
            String responseBody = "resp";
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(responseBody);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        List<String> excludeUrlPatterns = List.of(
            "/v1/user/register",
            "/v1/auth/login"
        );
        return excludeUrlPatterns.stream().anyMatch(path -> pathMatcher.match(path, request.getServletPath()));
    }
}
