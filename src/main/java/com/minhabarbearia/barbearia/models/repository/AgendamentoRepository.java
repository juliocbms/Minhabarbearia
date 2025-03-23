package com.minhabarbearia.barbearia.models.repository;

import com.minhabarbearia.barbearia.models.entity.AgendamentoEntity;
import com.minhabarbearia.barbearia.models.enums.Status;
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



    boolean existsByStartAtAndEndAt(final OffsetDateTime startAt, final OffsetDateTime endAt);

    @Query("SELECT a FROM AgendamentoEntity a WHERE (a.cliente.id = :id OR a.barbeiro.id = :id) AND a.dataAgendamento = :date")
    List<AgendamentoEntity> findByUsuarioIdOrBarbeiroIdAndDataAgendamento(
            @Param("id") Long id,
            @Param("date") LocalDate date
    );


    @Query("SELECT a FROM AgendamentoEntity a WHERE a.barbeiro.id = :barbeiroId AND a.startAt >= :startAt AND a.endAt <= :endAt")
    Optional<AgendamentoEntity> findByUsuarioIdAndStartAtGreaterThanEqualAndEndAtLessThanEqual(
            @Param("barbeiroId") Long id,
            @Param("startAt") OffsetDateTime startAt,
            @Param("endAt") OffsetDateTime endAt
    );


    @Query("SELECT a FROM AgendamentoEntity a WHERE (a.cliente.id = :id OR a.barbeiro.id = :id) AND a.dataAgendamento BETWEEN :dataInicio AND :dataFim AND a.status = :status")
    List<AgendamentoEntity> findByUsuarioIdOrBarbeiroIdAndDataAgendamentoBetweenAndStatus(
            @Param("clienteId") Long id,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("status") Status status
    );

    @Query("SELECT a FROM AgendamentoEntity a WHERE (a.cliente.id = :id OR a.barbeiro.id = :id) " +
            "AND (:dataInicio IS NULL OR a.dataAgendamento >= :dataInicio) " +
            "AND (:dataFim IS NULL OR a.dataAgendamento <= :dataFim) " +
            "AND (:status IS NULL OR a.status = :status)")
    List<AgendamentoEntity> findByClienteIdOrBarbeiroIdAndDataAgendamentoBetweenAndStatus(
            @Param("id") Long id,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("status") Status status);


    @Query("SELECT a FROM AgendamentoEntity a WHERE (a.cliente.id = :id OR a.barbeiro.id = :id) " +
            "AND a.dataAgendamento >= :dataInicio " +
            "AND (:status IS NULL OR a.status = :status)")
    List<AgendamentoEntity> findByClienteIdOrBarbeiroIdAndDataAgendamentoAfterAndStatus(
            @Param("id") Long id,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("status") Status status);

    @Query("SELECT a FROM AgendamentoEntity a WHERE (a.cliente.id = :id OR a.barbeiro.id = :id) " +
            "AND a.dataAgendamento <= :dataFim " +
            "AND (:status IS NULL OR a.status = :status)")
    List<AgendamentoEntity> findByClienteIdOrBarbeiroIdAndDataAgendamentoBeforeAndStatus(
            @Param("id") Long id,
            @Param("dataFim") LocalDate dataFim,
            @Param("status") Status status);
}
