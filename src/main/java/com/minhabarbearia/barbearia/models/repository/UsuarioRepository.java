package com.minhabarbearia.barbearia.models.repository;

import com.minhabarbearia.barbearia.models.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity,Long> {

    boolean existsByEmail(String email);

    Optional<UsuarioEntity> findByEmail(String email);

    boolean existsByPhone(final String phone);

    Optional<UsuarioEntity> findByPhone(final String phone);
}
