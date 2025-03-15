package com.minhabarbearia.barbearia.services;

import com.minhabarbearia.barbearia.dto.AgendamentoDTO;
import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.models.enums.Status;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface AgendamentoService {

    AgendamentoEntity salvar(AgendamentoDTO agendamentoDTO);

    AgendamentoEntity atualizar(Long id,AgendamentoDTO dto);

    void deletar(long id);

    Optional<AgendamentoEntity> obterPorId(Long id);

    AgendamentoEntity atualizarStatus(AgendamentoEntity agendamentoEntity, Status status);






}
