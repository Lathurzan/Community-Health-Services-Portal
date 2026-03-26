package com.example.chaspjava.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import io.jsonwebtoken.JwtException;

class JwtTokenProviderTest {

    @Test
    void generateToken_then_getUserId_returnsSameUserId() {
        JwtTokenProvider provider = new JwtTokenProvider();

        String userId = "user-123";
        String token = provider.generateToken(userId, "test@example.com", "USER");

        assertNotNull(token, "Generated token should not be null");

        String parsedUserId = provider.getUserId(token);
        assertEquals(userId, parsedUserId, "Parsed userId must match the original");
    }

    @Test
    void getUserId_withInvalidToken_throwsJwtException() {
        JwtTokenProvider provider = new JwtTokenProvider();

        assertThrows(JwtException.class, () -> provider.getUserId("this.is.not.a.valid.token"));
    }
}
