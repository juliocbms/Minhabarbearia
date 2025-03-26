package com.minhabarbearia.barbearia.controller;

import com.minhabarbearia.barbearia.dto.AgendamentoDTO;
import com.minhabarbearia.barbearia.dto.AtualizaStatusDTO;
import com.minhabarbearia.barbearia.dto.UsuarioDTO;
import com.minhabarbearia.barbearia.exception.RegraNegocioException;
import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.models.enums.Status;
import com.minhabarbearia.barbearia.models.mapper.AgendamentoRequest;
import com.minhabarbearia.barbearia.models.repository.UsuarioRepository;
import com.minhabarbearia.barbearia.services.AgendamentoService;
import com.minhabarbearia.barbearia.services.query.AgendamentoServiceQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("agendamentos")
@AllArgsConstructor
public class AgendamentoController {

    private final AgendamentoService service;
    private final AgendamentoServiceQuery serviceQuery;
    private final UsuarioRepository repository;


    @PostMapping("/save")
    @Operation(summary = "salva agendamentos", description = "Método para salvar agendamentos")
    @ApiResponse(responseCode = "201", description = "Agendamento salvo")
    @ApiResponse(responseCode = "400", description = "Agendamento já cadastrado")
    public ResponseEntity<AgendamentoEntity> salvar(@RequestBody AgendamentoDTO agendamentoDTO) {
        try {
            AgendamentoEntity agendamento = service.salvar(agendamentoDTO);
            System.out.println("Agendamento: " + agendamentoDTO
            );
            System.out.println("Agendamento: " + agendamento
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(agendamento);

        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

    @PutMapping("{id}")
    @Operation(summary = "atualiza agendamentos", description = "metodo para atualizar agendamentos")
    @ApiResponse(responseCode = "201" , description = "agendamento atualizado")
    @ApiResponse(responseCode = "400", description = "erro")
    public ResponseEntity atualizar(@PathVariable("id")  long id, @RequestBody AgendamentoDTO agendamentoDTO){
        try {
            AgendamentoEntity agendamentoAtualizado = service.atualizar(id, agendamentoDTO);
            return ResponseEntity.ok(agendamentoAtualizado);
        } catch (RegraNegocioException e ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Agendamento não encontrado na base de Dados.");
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "deleta agendamentos", description = "metodo para deletar agendamentos")
    @ApiResponse(responseCode = "201" , description = "agendamento deletado")
    @ApiResponse(responseCode = "400", description = "agendamento nao encontrado")
    public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
        return service.obterPorId(id).map( entidade -> {
            service.deletar(entidade.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }).orElseGet(() ->
                new ResponseEntity<>("Agendamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/{id}/atualizar-status")
    @Operation(summary = "atualiza status", description = "Método para atualizar o status de agendamentos")
    @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar status")
    public ResponseEntity<?> atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusDTO dto) {
        return service.obterPorId(id).map(entity -> {
            try {
                Status statusSelecionado = Status.valueOf(dto.getStatus());
                if (statusSelecionado == null) {
                    return ResponseEntity.badRequest().body("Não foi possível atualizar status do lançamento, envie um status válido");
                }
                entity.setStatus(statusSelecionado);

                service.atualizarStatus(entity, statusSelecionado);
                return ResponseEntity.ok(entity);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Não foi possível atualizar o status, envie um status válido");
            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() ->
                new ResponseEntity<>("Agendamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
    }


    @GetMapping("/clients/inicio/{id}")
    @Operation(summary = "Busca agendamento por período", description = "Método para buscar agendamentos por período")
    @ApiResponse(responseCode = "200", description = "Agendamentos encontrados")
    @ApiResponse(responseCode = "204", description = "Nenhum agendamento encontrado")
    public ResponseEntity<List<AgendamentoEntity>> obterAgendamentos(
            @PathVariable("id") long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) Status status) {


        List<AgendamentoEntity> agendamentos = serviceQuery.listarAgendamentos(id, dataInicio, dataFim, status);

        if (agendamentos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/clients/history/{id}")
    @Operation(summary = "Busca agendamento por período", description = "Método para buscar agendamentos por período")
    @ApiResponse(responseCode = "200", description = "Agendamentos encontrados")
    @ApiResponse(responseCode = "204", description = "Nenhum agendamento encontrado")
    public ResponseEntity<List<AgendamentoEntity>> obterAgendamentosHistory(
            @PathVariable("id") long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) Status status) {


        List<AgendamentoEntity> agendamentos = serviceQuery.listarAgendamentos(id, dataInicio, dataFim, status);

        if (agendamentos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/by-date/{id}")
    @Operation(summary = "Busca agendamentos por data", description = "Método para buscar agendamentos em uma data específica")
    public ResponseEntity<List<AgendamentoEntity>> obterAgendamentosPorData(
            @PathVariable("id") long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<AgendamentoEntity> agendamentos = serviceQuery.listarAgendamentos(
                id,
                data,
                data,
                null // status null para trazer todos
        );

        if (agendamentos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(agendamentos);
    }



}
