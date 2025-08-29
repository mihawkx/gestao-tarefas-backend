package com.enzo.gestao_tarefas.controller;

import com.enzo.gestao_tarefas.model.Tarefa;
import com.enzo.gestao_tarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
@CrossOrigin(origins= "*")
public class TarefaController {

    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTarefas(@AuthenticationPrincipal Jwt jwt) {
        String userEmail = jwt.getSubject();
        Long usuarioId = service.findUsuarioIdByEmail(userEmail);
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Tarefa> criar(@Valid @RequestBody NovaTarefaRequest req,
                                        @AuthenticationPrincipal Jwt jwt) {
        String userEmail = jwt.getSubject();
        return ResponseEntity.ok(service.criar(req, userEmail));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id,
                                            @Valid @RequestBody AtualizaTarefaRequest req,
                                            @AuthenticationPrincipal Jwt jwt) {
        String userEmail = jwt.getSubject();
        service.verificarPropriedadeTarefa(id, userEmail);
        return ResponseEntity.ok(service.atualizar(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id,
                                        @AuthenticationPrincipal Jwt jwt) {
        String userEmail = jwt.getSubject();
        service.verificarPropriedadeTarefa(id, userEmail);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
