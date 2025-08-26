package com.enzo.gestao_tarefas.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record AtualizaTarefaRequest(
        @NotBlank String titulo,
        String descricao,
        @NotBlank String status
) {}
