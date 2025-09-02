package com.enzo.gestao_tarefas.controllers;

import com.enzo.gestao_tarefas.domain.Tarefa;
import com.enzo.gestao_tarefas.dto.AtualizaTarefaRequestDTO;
import com.enzo.gestao_tarefas.dto.NovaTarefaRequestDTO;
import com.enzo.gestao_tarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
@CrossOrigin(origins= "*")
public class TarefaController {

    private final TarefaService service;
    public TarefaController(TarefaService service){ this.service = service; }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listar(@RequestParam Long usuarioId){
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Tarefa> criar(@Valid @RequestBody NovaTarefaRequestDTO req){
        return ResponseEntity.ok(service.criar(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id,
                                            @Valid @RequestBody AtualizaTarefaRequestDTO req){
        return ResponseEntity.ok(service.atualizar(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
