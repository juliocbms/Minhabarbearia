package com.minhabarbearia.barbearia.services.query.impl;

import com.minhabarbearia.barbearia.exception.RegraNegocioException;
import com.minhabarbearia.barbearia.exception.ScheduleInUseException;
import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
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
    public void verificarSeAgendamentoExiste(OffsetDateTime startAt, OffsetDateTime endAt) {
        if (repository.existsByStartAtAndEndAt(startAt, endAt)){
            var message = "Já existe um cliente agendado no horário solicitado";
            throw new ScheduleInUseException(message);
        }
    }


    @Override
    public void verificarDisponibilidade(Long id,  OffsetDateTime startAt,  OffsetDateTime endAt) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

        if ("BARBEIRO".equals(usuario.getRole())){
            boolean barbeiroDisponivel = repository.findByUsuarioIdAndStartAtGreaterThanEqualAndEndAtLessThanEqual(id, startAt.toLocalDate(), endAt.toLocalDate()).isEmpty();
            if (!barbeiroDisponivel) {
                throw new RegraNegocioException("O barbeiro não está disponível nesse horário.");
            }
        } else {
            throw new RegraNegocioException("Este usuário não é um barbeiro.");
        }
    }



    @Override
    public List<AgendamentoEntity> listarAgendamentosporData(Long id, LocalDate data) {
        return repository.findByUsuarioIdAndDataHora(id,data);
    }

    @Override
    public List<AgendamentoEntity> listarAgendamentosporClientePEriodoEStatus(Long id, LocalDate dataInicio, LocalDate dataFim, String status) {
        return repository.findByClienteIdAndDataAgendamentoBetweenAndStatus(id, dataInicio, dataFim, status);
    }
}
