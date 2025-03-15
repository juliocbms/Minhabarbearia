package com.minhabarbearia.barbearia.models.repository;

import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity,Long> {

    List<AgendamentoEntity> findByStartAtGreaterThanEqualAndEndAtLessThanEqualOrderByStartAtAscEndAtAsc(
            final OffsetDateTime startAt,
            final OffsetDateTime endAt
    );

    boolean existsByStartAtAndEndAt(final OffsetDateTime startAt, final OffsetDateTime endAt);

    @Query("SELECT a FROM AgendamentoEntity a WHERE a.cliente.id = :id AND a.dataAgendamento = :date")
    List<AgendamentoEntity> findByUsuarioIdAndDataHora(
            @Param("id") Long id,
            @Param("date") LocalDate date
    );


    @Query("SELECT a FROM AgendamentoEntity a WHERE a.cliente.id = :usuarioId AND a.startAt >= :startAt AND a.endAt <= :endAt")
    Optional<AgendamentoEntity> findByUsuarioIdAndStartAtGreaterThanEqualAndEndAtLessThanEqual(
            @Param("usuarioId") Long id,
            @Param("startAt") LocalDate startAt,
            @Param("endAt") LocalDate endAt
    );


    @Query("SELECT a FROM AgendamentoEntity a WHERE a.cliente.id = :clienteId AND a.dataAgendamento BETWEEN :dataInicio AND :dataFim AND a.status = :status")
    List<AgendamentoEntity> findByClienteIdAndDataAgendamentoBetweenAndStatus(
            @Param("clienteId") Long id,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("status") String status
    );
}
