package com.minhabarbearia.barbearia.models.mapper;

import com.minhabarbearia.barbearia.models.enums.Status;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Data
@Getter
@Setter
public class AgendamentoRequest {
    private Long id;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Status status;
}
