package com.minhabarbearia.barbearia.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "AGENDAMENTOS",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_AGENDAMENTOS_INTERVALO", columnNames ={"start_at", "end_at"} )
        }
)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendamentoEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private UsuarioEntity cliente;

    @ManyToOne
    @JoinColumn(name = "id_barbeiro", nullable = false)
    private UsuarioEntity barbeiro;

    @Column(nullable = false, name = "start_at")
    private OffsetDateTime startAt;

    @Column(nullable = false, name = "end_at")
    private OffsetDateTime endAt;

   // @Column(name = "data_agendamento", nullable = false)
   // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
   // private LocalDate dataAgendamento;



}
