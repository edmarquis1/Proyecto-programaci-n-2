package com.example.billetera.controller;

import com.example.billetera.model.Usuario;
import com.example.billetera.service.AdminService;
import com.example.billetera.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/panel")
    public String panelAdministracion(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Usuario admin = usuarioService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si es admin
        if (!"ADMIN".equals(admin.getRol())) {
            return "redirect:/dashboard?error=No+tienes+permisos+de+administrador";
        }

        List<Usuario> usuarios = adminService.obtenerTodosUsuarios();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("admin", admin);
        return "admin/panel";
    }

    @GetMapping("/recargas")
    public String mostrarRecargas(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Usuario admin = usuarioService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!"ADMIN".equals(admin.getRol())) {
            return "redirect:/dashboard?error=No+tienes+permisos+de+administrador";
        }

        List<Usuario> usuarios = adminService.obtenerTodosUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "admin/recargas";
    }

    @PostMapping("/recargar")
    public String recargarSaldo(@RequestParam Long usuarioId,
                                @RequestParam BigDecimal monto,
                                RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Usuario admin = usuarioService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!"ADMIN".equals(admin.getRol())) {
            return "redirect:/dashboard?error=No+tienes+permisos+de+administrador";
        }

        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            redirectAttributes.addFlashAttribute("error", "El monto debe ser mayor a 0");
            return "redirect:/admin/recargas";
        }

        boolean exito = adminService.recargarSaldoUsuario(usuarioId, monto);
        if (exito) {
            redirectAttributes.addFlashAttribute("success", "Recarga realizada exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al realizar la recarga");
        }

        return "redirect:/admin/recargas";
    }

    @GetMapping("/buscar")
    public String buscarUsuarios(@RequestParam String query, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Usuario admin = usuarioService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!"ADMIN".equals(admin.getRol())) {
            return "redirect:/dashboard?error=No+tienes+permisos+de+administrador";
        }

        List<Usuario> usuarios = adminService.buscarUsuariosPorNombre(query);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("query", query);
        return "admin/panel";
    }

    // Endpoint para GCampus (opcional)
    @PostMapping("/gcampus")
    public String gcampusOperation(@RequestParam Long squadId,
                                   @RequestParam BigDecimal monto,
                                   RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Usuario admin = usuarioService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!"ADMIN".equals(admin.getRol())) {
            return "redirect:/dashboard?error=No+tienes+permisos+de+administrador";
        }

        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            redirectAttributes.addFlashAttribute("error", "El monto debe ser mayor a 0");
            return "redirect:/admin/gcampus";
        }

        boolean exito = adminService.realizarGcampus(squadId, monto);
        if (exito) {
            redirectAttributes.addFlashAttribute("success", "Operación GCampus realizada exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al realizar la operación GCampus");
        }

        return "redirect:/admin/panel";
    }
    @Transactional
    public boolean recargarSaldoUsuario(Long usuarioId, BigDecimal monto) {
        Optional<Usuario> usuarioOpt = usuarioService.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // ✅ SOLO modificar el saldo, no tocar otros campos
            usuario.recargarSaldo(monto);

            // ✅ Guardar usando el servicio que preserva el password codificado
            usuarioService.actualizarSaldo(usuario, usuario.getSaldo());
            return true;
        }
        return false;
    }
}