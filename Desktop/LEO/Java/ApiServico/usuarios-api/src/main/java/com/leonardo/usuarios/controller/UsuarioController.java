package com.leonardo.usuarios.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.leonardo.usuarios.model.Usuario;
import com.leonardo.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")  // Permite acesso de qualquer lugar (HTML local)
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    // Endpoint de teste simples para verificar se a API está no ar
    @GetMapping("/teste")
    public String teste() {
        return "API de Usuários está funcionando! " + java.time.LocalDateTime.now();
    }

    // POST: Criar novo usuário
    @PostMapping
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.criarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(criarRespostaErro(e.getMessage()));
        }
    }

    // GET: Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // GET: Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarUsuarioPorId(id);
        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(criarRespostaErro("Usuário não encontrado com ID: " + id));
        }
    }

    // PUT: Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(criarRespostaErro(e.getMessage()));
        }
    }

    // DELETE: Remover usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deletarUsuario(id);
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", "Usuário deletado com sucesso");
            return ResponseEntity.ok(resposta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(criarRespostaErro(e.getMessage()));
        }
    }

    // Método auxiliar para criar respostas de erro
    private Map<String, String> criarRespostaErro(String mensagem) {
        Map<String, String> resposta = new HashMap<>();
        resposta.put("erro", mensagem);
        return resposta;
    }
}