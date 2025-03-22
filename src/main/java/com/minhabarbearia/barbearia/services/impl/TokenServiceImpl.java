package com.minhabarbearia.barbearia.services.impl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.services.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${spring.api.security.token.secret}")
    private String secret;

    public  String generateToken(UsuarioEntity user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withClaim("id", user.getId())
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateexpiratinDate())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException exception){
            throw  new RuntimeException("Erro na geração de Token", exception);
        }
    }




    public String validarTOken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return null;
        }
    }

    private Instant generateexpiratinDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
