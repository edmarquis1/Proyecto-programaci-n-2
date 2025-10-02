package com.example.labspringboot.config;

import com.example.labspringboot.model.Usuario;
import com.example.labspringboot.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private UsuarioService usuarioService;
    @Override
    public void run(String... args) throws Exception {
// Crear usuario admin por defecto si no existe
        if (!usuarioService.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setRol("ADMIN");
            usuarioService.save(admin);
            System.out.println("Usuario admin creado: admin/admin123");
        }
    }
}
