package br.fatec.bd4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.fatec.bd4.entity.UserSys;
import br.fatec.bd4.entity.Enum.Role;

@Repository
public interface UserSysRepository extends JpaRepository<UserSys, Long> {
    Optional<UserSys> findByUsername(String username);

    @Query("SELECT u.role FROM UserSys u where u.username = ?1")
    Role findRoleByUsername(String username);
}
