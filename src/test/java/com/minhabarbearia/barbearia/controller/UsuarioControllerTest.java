package com.minhabarbearia.barbearia.controller;

import com.minhabarbearia.barbearia.dto.AuthenticationDTO;
import com.minhabarbearia.barbearia.dto.LoginResponseDTO;
import com.minhabarbearia.barbearia.dto.UsuarioDTO;
import com.minhabarbearia.barbearia.exception.RegraNegocioException;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.models.enums.Role;
import com.minhabarbearia.barbearia.services.TokenService;
import com.minhabarbearia.barbearia.services.UsuarioService;
import com.minhabarbearia.barbearia.services.query.UsuarioServiceQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService service;

    @Mock
    private UsuarioServiceQuery serviceQuery;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UsuarioController usuarioController;

    private UsuarioDTO usuarioDTO;
    private UsuarioEntity usuarioEntity;
    private AuthenticationDTO authDTO;

    @BeforeEach
    void setUp() {
        usuarioDTO = UsuarioDTO.builder()
                .name("João Silva")
                .email("joao@example.com")
                .phone("11999999999")
                .password("senha123")
                .role(Role.BARBEIRO)
                .build();

        usuarioEntity = UsuarioEntity.builder()
                .id(1L)
                .name("João Silva")
                .email("joao@example.com")
                .phone("11999999999")
                .password("senha123")
                .role(Role.BARBEIRO)
                .build();

        authDTO = new AuthenticationDTO("joao@example.com", "senha123");
    }





    @Test
    void salvar_ComDadosValidos_DeveRetornarCreated() {
        when(service.salvarUsuario(any(UsuarioDTO.class))).thenReturn(usuarioEntity);

        ResponseEntity<?> response = usuarioController.salvar(usuarioDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(usuarioEntity, response.getBody());
    }

    @Test
    void salvar_ComEmailExistente_DeveRetornarBadRequest() {
        when(service.salvarUsuario(any(UsuarioDTO.class)))
                .thenThrow(new RegraNegocioException("Usuário já cadastrado com este e-mail."));

        ResponseEntity<?> response = usuarioController.salvar(usuarioDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Usuário já cadastrado com este e-mail.", response.getBody());
    }

    @Test
    void atualizar_ComDadosValidos_DeveRetornarOk() {
        when(service.atualizar(anyLong(), any(UsuarioDTO.class))).thenReturn(usuarioEntity);

        ResponseEntity<?> response = usuarioController.atualizar(1L, usuarioDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarioEntity, response.getBody());
    }

    @Test
    void atualizar_ComIdInvalido_DeveRetornarBadRequest() {
        when(service.atualizar(anyLong(), any(UsuarioDTO.class)))
                .thenThrow(new RegraNegocioException("Usuário não encontrado na base de Dados."));

        ResponseEntity<?> response = usuarioController.atualizar(99L, usuarioDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Usuário não encontrado na base de Dados.", response.getBody());
    }

    @Test
    void deletar_ComIdExistente_DeveRetornarNoContent() {
        when(service.obterPorId(1L)).thenReturn(Optional.of(usuarioEntity));
        doNothing().when(service).delete(1L);

        ResponseEntity<?> response = usuarioController.deletar(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deletar_ComIdInexistente_DeveRetornarBadRequest() {
        when(service.obterPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = usuarioController.deletar(99L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Usuário não encontrado na base de Dados.", response.getBody());
    }

    @Test
    void obterUsuario_ComIdExistente_DeveRetornarUsuario() {
        when(service.obterPorId(1L)).thenReturn(Optional.of(usuarioEntity));

        ResponseEntity<?> response = usuarioController.obterUsuario(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(usuarioEntity), response.getBody());
    }

    @Test
    void obterUsuario_ComIdInexistente_DeveRetornarNotFound() {
        when(service.obterPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = usuarioController.obterUsuario(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void obterTodosBarbeiros_QuandoExistirBarbeiros_DeveRetornarLista() {
        when(serviceQuery.findAllBarbeiros()).thenReturn(Collections.singletonList(usuarioEntity));

        ResponseEntity<List<UsuarioEntity>> response = usuarioController.obterTodosBarbeiros();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void obterTodosBarbeiros_QuandoNaoExistirBarbeiros_DeveRetornarNotFound() {
        when(serviceQuery.findAllBarbeiros()).thenReturn(Collections.emptyList());

        ResponseEntity<List<UsuarioEntity>> response = usuarioController.obterTodosBarbeiros();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}