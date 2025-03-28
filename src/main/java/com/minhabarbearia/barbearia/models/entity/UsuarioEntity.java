package com.minhabarbearia.barbearia.models.entity;




import com.minhabarbearia.barbearia.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


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
public class UsuarioEntity implements UserDetails {


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

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
        }

        @Override
        public String getPassword() {
                return password;
        }

        @Override
        public String getUsername() {
                return "";
        }


        @Override
        public boolean isAccountNonExpired() {
                return UserDetails.super.isAccountNonExpired();
        }

        @Override
        public boolean isAccountNonLocked() {
                return UserDetails.super.isAccountNonLocked();
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return UserDetails.super.isCredentialsNonExpired();
        }

        @Override
        public boolean isEnabled() {
                return UserDetails.super.isEnabled();
        }


}
