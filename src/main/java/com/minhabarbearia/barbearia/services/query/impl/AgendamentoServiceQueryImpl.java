package com.minhabarbearia.barbearia.services.query.impl;

import com.minhabarbearia.barbearia.exception.ScheduleInUseException;
import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;
import com.minhabarbearia.barbearia.models.repository.AgendamentoRepository;
import com.minhabarbearia.barbearia.models.repository.UsuarioRepository;
import com.minhabarbearia.barbearia.services.query.AgendamentoServiceQuery;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AgendamentoServiceQueryImpl implements AgendamentoServiceQuery {

    private final AgendamentoRepository repository;




    @Override
    public Optional<AgendamentoEntity> obterPorId(Long id) {
        return Optional.empty();
    }

    @Override
    public void verifyIfScheduleExists(OffsetDateTime startAt, OffsetDateTime endAt) {
        if (repository.existsByStartAtAndEndAt(startAt, endAt)){
            var message = "Já existe um cliente agendado no horário solicitado";
            throw new ScheduleInUseException(message);
        }
    }

    @Override
    public List<AgendamentoEntity> buscar(AgendamentoEntity agendamentoFiltro) {
        return List.of();
    }
}
