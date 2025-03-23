package com.minhabarbearia.barbearia.services.query.impl;

import com.minhabarbearia.barbearia.exception.RegraNegocioException;
import com.minhabarbearia.barbearia.exception.ScheduleInUseException;
import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.models.enums.Status;
import com.minhabarbearia.barbearia.models.repository.AgendamentoRepository;
import com.minhabarbearia.barbearia.models.repository.UsuarioRepository;
import com.minhabarbearia.barbearia.services.query.AgendamentoServiceQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AgendamentoServiceQueryImpl implements AgendamentoServiceQuery {

    private final AgendamentoRepository repository;
    private final UsuarioRepository usuarioRepository;






    @Override
    public void verificarSeAgendamentoExiste( OffsetDateTime startAt, OffsetDateTime endAt) {
        if (repository.existsByStartAtAndEndAt( startAt, endAt)){
            var message = "Já existe um cliente agendado no horário solicitado";
            throw new ScheduleInUseException(message);
        }
    }

    @Override
    public void verificarSeBArbeiroEstaDInponivel(Long id, OffsetDateTime startAt, OffsetDateTime endAt) {
        if (repository.findByUsuarioIdAndStartAtGreaterThanEqualAndEndAtLessThanEqual(id, startAt, endAt).isPresent()) {
            var message = "Já existe um cliente agendado no horário solicitado";
            throw new ScheduleInUseException(message);
        }
    }

    @Override
    public List<AgendamentoEntity> listarAgendamentos(Long id, LocalDate dataInicio, LocalDate dataFim, Status status) {
        if (dataInicio != null && dataFim != null) {
            return repository.findByClienteIdOrBarbeiroIdAndDataAgendamentoBetweenAndStatus(id, dataInicio, dataFim, status);
        } else if (dataInicio != null) {
            return repository.findByClienteIdOrBarbeiroIdAndDataAgendamentoAfterAndStatus(id, dataInicio, status);
        } else if (dataFim != null) {
            return repository.findByClienteIdOrBarbeiroIdAndDataAgendamentoBeforeAndStatus(id, dataFim, status);
        } else {
            return repository.findByClienteIdOrBarbeiroIdAndDataAgendamentoBetweenAndStatus(id, null, null, status);
        }
    }




}
