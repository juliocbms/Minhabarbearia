package com.minhabarbearia.barbearia.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class AgendamentoFiltroDTO {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String status;
}