package com.minhabarbearia.barbearia.services.impl;

import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.models.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public  SecurityUserDetailsService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioEntity usuarioEncontrado = usuarioRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Email não cadastrado"));


        return  User.builder()
                .username(usuarioEncontrado.getEmail())
                .password(usuarioEncontrado.getPassword())
                .roles("USER")
                .build();
    }
}
