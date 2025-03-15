package com.minhabarbearia.barbearia.services.impl;

import com.minhabarbearia.barbearia.dto.AgendamentoDTO;
import com.minhabarbearia.barbearia.exception.RegraNegocioException;
import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.models.enums.Role;
import com.minhabarbearia.barbearia.models.repository.AgendamentoRepository;
import com.minhabarbearia.barbearia.models.repository.UsuarioRepository;
import com.minhabarbearia.barbearia.services.AgendamentoService;
import com.minhabarbearia.barbearia.services.query.AgendamentoServiceQuery;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AgendamentoServiceImpl implements AgendamentoService {



    private final AgendamentoRepository repository;


    private final AgendamentoServiceQuery serviceQuery;


    private final UsuarioRepository usuarioRepository;

    @Override
    public AgendamentoEntity salvar(AgendamentoDTO agendamentoDTO) {
        serviceQuery.verifyIfScheduleExists(agendamentoDTO.getStartAt(), agendamentoDTO.getEndAt());

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
    public AgendamentoEntity atualizar(AgendamentoDTO agendamentoDTO) {
        return null;
    }

    @Override
    public void deletar(AgendamentoDTO agendamentoDTO) {

    }
}
