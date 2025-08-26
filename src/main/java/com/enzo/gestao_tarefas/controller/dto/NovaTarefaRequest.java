package com.enzo.gestao_tarefas.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NovaTarefaRequest(
    @NotBlank String titulo,
    String descricao,
    String status,
    @NotNull Long usuarioId
){}
