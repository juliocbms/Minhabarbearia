package com.minhabarbearia.barbearia.services.impl;

import com.minhabarbearia.barbearia.dto.UsuarioDTO;
import com.minhabarbearia.barbearia.exception.RegraNegocioException;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.models.repository.UsuarioRepository;

import com.minhabarbearia.barbearia.services.UsuarioService;
import com.minhabarbearia.barbearia.services.query.UsuarioServiceQuery;
import lombok.AllArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioServiceQuery serviceQuery;
    private final BCryptPasswordEncoder encoder;


    @Override
    public UsuarioEntity autenticar(String email, String password) {
        Optional<UsuarioEntity> user = repository.findByEmail(email);

        if(user.isEmpty()) {
            throw new RegraNegocioException("Usuário não encontrado para o email informado.");
        }

        boolean senhasBatem = encoder.matches(password,user.get().getPassword());

        if(!senhasBatem) {
            throw new RegraNegocioException("Senha inválida.");
        }

        return user.get();
    }




    @Override
    public UsuarioEntity salvarUsuario(UsuarioDTO usuarioDTO) {
        if (repository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new RegraNegocioException("Usuário já cadastrado com este e-mail.");
        }

        if (usuarioDTO.getPassword() == null || usuarioDTO.getPassword().trim().isEmpty()) {
            throw new RegraNegocioException("A senha não pode ser vazia.");
        }

        UsuarioEntity usuario = UsuarioEntity.builder()
                .name(usuarioDTO.getName())
                .email(usuarioDTO.getEmail())
                .phone(usuarioDTO.getPhone())
                .password(encoder.encode(usuarioDTO.getPassword()))
                .role(usuarioDTO.getRole())
                .build();


        serviceQuery.verifyPhone(usuarioDTO.getPhone());

        return repository.save(usuario);
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
        stored.setPassword(encoder.encode(usuarioDTO.getPassword()));
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
