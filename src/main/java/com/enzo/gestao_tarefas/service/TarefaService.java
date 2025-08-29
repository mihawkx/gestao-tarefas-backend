package com.enzo.gestao_tarefas.service;

import com.enzo.gestao_tarefas.controller.AtualizaTarefaRequest;
import com.enzo.gestao_tarefas.controller.NovaTarefaRequest;
import com.enzo.gestao_tarefas.model.Tarefa;
import com.enzo.gestao_tarefas.model.Usuario;
import com.enzo.gestao_tarefas.repository.TarefaRepository;
import com.enzo.gestao_tarefas.repository.UsuarioRepository;
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

    public Long findUsuarioIdByEmail(String email) {
        return usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"))
                .getId();
    }

    public List<Tarefa> listarPorUsuario(Long usuarioId) {
        return tarefaRepo.findByUsuario_Id(usuarioId);
    }

    @Transactional
    public Tarefa criar(NovaTarefaRequest req, String userEmail) {
        Usuario usuario = usuarioRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Tarefa t = new Tarefa();
        t.setTitulo(req.titulo());
        t.setDescricao(req.descricao());
        t.setStatus(req.status() == null ? "PENDENTE" : req.status());
        t.setUsuario(usuario);

        return tarefaRepo.save(t);
    }

    @Transactional
    public Tarefa atualizar(Long id, AtualizaTarefaRequest req) {
        Tarefa t = tarefaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        t.setTitulo(req.titulo());
        t.setDescricao(req.descricao());
        t.setStatus(req.status());
        return tarefaRepo.save(t);
    }

    @Transactional
    public void excluir(Long id) {
        if (!tarefaRepo.existsById(id)) throw new RuntimeException("Tarefa não encontrada");
        tarefaRepo.deleteById(id);
    }

    public void verificarPropriedadeTarefa(Long tarefaId, String userEmail) {
        Tarefa tarefa = tarefaRepo.findById(tarefaId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        if (!tarefa.getUsuario().getEmail().equals(userEmail)) {
            throw new RuntimeException("Acesso negado - tarefa não pertence ao usuário");
        }
    }
}
