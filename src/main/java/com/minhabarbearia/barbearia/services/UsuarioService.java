package com.minhabarbearia.barbearia.services;

import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UsuarioService {

    UsuarioEntity autenticar(String email, String senha);

    UsuarioEntity salvarUsuario(UsuarioEntity usuario);

    UsuarioEntity atualizar( UsuarioEntity usuario);


}
