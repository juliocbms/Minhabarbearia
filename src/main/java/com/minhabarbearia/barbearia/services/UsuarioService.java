package com.minhabarbearia.barbearia.services;

import com.minhabarbearia.barbearia.dto.UsuarioDTO;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UsuarioService {

    UsuarioEntity autenticar(String email, String senha);

    UsuarioEntity salvarUsuario(UsuarioDTO usuarioDTO);

    UsuarioEntity atualizar(Long id, UsuarioDTO usuarioDTO);

    void delete( long id);

    Optional<UsuarioEntity> obterPorId(Long id);
}
