package com.minhabarbearia.barbearia.services.query;

import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;


public interface AgendamentoServiceQuery {

    Optional<AgendamentoEntity> obterPorId(Long id);

    List<AgendamentoEntity> buscar(AgendamentoEntity agendamentoFiltro);

    void verifyIfScheduleExists(OffsetDateTime startAt,  OffsetDateTime endAt);
}
