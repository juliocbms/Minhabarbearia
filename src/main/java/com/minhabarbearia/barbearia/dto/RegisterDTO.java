package com.minhabarbearia.barbearia.dto;

import com.minhabarbearia.barbearia.models.enums.Role;

public record RegisterDTO(String email, String password, Role role) {
}
