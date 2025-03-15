package com.minhabarbearia.barbearia.services.impl;

import com.minhabarbearia.barbearia.dto.UsuarioDTO;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.models.repository.UsuarioRepository;
import com.minhabarbearia.barbearia.services.UsuarioService;
import com.minhabarbearia.barbearia.services.query.UsuarioServiceQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public UsuarioEntity atualizar(Long id, UsuarioDTO usuarioDTO) {
        serviceQuery.verifyEmail(id, usuarioDTO.getEmail());
        serviceQuery.verifyPhone(id, usuarioDTO.getPhone());

        UsuarioEntity stored = repository.findById(usuarioDTO.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        stored.setName(usuarioDTO.getName());
        stored.setEmail(usuarioDTO.getEmail());
        stored.setPhone(usuarioDTO.getPhone());
        stored.setPassword(usuarioDTO.getPassword());
        stored.setRole(usuarioDTO.getRole());

        return repository.save(stored);
    }

    @Override
    public void delete(long id) {
        serviceQuery.obterPorId(id);
        repository.deleteById(id);

    }

    @Override
    public Optional<UsuarioEntity> obterPorId(Long id) {
        return repository.findById(id);
    }
}
