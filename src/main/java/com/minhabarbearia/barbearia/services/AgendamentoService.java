package com.minhabarbearia.barbearia.services;

import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface AgendamentoService {

    AgendamentoEntity salvar(AgendamentoEntity agendamento);

    AgendamentoEntity atualizar(AgendamentoEntity agendamento);

    void deletar(AgendamentoEntity agendamento);

    void validar(AgendamentoEntity agendamento);




}
