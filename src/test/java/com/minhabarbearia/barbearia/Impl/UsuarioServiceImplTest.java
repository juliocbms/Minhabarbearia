package com.minhabarbearia.barbearia.Impl;

import com.minhabarbearia.barbearia.dto.UsuarioDTO;
import com.minhabarbearia.barbearia.exception.RegraNegocioException;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.models.enums.Role;
import com.minhabarbearia.barbearia.models.repository.UsuarioRepository;
import com.minhabarbearia.barbearia.services.impl.UsuarioServiceImpl;
import com.minhabarbearia.barbearia.services.query.UsuarioServiceQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UsuarioServiceImplTest {
    @InjectMocks
    private UsuarioServiceImpl service;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private UsuarioServiceQuery serviceQuery;

    @Mock
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        UsuarioDTO dto = UsuarioDTO.builder()
                .name("João")
                .email("joao@email.com")
                .phone("99999-9999")
                .password("senha123")
                .role(Role.CLIENTE)
                .build();

        when(repository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(encoder.encode(dto.getPassword())).thenReturn("senhaCriptografada");

        UsuarioEntity usuarioSalvo = UsuarioEntity.builder()
                .id(1L)
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .password("senhaCriptografada")
                .role(dto.getRole())
                .build();

        when(repository.save(any())).thenReturn(usuarioSalvo);

        UsuarioEntity resultado = service.salvarUsuario(dto);

        assertNotNull(resultado);
        assertEquals(dto.getEmail(), resultado.getEmail());
        verify(repository).save(any());
    }

    @Test
    void deveLancarErroAoSalvarUsuarioEmailJaCadastrado() {
        UsuarioDTO dto = UsuarioDTO.builder()
                .name("João")
                .email("joao@email.com")
                .phone("99999-9999")
                .password("senha123")
                .role(Role.CLIENTE)
                .build();

        when(repository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new UsuarioEntity()));

        assertThrows(RegraNegocioException.class, () -> service.salvarUsuario(dto));
    }

    @Test
    void deveAutenticarUsuarioComSucesso() {
        UsuarioEntity user = UsuarioEntity.builder()
                .email("joao@email.com")
                .password("senhaCriptografada")
                .build();

        when(repository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(encoder.matches("senha123", "senhaCriptografada")).thenReturn(true);

        UsuarioEntity resultado = service.autenticar(user.getEmail(), "senha123");

        assertNotNull(resultado);
        assertEquals(user.getEmail(), resultado.getEmail());
    }

    @Test
    void deveLancarErroQuandoSenhaIncorreta() {
        UsuarioEntity user = UsuarioEntity.builder()
                .email("joao@email.com")
                .password("senhaCriptografada")
                .build();

        when(repository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(encoder.matches("senhaErrada", "senhaCriptografada")).thenReturn(false);

        assertThrows(RegraNegocioException.class, () -> service.autenticar(user.getEmail(), "senhaErrada"));
    }

    @Test
    void deveLancarErroQuandoEmailNaoExiste() {
        when(repository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        assertThrows(RegraNegocioException.class, () -> service.autenticar("naoexiste@email.com", "senha123"));
    }

}
