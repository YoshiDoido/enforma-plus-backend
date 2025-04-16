package br.com.diademaenforma.enformaplus.model.user;

import br.com.diademaenforma.enformaplus.model.local.Local;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "usuario")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String usuario;
    private String email;
    private String senha;
    private String telefone;

    @Enumerated(EnumType.STRING)
    private Papel papel;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @ManyToOne
    @JoinColumn(name = "local_id")
    @JsonIgnoreProperties("usuariosProfissionais")
    private Local local;

    @JsonFormat(pattern = "dd-MM-yy'T'HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "dd-MM-yy'T'HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

}
