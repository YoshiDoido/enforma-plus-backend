package br.com.diademaenforma.enformaplus.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class UsuarioComum extends Usuario {

    // Entidade de usuário comum que herda de usuário.
    // Sobrescreve o telefone para ser obrigatório.
    @Column(name = "telefone", nullable = false, length = 20)
    private String telefone;
}
