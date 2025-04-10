package br.com.diademaenforma.enformaplus.repository;

import br.com.diademaenforma.enformaplus.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

