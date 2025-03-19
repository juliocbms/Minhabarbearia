package com.minhabarbearia.barbearia.services;

import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;

public interface TokenService {
    String generateToken(UsuarioEntity user);
    String validarTOken(String token);
}
