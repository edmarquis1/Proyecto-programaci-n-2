package com.example.labspringboot.controller;

import com.example.labspringboot.model.Producto;
import com.example.labspringboot.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
@Controller
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.findAll());
        return "productos/listar";
    }
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/formulario";
    }
    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute Producto producto,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "productos/formulario";
        }
        productoService.save(producto);
        return "redirect:/productos";
    }
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Producto> producto = productoService.findById(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "productos/formulario";
        }
        return "redirect:/productos";
    }
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.deleteById(id);
        return "redirect:/productos";
    }
}