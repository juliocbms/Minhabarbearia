package com.minhabarbearia.barbearia.controller;

import com.minhabarbearia.barbearia.dto.AgendamentoDTO;
import com.minhabarbearia.barbearia.dto.AtualizaStatusDTO;
import com.minhabarbearia.barbearia.dto.UsuarioDTO;
import com.minhabarbearia.barbearia.exception.RegraNegocioException;
import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.models.enums.Status;
import com.minhabarbearia.barbearia.models.repository.UsuarioRepository;
import com.minhabarbearia.barbearia.services.AgendamentoService;
import com.minhabarbearia.barbearia.services.query.AgendamentoServiceQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
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

    @GetMapping("{id}")
    @Operation(summary = "busca agendamento por id", description = "metodo para buscar agendamentos por id")
    @ApiResponse(responseCode = "201" , description = "agendamento encontrado")
    @ApiResponse(responseCode = "400", description = "agendamento nao encontrado")
    public ResponseEntity obterAgendamento(@PathVariable("id") Long id){
        Optional<AgendamentoEntity> agendamento = service.obterPorId(id);

        if (!agendamento.isPresent()){
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(agendamento);
    }

    @GetMapping("/data")
    @Operation(summary = "busca agendamento por data", description = "metodo para buscar agendamentos por data")
    @ApiResponse(responseCode = "201" , description = "agendamento encontrado")
    @ApiResponse(responseCode = "400", description = "agendamento nao encontrado")
    public ResponseEntity<List<AgendamentoEntity>> obterAgendamentoPorData( Long id, LocalDate data){
        List<AgendamentoEntity> agendamento = serviceQuery.listarAgendamentosporData(id,data);

        if (agendamento.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(agendamento);
    }

    @GetMapping("/data/periodo")
    @Operation(summary = "busca agendamento por periodo", description = "metodo para buscar agendamentos por periodo")
    @ApiResponse(responseCode = "201" , description = "agendamento encontrado")
    @ApiResponse(responseCode = "400", description = "agendamento nao encontrado")
    public ResponseEntity obterAgendamentoPorPeriodo( Long id, LocalDate dataInicio, LocalDate dataFim, String status){
        List<AgendamentoEntity> agendamento = serviceQuery.listarAgendamentosporClientePEriodoEStatus(id,dataInicio, dataFim,status);

        if (!agendamento.isEmpty()){
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(agendamento);
    }




}
