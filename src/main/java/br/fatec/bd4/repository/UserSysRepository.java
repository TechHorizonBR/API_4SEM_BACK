package br.fatec.bd4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.fatec.bd4.entity.UserSys;

@Repository
public interface UserSysRepository extends JpaRepository<UserSys, Long> {
    Optional<UserSys> findByUsername(String username);
}
