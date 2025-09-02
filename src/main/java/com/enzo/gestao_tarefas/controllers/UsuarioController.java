<<<<<<<< Updated upstream:src/main/java/com/enzo/gestao_tarefas/UsuarioController.java
package com.enzo.gestao_tarefas.controller;
========
package com.enzo.gestao_tarefas.controllers;
>>>>>>>> Stashed changes:src/main/java/com/enzo/gestao_tarefas/controllers/UsuarioController.java

import com.enzo.gestao_tarefas.domain.Usuario;
import com.enzo.gestao_tarefas.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioRepository repo;
    public UsuarioController(UsuarioRepository repo) { this.repo = repo; }

    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario u){
        return ResponseEntity.ok(repo.save(u));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obter(@PathVariable Long id){
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
