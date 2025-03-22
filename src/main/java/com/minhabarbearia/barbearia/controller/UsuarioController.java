package com.minhabarbearia.barbearia.controller;




import com.minhabarbearia.barbearia.dto.AuthenticationDTO;
import com.minhabarbearia.barbearia.dto.LoginResponseDTO;
import com.minhabarbearia.barbearia.dto.RegisterDTO;
import com.minhabarbearia.barbearia.dto.UsuarioDTO;
import com.minhabarbearia.barbearia.exception.RegraNegocioException;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.models.repository.UsuarioRepository;
import com.minhabarbearia.barbearia.services.TokenService;
import com.minhabarbearia.barbearia.services.UsuarioService;
import com.minhabarbearia.barbearia.services.impl.AuthServiceImpl;
import com.minhabarbearia.barbearia.services.query.UsuarioServiceQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("clients")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioServiceQuery serviceQuery;



    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private   TokenService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    @Operation(summary = "Autentica usuários", description = "Método para autenticar dados de usuários")
    @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso")
    @ApiResponse(responseCode = "401", description = "Falha na autenticação. Usuário não encontrado ou senha incorreta")
    @ApiResponse(responseCode = "400", description = "Requisição mal formatada")
    public ResponseEntity<?> autenticar(@RequestBody @Valid AuthenticationDTO dto) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
            System.out.println("email: " +dto.email()+ " senha: " + dto.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = jwtService.generateToken((UsuarioEntity) auth.getPrincipal());
            var tokenDTO = new LoginResponseDTO(token);
            return ResponseEntity.ok(tokenDTO);
        } catch (RegraNegocioException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao processar a requisição: " + e.getMessage());
        }
    }


    @PostMapping
    @Operation(summary = "Salva usuários", description = "Método para salvar dados de usuários")
    @ApiResponse(responseCode = "201", description = "Usuário salvo")
    @ApiResponse(responseCode = "400", description = "Usuário já cadastrado ou dados inválidos")
    public ResponseEntity salvar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        try {
            UsuarioEntity usuarioSalvo = service.salvarUsuario(usuarioDTO);
            return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
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
