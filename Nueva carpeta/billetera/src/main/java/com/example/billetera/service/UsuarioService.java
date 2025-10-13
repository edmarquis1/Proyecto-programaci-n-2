package com.example.billetera.service;

import com.example.billetera.model.Usuario;
import com.example.billetera.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario save(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public void actualizarSaldo(Usuario usuario, BigDecimal nuevoSaldo) {
        usuario.setSaldo(nuevoSaldo);
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }
    @Transactional
    public void actualizarSaldo(Usuario usuario, BigDecimal nuevoSaldo) {
        // ✅ Obtener el usuario fresco de la base de datos
        Usuario usuarioPersistido = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // ✅ Solo actualizar el saldo
        usuarioPersistido.setSaldo(nuevoSaldo);
        usuarioRepository.save(usuarioPersistido);
    }

    public Usuario save(Usuario usuario) {
        // ✅ Solo codificar password si es nuevo o está siendo cambiado
        if (usuario.getId() == null || usuario.getPassword().length() < 20) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }
}

}