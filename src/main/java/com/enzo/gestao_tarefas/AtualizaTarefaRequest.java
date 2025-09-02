<<<<<<<< Updated upstream:src/main/java/com/enzo/gestao_tarefas/AtualizaTarefaRequest.java
package com.enzo.gestao_tarefas.controller;
========
package com.enzo.gestao_tarefas.dto;
>>>>>>>> Stashed changes:src/main/java/com/enzo/gestao_tarefas/dto/AtualizaTarefaRequestDTO.java

import jakarta.validation.constraints.NotBlank;

public record AtualizaTarefaRequestDTO(
        @NotBlank String titulo,
        String descricao,
        @NotBlank String status
) {}
