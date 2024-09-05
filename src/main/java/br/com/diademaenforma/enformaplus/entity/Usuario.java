package br.com.diademaenforma.enformaplus.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED) // Esse bean permite que a entidade seja persistida em uma tabela única ou em tabelas separadas.
@Entity
public class Usuario {

    // Entidade base para depois poder criar as entidades de usuário comum, usuário profissional e administrador.
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

    // Por padrão, o telefone pode ser nulo, cabe as classes filhas decidirem se o telefone é obrigatório ou não.
    @Column(name = "telefone", nullable = true, length = 20)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel", nullable = false,  length = 30)
    private Papel papel;
}
