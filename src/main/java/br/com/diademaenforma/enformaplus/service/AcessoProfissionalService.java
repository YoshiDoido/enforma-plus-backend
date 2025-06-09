package br.com.diademaenforma.enformaplus.service;

import br.com.diademaenforma.enformaplus.model.acesso.AcessoProfissional;
import br.com.diademaenforma.enformaplus.repository.AcessoProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AcessoProfissionalService {

    private final AcessoProfissionalRepository repository;

    public AcessoProfissionalService(AcessoProfissionalRepository repository) {
        this.repository = repository;
    }

    public List<AcessoProfissional> buscarTodosAcessos() {
       return repository.findAll();
    }

    public AcessoProfissional registrarAcesso(Long usuarioId, Long profissionalId) {
        AcessoProfissional acesso = new AcessoProfissional();
        acesso.setUsuarioId(usuarioId);
        acesso.setProfissionalId(profissionalId);
        acesso.setDataHoraAcesso(LocalDateTime.now());

        return repository.save(acesso);
    }
}
