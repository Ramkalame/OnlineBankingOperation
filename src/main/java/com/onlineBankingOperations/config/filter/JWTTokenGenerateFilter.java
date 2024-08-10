package com.onlineBankingOperations.config.filter;

import com.onlineBankingOperations.config.filter.constant.JWTSecurityConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTTokenGenerateFilter extends OncePerRequestFilter {
    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //first we  need to load the current authentication object means current logged in user
        Authentication currentAuthenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        //here we checking the user is present or not if yes then we have to generate the token if no then simple called the dofilter method
        if(currentAuthenticatedUser != null){
            SecretKey key = Keys.hmacShaKeyFor(JWTSecurityConstant.JWT_SECRET_DEFAULT_VALUE.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder()
                    .issuer("Online Bank")
                    .subject("JWT Token")
                    .claim("username", currentAuthenticatedUser.getName())
                    .claim("authorities", currentAuthenticatedUser.getAuthorities()
                            .stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .issuedAt(new Date())
                    .expiration(new Date(new Date().getTime() + 30000000))
                    .signWith(key)
                    .compact();
            response.setHeader(JWTSecurityConstant.JWT_HEADER, jwt);

        }
        filterChain.doFilter(request, response);

    }
    // if the shouldNotFilter method return true the above JWTTokeGenerateFilter method not execute for those ture condtion
    //This filter only execute only once during the login operation
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/client/login");
    }

//    private String populatedAuthorities(Collection<? extends GrantedAuthority> authorities){
//        Set<String> authoritiesSet = new HashSet<>();
//        for (GrantedAuthority authority:authorities){
//            authoritiesSet.add(authority.getAuthority());
//        }
//        return String.join(",", authoritiesSet);
//    }
}
