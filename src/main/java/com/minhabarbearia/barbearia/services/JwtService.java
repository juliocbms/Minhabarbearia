package com.minhabarbearia.barbearia.services;

import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

public interface JwtService {

    String gerarToken(UsuarioEntity usuario);

    Claims obterClaims(String token) throws ExpiredJwtException;

    boolean isTokenValido(String token);

    String obterLoginUsuraio(String token);
}