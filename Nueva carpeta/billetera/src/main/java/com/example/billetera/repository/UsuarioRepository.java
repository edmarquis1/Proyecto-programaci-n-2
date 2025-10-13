package com.example.billetera.repository;

import com.example.billetera.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    // Buscar usuarios por nombre (ignorando mayúsculas/minúsculas)
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);

    // Buscar usuarios por email
    Optional<Usuario> findByEmail(String email);

    // Verificar si existe un usuario con el username
    boolean existsByUsername(String username);

    // Verificar si existe un usuario con el email
    boolean existsByEmail(String email);
}