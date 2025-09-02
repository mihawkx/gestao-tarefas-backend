package com.enzo.gestao_tarefas.dto;

public record TarefaDTO(Long id, String titulo, String descricao, String status, Long usuarioId) {}

