package com.minhabarbearia.barbearia.services.impl;

import com.minhabarbearia.barbearia.dto.UsuarioDTO;
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
    public UsuarioEntity salvarUsuario(UsuarioDTO usuarioDTO) {
        UsuarioEntity usuario = UsuarioEntity.builder()
                .name(usuarioDTO.getName())
                .email(usuarioDTO.getEmail())
                .phone(usuarioDTO.getPhone())
                .password(usuarioDTO.getPassword())
                .role(usuarioDTO.getRole())
                .build();
        serviceQuery.verifyEmail(usuarioDTO.getEmail());
        serviceQuery.verifyPhone(usuarioDTO.getPhone());

        return  repository.save(usuario);
    }

    @Override
    public UsuarioEntity atualizar(UsuarioEntity usuario) {
        return null;
    }
}
