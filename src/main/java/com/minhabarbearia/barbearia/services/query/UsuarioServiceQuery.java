package com.minhabarbearia.barbearia.services.query;

import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;

import java.util.Optional;

public interface UsuarioServiceQuery {

    Optional<UsuarioEntity> obterPorId(Long id);

    void validarEmail(String email);

    void verifyPhone(final String phone);


    void verifyEmail(final String email);



}
