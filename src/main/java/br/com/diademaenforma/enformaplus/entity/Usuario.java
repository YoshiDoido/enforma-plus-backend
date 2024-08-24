package br.com.diademaenforma.enformaplus.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "usuario")
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario", nullable = false, length = 100)
    private String usuario;

    @Column(name = "nome", nullable = false,length = 100)
    private String nome;

    @Column(name = "email", nullable = false,  length = 100)
    private String email;

    @Column(name = "senha", nullable = false,  length = 100)
    private String senha;

    @Column(name = "papel", nullable = false,  length = 30)
    private Papel papel;
}
