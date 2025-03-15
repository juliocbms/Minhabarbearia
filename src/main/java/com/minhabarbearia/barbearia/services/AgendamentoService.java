package com.minhabarbearia.barbearia.services;

import com.minhabarbearia.barbearia.dto.AgendamentoDTO;
import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface AgendamentoService {

    AgendamentoEntity salvar(AgendamentoDTO agendamentoDTO);

    AgendamentoEntity atualizar(AgendamentoDTO agendamentoDTO);

    void deletar(AgendamentoDTO agendamentoDTO);






}
