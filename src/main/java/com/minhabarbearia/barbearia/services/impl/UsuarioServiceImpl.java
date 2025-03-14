package com.minhabarbearia.barbearia.services.impl;

import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.models.repository.UsuarioRepository;
import com.minhabarbearia.barbearia.services.UsuarioService;
import com.minhabarbearia.barbearia.services.query.UsuarioServiceQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioServiceQuery serviceQuery;

    @Override
    public UsuarioEntity autenticar(String email, String senha) {
        return null;
    }

    @Override
    public UsuarioEntity salvarUsuario(UsuarioEntity usuario) {
        serviceQuery.verifyEmail(usuario.getEmail());
        serviceQuery.verifyPhone(usuario.getPhone());

        return  repository.save(usuario);
    }

    @Override
    public UsuarioEntity atualizar(UsuarioEntity usuario) {
        return null;
    }
}
