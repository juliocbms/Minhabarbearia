package com.minhabarbearia.barbearia.controller;


import com.minhabarbearia.barbearia.dto.UsuarioDTO;
import com.minhabarbearia.barbearia.exception.RegraNegocioException;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.services.UsuarioService;
import com.minhabarbearia.barbearia.services.query.UsuarioServiceQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioServiceQuery serviceQuery;

    @PostMapping
    @Operation(summary = "salva usuarios", description = "metodo para salvar dados de usuarios")
    @ApiResponse(responseCode = "201" , description = "usuario salvo")
    @ApiResponse(responseCode = "400", description = "Usuario ja cadastrado")
    public ResponseEntity salvar(@RequestBody UsuarioDTO dto){
        UsuarioEntity usuario = UsuarioEntity.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
        try{
            UsuarioEntity usuarioSalvo = service.salvarUsuario(usuario);
            return  new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
