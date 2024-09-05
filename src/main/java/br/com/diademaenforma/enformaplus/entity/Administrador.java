package br.com.diademaenforma.enformaplus.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Administrador extends Usuario {
    // Entidade de administrador que herda de usuário.
    // No momento, não possui atributos adicionais.
}
