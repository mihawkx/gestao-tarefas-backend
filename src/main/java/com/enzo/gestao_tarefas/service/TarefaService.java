package com.enzo.gestao_tarefas.service;

<<<<<<< Updated upstream
import com.enzo.gestao_tarefas.controller.AtualizaTarefaRequest;
import com.enzo.gestao_tarefas.controller.NovaTarefaRequest;
import com.enzo.gestao_tarefas.model.Tarefa;
import com.enzo.gestao_tarefas.model.Usuario;
import com.enzo.gestao_tarefas.repository.TarefaRepository;
import com.enzo.gestao_tarefas.repository.UsuarioRepository;
=======
import com.enzo.gestao_tarefas.dto.AtualizaTarefaRequestDTO;
import com.enzo.gestao_tarefas.dto.NovaTarefaRequestDTO;
import com.enzo.gestao_tarefas.domain.Tarefa;
import com.enzo.gestao_tarefas.domain.Usuario;
import com.enzo.gestao_tarefas.repositories.TarefaRepository;
import com.enzo.gestao_tarefas.repositories.UsuarioRepository;
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
    public Tarefa criar(NovaTarefaRequest req, String userEmail) {
        Usuario usuario = usuarioRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

=======
    public Tarefa criar(NovaTarefaRequestDTO req) {
        Usuario usuario = usuarioRepo.findById(req.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
>>>>>>> Stashed changes
        Tarefa t = new Tarefa();
        t.setTitulo(req.titulo());
        t.setDescricao(req.descricao());
        t.setStatus(req.status() == null ? "PENDENTE" : req.status());
        t.setUsuario(usuario);

        return tarefaRepo.save(t);
    }

    @Transactional
    public Tarefa atualizar(Long id, AtualizaTarefaRequestDTO req) {
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
