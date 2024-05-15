package com.eledevo.integration.config.security;

import com.eledevo.integration.repository.TokenRepository;
import com.eledevo.integration.repository.redis.BaseRedisRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final BaseRedisRepository redisService;
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        var storedToken = redisService.get(userEmail); // Use 'get' instead of `hashExists`
        if (storedToken != null && storedToken.equals(jwt)) {
           redisService.delete(userEmail);
            SecurityContextHolder.clearContext();
        }
    }
}
