package com.minhabarbearia.barbearia.services.query.impl;

import com.minhabarbearia.barbearia.exception.EmailInUseException;
import com.minhabarbearia.barbearia.exception.PhoneInUseException;
import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import com.minhabarbearia.barbearia.models.repository.UsuarioRepository;
import com.minhabarbearia.barbearia.services.query.UsuarioServiceQuery;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioServiceQueryImpl implements UsuarioServiceQuery {

    private UsuarioRepository repository;


    @Override
    public Optional<UsuarioEntity> obterPorId(Long id) {
        return Optional.empty();
    }

    @Override
    public void validarEmail(String email) {
        if(repository.existsByEmail(email)){
            var message = "O email" + email + "já está em uso";
            throw new EmailInUseException(message);
        }
    }

    @Override
    public void verifyPhone(long id, String phone) {
        var optional = repository.findByPhone(phone);
        if (optional.isPresent() && !Objects.equals(optional.get().getPhone(), phone)){
            var message = "O telefone" + phone +"já está em uso";
            throw  new PhoneInUseException(message);
        }
    }

    @Override
    public void verifyPhone(String phone) {
        if(repository.existsByPhone(phone)){
            var message = "O telefone" + phone + "já está em uso";
            throw new PhoneInUseException(message);
        }

    }

    @Override
    public void verifyEmail( String email) {
        if(repository.existsByEmail(email)){
            var message = "O email" + email + "já está em uso";
            throw new EmailInUseException(message);
        }
    }

    @Override
    public void verifyEmail(long id,String email) {
        var optional = repository.findByEmail(email);
        if (repository.existsByEmail(email) && !repository.findById(id).get().getEmail().equals(email)) {
            var message = "O email" + email +"já está em uso";
            throw  new EmailInUseException(message);
        }
    }
}
