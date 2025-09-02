package com.enzo.gestao_tarefas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tarefas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    private String titulo;
    private String descricao;
    private String status = "PENDENTE";

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
