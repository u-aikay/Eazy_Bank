package com.eazybank.config.security;

import com.eazybank.services.BlacklistService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class JWTAuthenticatorFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final BlacklistService blackListService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getJwtFromRequest(request);

        if(token == null){
            filterChain.doFilter(request, response);
            return;
        }

        if(blackListService.isTokenBlackListed(token)){
            throw new BadCredentialsException("Token is blacklisted");
        }

        Jws<Claims> claimsJws = jwtTokenProvider.validateToken(token);
        Claims claims = claimsJws.getBody();
        String email = claims.getSubject();
        Set<GrantedAuthority> grantedAuthorities = getGrantedAuthoritiesFromClaims(claims);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                email, null, grantedAuthorities
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    private Set<GrantedAuthority>  getGrantedAuthoritiesFromClaims(Claims claim){
        List<Map<String, String>> authorities = (List<Map<String, String>>) claim.get("Authorities");
        return authorities.stream()
                .map(authority ->
                        new SimpleGrantedAuthority(authority.get("authority"))).collect(Collectors.toSet());
    }
}
