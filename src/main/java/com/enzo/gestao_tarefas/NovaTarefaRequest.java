package com.enzo.gestao_tarefas.controller;

import jakarta.validation.constraints.NotBlank;

public record NovaTarefaRequest(
    @NotBlank String titulo,
    String descricao,
    String status
){}
