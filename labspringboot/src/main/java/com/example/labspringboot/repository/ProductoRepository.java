package com.example.labspringboot.repository;

import com.example.labspringboot.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}