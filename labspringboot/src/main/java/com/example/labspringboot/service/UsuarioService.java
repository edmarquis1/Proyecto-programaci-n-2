package com.example.labspringboot.service;

import com.example.labspringboot.model.Usuario;
import com.example.labspringboot.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }
    public Usuario save(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }
}
