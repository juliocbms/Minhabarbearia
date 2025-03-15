package com.minhabarbearia.barbearia.services.query;

import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;


public interface AgendamentoServiceQuery {


    void verificarSeAgendamentoExiste(OffsetDateTime startAt,  OffsetDateTime endAt);

    void verificarDisponibilidade(Long id,  OffsetDateTime startAt,  OffsetDateTime endAt);



    List<AgendamentoEntity> listarAgendamentosporData(Long usuarioId, LocalDate data);

    List<AgendamentoEntity> listarAgendamentosporClientePEriodoEStatus(Long usuarioId, LocalDate dataInicio, LocalDate dataFim, String status);
}
