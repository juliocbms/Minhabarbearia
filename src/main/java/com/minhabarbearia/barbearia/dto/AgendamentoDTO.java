package com.minhabarbearia.barbearia.dto;

import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.models.enums.Status;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AgendamentoDTO {


    private Long id;

    private Long clienteId;

    private Long barbeiroId;

    private OffsetDateTime startAt;

    private OffsetDateTime endAt;

    private Status status;

    private LocalDate dataCadastro;

     private LocalDate dataAgendamento;
}
