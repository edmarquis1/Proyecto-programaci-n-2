package com.example.billetera.service;

import com.example.billetera.model.Usuario;
import com.example.billetera.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para obtener todos los usuarios
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Método para buscar usuarios por nombre
    public List<Usuario> buscarUsuariosPorNombre(String query) {
        return usuarioRepository.findByNombreContainingIgnoreCase(query);
    }

    // Método para recargar saldo a usuario
    @Transactional
    public boolean recargarSaldoUsuario(Long usuarioId, BigDecimal monto) {
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                BigDecimal nuevoSaldo = usuario.getSaldo().add(monto);
                usuario.setSaldo(nuevoSaldo);
                usuarioRepository.save(usuario);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // Método para GCampus (si lo necesitas)
    @Transactional
    public boolean realizarGcampus(Long squadId, BigDecimal monto) {
        // Implementar lógica específica para GCampus
        try {
            // Aquí tu lógica para GCampus
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}