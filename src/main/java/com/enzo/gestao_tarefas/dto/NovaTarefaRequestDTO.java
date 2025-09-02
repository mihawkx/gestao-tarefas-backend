package com.enzo.gestao_tarefas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NovaTarefaRequestDTO(
    @NotBlank String titulo,
    String descricao,
    String status,
    @NotNull String usuarioId
){}
