package com.minhabarbearia.barbearia.models.entity;




import com.minhabarbearia.barbearia.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(
        name = "USUARIOS",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_EMAIL", columnNames = "email"),
                @UniqueConstraint(name = "UK_PHONE", columnNames = "phone")
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
public class UsuarioEntity {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 150)
        private String name;

        @Column(nullable = false, length = 150)
        private String email;

        @Column(nullable = false, length = 500)
        private String password;

        @Column(nullable = false, length = 11, columnDefinition = "bpchar(11)")
        private String phone;

        @Column(name = "role", nullable = false)
        @Enumerated(value = EnumType.STRING)
        private Role role;

}
