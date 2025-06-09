package br.com.diademaenforma.enformaplus.model.acesso;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "acesso_profissionais")
public class AcessoProfissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "profissional_id", nullable = false)
    private Long profissionalId;

    @Column(name =  "data_hora_acesso")
    private LocalDateTime dataHoraAcesso = LocalDateTime.now();
}
