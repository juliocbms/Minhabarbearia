package com.minhabarbearia.barbearia.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

public class AgendamentoDTO {


    private Long id;


    private UsuarioEntity cliente;


    private UsuarioEntity barbeiro;


    private OffsetDateTime startAt;


    private OffsetDateTime endAt;

    // private LocalDate dataAgendamento;
}
