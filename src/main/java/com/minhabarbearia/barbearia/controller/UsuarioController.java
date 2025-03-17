package com.minhabarbearia.barbearia.controller;


import com.minhabarbearia.barbearia.dto.TokenDTO;
import com.minhabarbearia.barbearia.dto.UsuarioDTO;
import com.minhabarbearia.barbearia.exception.RegraNegocioException;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.services.JwtService;
import com.minhabarbearia.barbearia.services.UsuarioService;
import com.minhabarbearia.barbearia.services.query.UsuarioServiceQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("usuarios")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioServiceQuery serviceQuery;
    private  final JwtService jwtService;

    @PostMapping("/autenticar")
    @Operation(summary = "autentica usuarios", description = "metodo para autenticar dados de usuarios")
    @ApiResponse(responseCode = "200" , description = "usuario autenticado")
    @ApiResponse(responseCode = "400", description = "Usuario não cadastrado")
    public ResponseEntity<?> autenticar(@RequestBody UsuarioDTO dto){
        try{
            UsuarioEntity usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getPassword());
            String token = jwtService.gerarToken(usuarioAutenticado);
            TokenDTO tokenDTO = new TokenDTO(usuarioAutenticado.getName(), token);
            return  ResponseEntity.ok(tokenDTO);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @PostMapping
    @Operation(summary = "salva usuarios", description = "metodo para salvar dados de usuarios")
    @ApiResponse(responseCode = "201" , description = "usuario salvo")
    @ApiResponse(responseCode = "400", description = "Usuario ja cadastrado")
    public ResponseEntity salvar(@RequestBody UsuarioDTO usuariodto){

        try{
            UsuarioEntity usuarioSalvo = service.salvarUsuario(usuariodto);
            return  new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }


    @PutMapping("{id}")
    @Operation(summary = "atualiza usuarios", description = "metodo para atualizar dados de usuarios")
    @ApiResponse(responseCode = "201" , description = "usuario atualizado")
    @ApiResponse(responseCode = "400", description = "erro")
    public ResponseEntity atualizar(@PathVariable("id")  long id, @RequestBody UsuarioDTO usuariodto){
        try {
            UsuarioEntity usuarioatualizado = service.atualizar(id, usuariodto);
            return ResponseEntity.ok(usuarioatualizado);
        } catch (RegraNegocioException e ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado na base de Dados.");
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "deleta usuarios", description = "metodo para deletar dados de usuarios")
    @ApiResponse(responseCode = "201" , description = "usuario deletado")
    @ApiResponse(responseCode = "400", description = "usuario nao encontrado")
    public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
        return service.obterPorId(id).map( entidade -> {
            service.delete(entidade.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }).orElseGet(() ->
                new ResponseEntity<>("Usuário não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
    }

    @GetMapping("{id}")
    @Operation(summary = "busca usuarios", description = "metodo para buscar dados de usuarios")
    @ApiResponse(responseCode = "201" , description = "usuario encontrado")
    @ApiResponse(responseCode = "400", description = "usuario nao encontrado")
    public ResponseEntity obterUsuario(@PathVariable("id") Long id){
        Optional<UsuarioEntity> usuario = service.obterPorId(id);

        if (!usuario.isPresent()){
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(usuario);
    }
}
