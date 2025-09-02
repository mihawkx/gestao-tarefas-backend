package com.enzo.gestao_tarefas.dto;

import jakarta.validation.constraints.NotBlank;

public record AtualizaTarefaRequestDTO(
        @NotBlank String titulo,
        String descricao,
        @NotBlank String status
) {}
