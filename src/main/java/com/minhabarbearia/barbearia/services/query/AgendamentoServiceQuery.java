package com.minhabarbearia.barbearia.services.query;

import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;
import com.minhabarbearia.barbearia.models.enums.Status;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;


public interface AgendamentoServiceQuery {


    void verificarSeAgendamentoExiste( OffsetDateTime startAt,  OffsetDateTime endAt);

    void verificarSeBArbeiroEstaDInponivel(Long id, OffsetDateTime startAt,  OffsetDateTime endAt);

    List<AgendamentoEntity> listarAgendamentos(Long id, LocalDate dataInicio, LocalDate dataFim, Status status);

}
