package com.minhabarbearia.barbearia.services.impl;

import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.services.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class JwtFilterImpl implements JwtService {
    @Value("${jwt.expiracao}")
    private  String expiracao;

    @Value("${jwt.chave-assinatura}")
    private  String chaveAssinatura;

    @Override
    public String gerarToken(UsuarioEntity usuario) {
        long exp = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(exp);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts.builder()
                .setExpiration(data)
                .setSubject(usuario.getEmail())
                .claim("nome",usuario.getName())
                .claim("usuario",usuario.getId() )
                .claim("horaExpiracao", dataHoraExpiracao.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .signWith(SignatureAlgorithm.HS256, chaveAssinatura)
                .compact();
    }

    @Override
    public Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    @Override
    public boolean isTokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date dataExp = claims.getExpiration();

            LocalDateTime dataExpiracao = dataExp
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            boolean dataHoraAtualIsAFTERDataExpiracao=  LocalDateTime.now().isAfter(dataExpiracao);
            return !dataHoraAtualIsAFTERDataExpiracao;
        }catch (ExpiredJwtException e){
            return false;
        }


    }

    @Override
    public String obterLoginUsuraio(String token) {
        Claims claims= obterClaims(token);
        return claims.getSubject();
    }
}
