package com.minhabarbearia.barbearia.dto;

import com.minhabarbearia.barbearia.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private String name;


    private String email;


    private String password;


    private String phone;


    private Role role;
}
