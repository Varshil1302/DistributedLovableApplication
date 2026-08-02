package com.example.distributed_lovable.APIGateway.api_gateway.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class GatewayAuthService
{
    @Value("${jwt.secretKey}")
    private String secretKey;

    public void validateToken(String token)
    {

        SecretKey secretKey1 = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        Jwts.parser()
                .verifyWith(secretKey1)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
}
