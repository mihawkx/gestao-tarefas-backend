package com.enzo.gestao_tarefas.repository;

import com.enzo.gestao_tarefas.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByUsuario_Id(Long usuarioId);
}
