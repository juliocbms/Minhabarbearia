package com.minhabarbearia.barbearia.controller;

import com.minhabarbearia.barbearia.dto.AgendamentoDTO;
import com.minhabarbearia.barbearia.dto.UsuarioDTO;
import com.minhabarbearia.barbearia.exception.RegraNegocioException;
import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.services.AgendamentoService;
import com.minhabarbearia.barbearia.services.query.AgendamentoServiceQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("agendamentos")
@AllArgsConstructor
public class AgendamentoController {

    private final AgendamentoService service;


    @PostMapping
    @Operation(summary = "salva agendamentos", description = "Método para salvar agendamentos")
    @ApiResponse(responseCode = "201", description = "Agendamento salvo")
    @ApiResponse(responseCode = "400", description = "Agendamento já cadastrado")
    public ResponseEntity<AgendamentoEntity> salvar(@RequestBody AgendamentoDTO agendamentoDTO) {
        try {
            AgendamentoEntity agendamento = service.salvar(agendamentoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(agendamento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
