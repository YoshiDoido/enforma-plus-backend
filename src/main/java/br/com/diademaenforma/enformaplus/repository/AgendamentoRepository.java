package br.com.diademaenforma.enformaplus.repository;

import br.com.diademaenforma.enformaplus.model.agendamento.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}
