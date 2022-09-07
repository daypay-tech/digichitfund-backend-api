package com.daypaytechnologies.digichitfund.security.filters;

import com.daypaytechnologies.digichitfund.security.AdministrationUserDetailsImpl;
import com.daypaytechnologies.digichitfund.security.AdministrationUserDetailsServiceImpl;
import com.daypaytechnologies.digichitfund.security.JwtTokenHandler;
import com.daypaytechnologies.digichitfund.security.MemberUserDetailsImpl;
import com.daypaytechnologies.digichitfund.security.MemberUserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenHandler jwtUtils;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String clientType = request.getHeader("X-CLIENT-ACCESS-TYPE");
                String userName = jwtUtils.getUserNameFromJwtToken(jwt);
                if(clientType != null && clientType.equals("administration_app")) {
                    UserDetailsService userDetailsService = (AdministrationUserDetailsServiceImpl) applicationContext.getBean("administrationUserDetailsService");
                    AdministrationUserDetailsImpl userDetails = (AdministrationUserDetailsImpl) userDetailsService.loadUserByUsername(userName);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    UserDetailsService userDetailsService = (MemberUserDetailsServiceImpl) applicationContext.getBean("memberUserDetailsService");
                    MemberUserDetailsImpl userDetails = (MemberUserDetailsImpl) userDetailsService.loadUserByUsername(userName);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}
