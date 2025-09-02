package com.enzo.gestao_tarefas.service;

import com.enzo.gestao_tarefas.domain.Tarefa;
import com.enzo.gestao_tarefas.domain.Usuario;
import com.enzo.gestao_tarefas.dto.AtualizaTarefaRequestDTO;
import com.enzo.gestao_tarefas.dto.NovaTarefaRequestDTO;
import com.enzo.gestao_tarefas.repositories.TarefaRepository;
import com.enzo.gestao_tarefas.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepo;
    private final UsuarioRepository usuarioRepo;

    public TarefaService(TarefaRepository tarefaRepo, UsuarioRepository usuarioRepo) {
        this.tarefaRepo = tarefaRepo;
        this.usuarioRepo = usuarioRepo;
    }

    public List<Tarefa> listarPorUsuario(Long usuarioId) {
        return tarefaRepo.findByUsuario_Id(usuarioId);
    }

    @Transactional
    public Tarefa criar(@Valid NovaTarefaRequestDTO req) {
        Usuario usuario = usuarioRepo.findById(req.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        Tarefa t = new Tarefa();
        t.setTitulo(req.titulo());
        t.setDescricao(req.descricao());
        t.setStatus(req.status() == null ? "PENDENTE" : req.status());
        t.setUsuario(usuario);
        return tarefaRepo.save(t);
    }

    @Transactional
    public Tarefa atualizar(Long id, @Valid AtualizaTarefaRequestDTO req) {
        Tarefa t = tarefaRepo.findById(String.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));
        t.setTitulo(req.titulo());
        t.setDescricao(req.descricao());
        t.setStatus(req.status());
        return tarefaRepo.save(t);
    }

    @Transactional
    public void excluir(Long id) {
        if (!tarefaRepo.existsById(String.valueOf(id))) throw new IllegalArgumentException("Tarefa não encontrada");
        tarefaRepo.deleteById(String.valueOf(id));
    }
}
