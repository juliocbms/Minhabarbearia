package com.minhabarbearia.barbearia.services.impl;

import com.minhabarbearia.barbearia.dto.AgendamentoDTO;
import com.minhabarbearia.barbearia.exception.RegraNegocioException;
import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.models.enums.Role;
import com.minhabarbearia.barbearia.models.enums.Status;
import com.minhabarbearia.barbearia.models.repository.AgendamentoRepository;
import com.minhabarbearia.barbearia.models.repository.UsuarioRepository;
import com.minhabarbearia.barbearia.services.AgendamentoService;
import com.minhabarbearia.barbearia.services.query.AgendamentoServiceQuery;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AgendamentoServiceImpl implements AgendamentoService {



    private final AgendamentoRepository repository;


    private final AgendamentoServiceQuery serviceQuery;


    private final UsuarioRepository usuarioRepository;

    @Override
    public AgendamentoEntity salvar(AgendamentoDTO agendamentoDTO) {
        serviceQuery.verificarDisponibilidade(agendamentoDTO.getId(), agendamentoDTO.getStartAt(),agendamentoDTO.getEndAt());
        serviceQuery.verificarSeAgendamentoExiste(agendamentoDTO.getStartAt(), agendamentoDTO.getEndAt());

        UsuarioEntity cliente = usuarioRepository.findById(agendamentoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        if (!cliente.getRole().equals(Role.CLIENTE)) {
            throw new RegraNegocioException("O usuário informado não possui a role de CLIENTE.");
        }

        UsuarioEntity barbeiro = usuarioRepository.findById(agendamentoDTO.getBarbeiroId())
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));

        AgendamentoEntity agendamento = AgendamentoEntity.builder()
                .cliente(cliente)
                .barbeiro(barbeiro)
                .startAt(agendamentoDTO.getStartAt())
                .endAt(agendamentoDTO.getEndAt())
                .status(agendamentoDTO.getStatus())
                .dataAgendamento(agendamentoDTO.getDataAgendamento())
                .build();

        return repository.save(agendamento);
    }

    @Override
    public AgendamentoEntity atualizar(Long id, AgendamentoDTO agendamentoDTO) {
        serviceQuery.verificarSeAgendamentoExiste(agendamentoDTO.getStartAt(),agendamentoDTO.getEndAt());


        AgendamentoEntity stored = repository.findById(agendamentoDTO.getId())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        UsuarioEntity barbeiro = usuarioRepository.findById(agendamentoDTO.getBarbeiroId())
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));


        stored.setBarbeiro(barbeiro);
        stored.setStartAt(agendamentoDTO.getStartAt());
        stored.setEndAt(agendamentoDTO.getEndAt());
        stored.setStatus(agendamentoDTO.getStatus());

        return repository.save(stored);
    }

    @Override
    public void deletar(long id) {
        repository.findById(id);
        repository.deleteById(id);
    }

    @Override
    public Optional<AgendamentoEntity> obterPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public AgendamentoEntity atualizarStatus(AgendamentoEntity agendamentoEntity, Status status) {
        AgendamentoEntity entity = repository.findById(agendamentoEntity.getId())
                .orElseThrow(() -> new RegraNegocioException("Agendamento não encontrado"));

        entity.setStatus(status);
        return repository.save(entity);
    }



}
