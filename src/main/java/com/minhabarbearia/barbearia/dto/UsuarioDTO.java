package com.minhabarbearia.barbearia.dto;

import com.minhabarbearia.barbearia.models.enums.Role;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    private String name;


    private String email;


    private String password;


    private String phone;


    private Role role;
}
