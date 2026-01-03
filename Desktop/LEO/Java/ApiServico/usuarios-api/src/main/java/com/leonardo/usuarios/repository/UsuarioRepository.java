package com.leonardo.usuarios.repository;

import com.leonardo.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // O Spring cria o SQL "SELECT count(*) > 0 FROM usuarios WHERE email = ?" automaticamente
    boolean existsByEmail(String email);

}