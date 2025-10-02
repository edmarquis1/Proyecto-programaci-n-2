package com.example.labspringboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
@Entity
@Table(name = "usuarios")
public class Usuario {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@NotBlank(message = "El username es obligatorio")
@Column(unique = true, nullable = false)
private String username;
@NotBlank(message = "La contraseña es obligatoria")
@Column(nullable = false)
private String password;
@NotBlank(message = "El rol es obligatorio")
private String rol;
// Constructores
public Usuario() {}
public Usuario(String username, String password, String rol) {
this.username = username;
this.password = password;
this.rol = rol;
}
// Getters y Setters
public Long getId() {
return id;
}
public void setId(Long id) {
this.id = id;
}
public String getUsername() {
return username;
}
public void setUsername(String username) {
this.username = username;
}
public String getPassword() {
return password;
}
public void setPassword(String password) {
this.password = password;
}
public String getRol() {
return rol;
}
public void setRol(String rol) {
this.rol = rol;
}
}